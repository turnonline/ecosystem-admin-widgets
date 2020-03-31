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

package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.ui.Route;
import com.google.gwt.user.client.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Router
{
    private static Map<Route, String> listFragments = new HashMap<>();

    private static Map<Route, String> detailFragments = new HashMap<>();

    static
    {
        listFragments.put( Route.INVOICES, "invoices" );
        listFragments.put( Route.ORDERS, "orders" );
        listFragments.put( Route.PRODUCTS, "products" );
        listFragments.put( Route.CONTACTS, "contacts" );

        detailFragments.put( Route.INVOICES, "edit-invoice" );
        detailFragments.put( Route.ORDERS, "edit-order" );
        detailFragments.put( Route.PRODUCTS, "edit-product" );
        detailFragments.put( Route.CONTACTS, "edit-contact" );
    }

    public static void routeToList( Route route )
    {
        routeTo( route, listFragments.get( route ), new String[]{} );
    }

    public static void routeToDetail( Route route, String[] tokens )
    {
        routeTo( route, detailFragments.get( route ), tokens );
    }

    public static void routeTo( Route route, String routePrefix, String[] tokens )
    {
        StringBuilder url = new StringBuilder();
        url.append( route.path() );
        url.append( "#" ).append( routePrefix ).append( ":" );

        int tokensApplied = 0;
        for ( String token : tokens )
        {
            if ( tokensApplied > 0 )
            {
                url.append( "|" );
            }
            tokensApplied++;

            url.append( token );
        }

        Window.Location.replace( url.toString() );
    }
}
