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

package biz.turnonline.ecosystem.widget.shared;

import biz.turnonline.ecosystem.widget.shared.rest.account.AccountStewardFacade;
import biz.turnonline.ecosystem.widget.shared.rest.bill.BillFacade;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductBillingFacade;
import biz.turnonline.ecosystem.widget.shared.rest.payment.PaymentProcessorFacade;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchFacade;
import com.google.web.bindery.event.shared.SimpleEventBus;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * The event bus dedicated to be used within application widgets to eases decoupling
 * by allowing objects to interact without having direct dependencies upon one another.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@Singleton
public class AppEventBus
        extends SimpleEventBus
{
    private static AppEventBus INSTANCE;

    private final AccountStewardFacade accountSteward;

    private final ProductBillingFacade productBilling;

    private final SearchFacade searchFacade;

    private final BillFacade billFacade;

    private final PaymentProcessorFacade paymentProcessor;

    private final Configuration configuration;

    @Inject
    public AppEventBus( AccountStewardFacade accountSteward,
                        ProductBillingFacade productBilling,
                        SearchFacade searchFacade,
                        BillFacade billFacade,
                        PaymentProcessorFacade paymentProcessor,
                        Configuration configuration )
    {
        this.accountSteward = accountSteward;
        this.productBilling = productBilling;
        this.searchFacade = searchFacade;
        this.billFacade = billFacade;
        this.paymentProcessor = paymentProcessor;
        this.configuration = configuration;
    }

    /**
     * The convenient static method to access application wide event bud.
     */
    public static AppEventBus get()
    {
        if ( INSTANCE == null )
        {
            throw new IllegalArgumentException( "EventBus has not been initialized yet!" );
        }
        return INSTANCE;
    }

    /**
     * Sets the initialized event bus to be available via static method.
     */
    static void set( @Nonnull AppEventBus bus )
    {
        INSTANCE = checkNotNull( bus, "AppEventBus is can't be null" );
    }

    /**
     * Account steward service facade.
     */
    public AccountStewardFacade account()
    {
        return accountSteward;
    }

    /**
     * Product billing service facade.
     */
    public ProductBillingFacade billing()
    {
        return productBilling;
    }

    /**
     * Search service facade.
     */
    public SearchFacade search()
    {
        return searchFacade;
    }

    /**
     * Bill service facade.
     */
    public BillFacade bill()
    {
        return billFacade;
    }

    /**
     * Payment processor service facade.
     */
    public PaymentProcessorFacade paymentProcessor()
    {
        return paymentProcessor;
    }

    /**
     * Application wide configuration.
     */
    public Configuration config()
    {
        return configuration;
    }
}
