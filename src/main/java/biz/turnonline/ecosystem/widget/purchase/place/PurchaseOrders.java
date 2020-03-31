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

package biz.turnonline.ecosystem.widget.purchase.place;

import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import com.google.common.base.Splitter;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.gwt.user.client.History;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Purchase orders place.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrders
        extends Place
{
    public static final String PREFIX = "orders";

    private static final String COLON_PREFIX = PREFIX + ":";

    private static final String SCROLL = "scroll";

    private String scrollspy;

    public PurchaseOrders()
    {
    }

    public PurchaseOrders( String scrollspy )
    {
        this.scrollspy = scrollspy;
    }

    public static String getScrollspy( @Nullable PurchaseOrder order )
    {
        return order == null || order.getId() == null ? null : scrollspy( String.valueOf( order.getId() ) );
    }

    private static String scrollspy( String id )
    {
        return "/" + SCROLL + "/" + id;
    }

    /**
     * Returns the boolean indication whether current history token represents an order list scrollspy.
     * An empty purchase orders place is being considered as a scrollspy too.
     *
     * @return {@code true} if order list represents scrollspy history token
     */
    public static boolean isCurrentTokenScrollspy()
    {
        PurchaseOrders.Tokenizer tokenizer = new PurchaseOrders.Tokenizer();
        String token = History.getToken();
        if ( !token.startsWith( COLON_PREFIX ) )
        {
            return false;
        }

        token = token.substring( COLON_PREFIX.length() );

        PurchaseOrders place = tokenizer.getPlace( token );
        return token.isEmpty() || place.getScrollspy() != null;
    }

    public String getScrollspy()
    {
        return scrollspy;
    }

    @Prefix( value = PREFIX )
    public static class Tokenizer
            implements PlaceTokenizer<PurchaseOrders>
    {
        @Override
        public PurchaseOrders getPlace( String token )
        {
            PurchaseOrders place = new PurchaseOrders();
            List<String> pieces = Splitter.on( "/" ).omitEmptyStrings().trimResults().splitToList( token );

            if ( pieces.contains( SCROLL ) )
            {
                int index = pieces.indexOf( SCROLL );
                place.scrollspy = scrollspy( pieces.get( index + 1 ) );
            }
            return place;
        }

        @Override
        public String getToken( PurchaseOrders place )
        {
            if ( place.getScrollspy() != null )
            {
                return place.getScrollspy();
            }

            return "";
        }
    }
}
