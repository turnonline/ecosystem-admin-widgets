package biz.turnonline.ecosystem.widget.shared.rest;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountStewardFacade;
import biz.turnonline.ecosystem.widget.shared.rest.account.Country;
import biz.turnonline.ecosystem.widget.shared.rest.account.LegalForm;
import biz.turnonline.ecosystem.widget.shared.rest.billing.BillingUnit;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductBillingFacade;
import biz.turnonline.ecosystem.widget.shared.rest.billing.VatRate;
import com.google.gwt.core.client.GWT;
import org.ctoolkit.gwt.client.facade.Items;
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
    private static AccountStewardFacade accountStewardFacade = GWT.create( AccountStewardFacade.class );

    private static ProductBillingFacade productBillingFacade = GWT.create( ProductBillingFacade.class );

    private static Map<Class<?>, List<?>> codeBookCache = new HashMap<>();

    private static Map<Class<?>, Retriever<?>> codeBookRetriever = new HashMap<>();

    static
    {
        // codeBook retrievers
        codeBookRetriever.put( Country.class, ( Retriever<Country> ) callback -> accountStewardFacade.getCountries( null, callback ) );
        codeBookRetriever.put( LegalForm.class, ( Retriever<LegalForm> ) callback -> accountStewardFacade.getLegalForms( null, callback ) );
        codeBookRetriever.put( BillingUnit.class, ( Retriever<BillingUnit> ) callback -> productBillingFacade.getBillingUnits( "SK", callback ) );
        codeBookRetriever.put( VatRate.class, ( Retriever<VatRate> ) callback -> productBillingFacade.getVatRates( Configuration.get().getDomicile(), "SK", callback ) );
    }

    @SuppressWarnings( "unchecked" )
    public static <T extends CodeBook> void getCodeBook( Class<T> codeBookClass, FacadeCallback<Items<T>> callback )
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
                    callback.onSuccess( method, response );
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
        getCodeBook( codeBookClass, response -> {} );

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

    private static <T extends CodeBook> void cacheCodeBook( Class<T> codeBookClass, List<T> codeBook )
    {
        codeBookCache.put( codeBookClass, codeBook );
    }

    @SuppressWarnings( "unchecked" )
    private static <T extends CodeBook> List<T> getCodeBookFromCache( Class<T> codeBookClass )
    {
        return ( List<T> ) codeBookCache.get( codeBookClass );
    }
}
