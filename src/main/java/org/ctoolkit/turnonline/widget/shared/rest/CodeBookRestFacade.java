package org.ctoolkit.turnonline.widget.shared.rest;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.storage.client.Storage;
import org.ctoolkit.gwt.client.facade.Items;
import org.ctoolkit.turnonline.widget.shared.Configuration;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.AccountStewardFacade;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.Country;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.CountryListMapper;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.LegalForm;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.LegalFormListMapper;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.BillingUnit;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.BillingUnitListMapper;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.ProductBillingFacade;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.VatRate;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.VatRateListMapper;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CodeBookRestFacade
{
    private static final String CODE_BOOK_STORAGE_KEY_PREFIX = "turnonline::codebook::";

    private static AccountStewardFacade accountStewardFacade = GWT.create( AccountStewardFacade.class );

    private static ProductBillingFacade productBillingFacade = GWT.create( ProductBillingFacade.class );

    private static Map<Class<?>, List<?>> codeBookCache = new HashMap<>();

    private static Map<Class<?>, Retriever<?>> codeBookRetriever = new HashMap<>();

    private static Map<Class<?>, ObjectMapper> codeBookObjectMapper = new HashMap<>();

    static
    {
        // codeBook retrievers
        codeBookRetriever.put( Country.class, ( Retriever<Country> ) callback -> accountStewardFacade.getCountries( null, callback ) );
        codeBookRetriever.put( LegalForm.class, ( Retriever<LegalForm> ) callback -> accountStewardFacade.getLegalForms( null, callback ) );
        codeBookRetriever.put( BillingUnit.class, ( Retriever<BillingUnit> ) callback -> productBillingFacade.getBillingUnits( LocaleInfo.getCurrentLocale().getLocaleName(), callback ) );
        codeBookRetriever.put( VatRate.class, ( Retriever<VatRate> ) callback -> productBillingFacade.getVatRates( Configuration.get().getDomicile(), "SK", callback ) );

        // codeBook object mappers
        codeBookObjectMapper.put( Country.class, CountryListMapper.INSTANCE );
        codeBookObjectMapper.put( LegalForm.class, LegalFormListMapper.INSTANCE );
        codeBookObjectMapper.put( BillingUnit.class, BillingUnitListMapper.INSTANCE );
        codeBookObjectMapper.put( VatRate.class, VatRateListMapper.INSTANCE );
    }

    @SuppressWarnings( "unchecked" )
    public static <T extends CodeBook> void getCodeBook( Class<T> codeBookClass, MethodCallback<Items<T>> callback )
    {
        List<T> codeBook = getCodeBookFromCache( codeBookClass );

        if ( codeBook == null || codeBook.isEmpty() )
        {
            Retriever<T> retriever = ( Retriever<T> ) codeBookRetriever.get( codeBookClass );
            retriever.retrieve( new CodeBookCallback( callback )
            {
                @Override
                public void onSuccess( Method method, Items response )
                {
                    cacheCodeBook( codeBookClass, response.getItems() );
                    super.onSuccess( method, response );
                }
            } );
        }
        else
        {
            Items<T> items = new Items<>();
            items.setItems( codeBook );
            callback.onSuccess( null, items );
        }
    }

    public static <T extends CodeBook> T getCodeBookValue( Class<T> codeBookClass, String code )
    {
        getCodeBook( codeBookClass, new FacadeCallback<>() );

        List<T> cache = getCodeBookFromCache( codeBookClass );
        if ( cache != null )
        {
            return cache.stream().filter( t -> t.getCode().equals( code ) ).findFirst().orElse( null );
        }
        else
        {
            return null;
        }
    }

    // -- private helpers

    @FunctionalInterface
    public interface Retriever<T>
    {
        void retrieve( MethodCallback<Items<T>> callback );
    }

    private static <T extends CodeBook> String storageKey( Class<T> codeBookClass )
    {
        return CODE_BOOK_STORAGE_KEY_PREFIX + codeBookClass.getSimpleName();
    }

    @SuppressWarnings( "unchecked" )
    private static <T extends CodeBook> void cacheCodeBook( Class<T> codeBookClass, List<T> codeBook )
    {
        Storage storage = storage();

        if ( storage != null )
        {
            String codeBookAsJson = JSON.stringify( codeBook, codeBookObjectMapper.get( codeBookClass ) );
            storage.setItem( storageKey( codeBookClass ), codeBookAsJson );
        }
        else
        {
            codeBookCache.put( codeBookClass, codeBook );
        }
    }

    @SuppressWarnings( "unchecked" )
    private static <T extends CodeBook> List<T> getCodeBookFromCache( Class<T> codeBookClass )
    {
        Storage storage = storage();
        if ( storage != null )
        {
            String codeBookAsJson = storage.getItem( storageKey( codeBookClass ) );
            if ( codeBookAsJson == null )
            {
                return null;
            }

            ObjectMapper<List<T>> objectMapper = codeBookObjectMapper.get( codeBookClass );
            return JSON.parse( codeBookAsJson, objectMapper );
        }
        else
        {
            return ( List<T> ) codeBookCache.get( codeBookClass );
        }
    }

    private static Storage storage()
    {
        return Storage.getLocalStorageIfSupported();
    }
}
