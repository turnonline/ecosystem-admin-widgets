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

import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.AccountStewardFacade;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.ProductBillingFacade;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchFacade;
import com.google.web.bindery.event.shared.SimpleEventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Singleton
public class AppEventBus
        extends SimpleEventBus
{
    private final AccountStewardFacade accountSteward;

    private final ProductBillingFacade productBilling;

    private final SearchFacade searchFacade;

    private final Configuration configuration;

    @Inject
    public AppEventBus( AccountStewardFacade accountSteward,
                        ProductBillingFacade productBilling,
                        SearchFacade searchFacade,
                        Configuration configuration )
    {
        this.accountSteward = accountSteward;
        this.productBilling = productBilling;
        this.searchFacade = searchFacade;
        this.configuration = configuration;
    }

    public AccountStewardFacade accountSteward()
    {
        return accountSteward;
    }

    public ProductBillingFacade productBilling()
    {
        return productBilling;
    }

    public SearchFacade searchFacade()
    {
        return searchFacade;
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }
}