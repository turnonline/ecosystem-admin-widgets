/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared;

import biz.turnonline.ecosystem.widget.shared.rest.account.AccountStewardFacade;
import biz.turnonline.ecosystem.widget.shared.rest.bill.BillFacade;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductBillingFacade;
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

    private final Configuration configuration;

    @Inject
    public AppEventBus( AccountStewardFacade accountSteward,
                        ProductBillingFacade productBilling,
                        SearchFacade searchFacade,
                        BillFacade billFacade,
                        Configuration configuration )
    {
        this.accountSteward = accountSteward;
        this.productBilling = productBilling;
        this.searchFacade = searchFacade;
        this.billFacade = billFacade;
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
     * Application wide configuration.
     */
    public Configuration config()
    {
        return configuration;
    }
}
