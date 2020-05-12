/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.shared.rest;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountStewardFacade;
import biz.turnonline.ecosystem.widget.shared.rest.account.Country;
import biz.turnonline.ecosystem.widget.shared.rest.account.LegalForm;
import biz.turnonline.ecosystem.widget.shared.rest.billing.BillingUnit;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductBillingFacade;
import biz.turnonline.ecosystem.widget.shared.rest.billing.VatRate;
import biz.turnonline.ecosystem.widget.shared.rest.payment.BankCode;
import biz.turnonline.ecosystem.widget.shared.rest.payment.PaymentProcessorFacade;
import com.google.gwt.core.client.GWT;
import org.ctoolkit.gwt.client.facade.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CodeBookRestFacade
{
    private static AccountStewardFacade accountStewardFacade = GWT.create( AccountStewardFacade.class );

    private static ProductBillingFacade productBillingFacade = GWT.create( ProductBillingFacade.class );

    private static PaymentProcessorFacade paymentFacade = GWT.create( PaymentProcessorFacade.class );

    private static Map<Class<?>, List<?>> codeBookCache = new HashMap<>();

    private static Map<Class<?>, Retriever<?>> codeBookRetriever = new HashMap<>();

    private static Map<Class<?>, List<SuccessCallback<Items>>> codeBookWaitingList = new HashMap<>();

    static
    {
        // codeBook retrievers
        codeBookRetriever.put( Country.class, ( Retriever<Country> ) callback -> accountStewardFacade.getCountries( null, callback ) );
        codeBookRetriever.put( LegalForm.class, ( Retriever<LegalForm> ) callback -> accountStewardFacade.getLegalForms( null, callback ) );
        codeBookRetriever.put( BillingUnit.class, ( Retriever<BillingUnit> ) callback -> productBillingFacade.getBillingUnits( "SK", callback ) );
        codeBookRetriever.put( VatRate.class, ( Retriever<VatRate> ) callback -> productBillingFacade.getVatRates( Configuration.get().getDomicile(), "SK", callback ) );
        codeBookRetriever.put( BankCode.class, ( Retriever<BankCode> ) callback -> paymentFacade.getBankCodes( "SK", Configuration.get().getDomicile(), callback ) );

        codeBookWaitingList.put( Country.class, new ArrayList<>() );
        codeBookWaitingList.put( LegalForm.class, new ArrayList<>() );
        codeBookWaitingList.put( BillingUnit.class, new ArrayList<>() );
        codeBookWaitingList.put( VatRate.class, new ArrayList<>() );
        codeBookWaitingList.put( BankCode.class, new ArrayList<>() );
    }

    @SuppressWarnings( "unchecked" )
    public static <T extends CodeBook> void getCodeBook( Class<T> codeBookClass, SuccessCallback<Items<T>> callback )
    {
        List<T> codeBook = getCodeBookFromCache( codeBookClass );

        if ( codeBook == null || codeBook.isEmpty() )
        {
            boolean firstCallback = codeBookWaitingList.get( codeBookClass ).isEmpty();

            // register callback to waiting list
            List<SuccessCallback<Items>> SuccessCallbacks = codeBookWaitingList.get( codeBookClass );
            SuccessCallbacks.add( ( SuccessCallback ) callback );

            if ( firstCallback )
            {
                Retriever<T> retriever = ( Retriever<T> ) codeBookRetriever.get( codeBookClass );
                retriever.retrieve( new CodeBookCallback( callback )
                {
                    @Override
                    public void onSuccess( Items response )
                    {
                        cacheCodeBook( codeBookClass, response.getItems() );

                        // initialize all callbacks from waiting list
                        codeBookWaitingList.get( codeBookClass ).forEach( c -> c.onSuccess( response ) );
                    }
                } );
            }
        }
        else
        {
            Items<T> items = new Items<>();
            items.setItems( codeBook );
            callback.onSuccess( null, items );
        }
    }

    // -- private helpers

    private static <T extends CodeBook> void cacheCodeBook( Class<T> codeBookClass, List<T> codeBook )
    {
        codeBookCache.put( codeBookClass, codeBook );
    }

    @SuppressWarnings( "unchecked" )
    private static <T extends CodeBook> List<T> getCodeBookFromCache( Class<T> codeBookClass )
    {
        return ( List<T> ) codeBookCache.get( codeBookClass );
    }

    @FunctionalInterface
    public interface Retriever<T>
    {
        void retrieve( SuccessCallback<Items<T>> callback );
    }
}
