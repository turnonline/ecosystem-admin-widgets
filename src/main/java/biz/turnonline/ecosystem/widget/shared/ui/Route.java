/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.user.client.Window;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public enum Route
{
    DASHBOARD( "/purchases", "dashboard", 1 ),
    MY_ACCOUNT( "/my-account", "my-account", 2 ),
    SETTINGS( "/my-account", "my-account/settings", 3 ),
    INVOICES( "/billing", "invoices", 4 ),
    ORDERS( "/billing", "orders", 5 ),
    PRODUCTS( "/products", "products", 6 ),
    CONTACTS( "/contacts", "contacts", 7 ),
    PURCHASES( "/purchases", "invoices", 8 ), // TODO: fix order of items bellow when Purchases will be visible again
    BILLS( "/purchases", "bills", 8 ),
    EXPENSES( "/purchases", "expenses", 9 ),
    TRANSACTIONS( "/purchases", "transactions", 10 ),
    LOGOUT( "/logout" );

    private final String url;

    private String fragment;

    private Integer navBarOrder;

    Route( String url )
    {
        this.url = url;
    }

    Route( String url, String fragment, Integer navBarOrder )
    {
        this.url = url;
        this.fragment = fragment;
        this.navBarOrder = navBarOrder;
    }

    public String url()
    {
        return path() + ( fragment != null ? "#" + fragment + ":" : "" );
    }

    public String path()
    {
        String path;
        if ( Window.Location.getPort().equals( "8888" ) )
        {
            path = Window.Location.getProtocol() + "//"
                    + Window.Location.getHost()
                    + url +
                    ".html";
        }
        else
        {
            path = url;
        }

        return path;
    }

    public Integer navBarOrder()
    {
        return navBarOrder;
    }
}
