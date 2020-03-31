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

package biz.turnonline.ecosystem.widget.billing.place;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import com.google.common.base.Splitter;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.gwt.user.client.History;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class Invoices
        extends Place
{
    public static final String PREFIX = "invoices";

    private static final String COLON_PREFIX = PREFIX + ":";

    private static final String SCROLL = "scroll";

    private static final String SEPARATOR = "::";

    private static final String ORDER = "order";

    private Long orderId;

    private String scrollspy;

    public Invoices()
    {
    }

    public Invoices( String scrollspy )
    {
        this.scrollspy = scrollspy;
    }

    public Invoices( Long orderId )
    {
        this.orderId = orderId;
    }

    public static String getScrollspy( @Nullable Invoice invoice )
    {
        return invoice == null ? null : scrollspy( invoice.getOrderId(), invoice.getId() );
    }

    private static String scrollspy( Long orderId, Long invoiceId )
    {
        return "/" + SCROLL + "/" + orderId + SEPARATOR + invoiceId;
    }

    private static String order( Long orderId )
    {
        return "/" + ORDER + "/" + orderId;
    }

    /**
     * Returns the boolean indication whether current history token represents an invoice list scrollspy.
     * An empty invoices place is being considered as a scrollspy too.
     *
     * @return {@code true} if invoice list represents scrollspy history token
     */
    public static boolean isCurrentTokenScrollspy()
    {
        Tokenizer tokenizer = new Tokenizer();
        String token = History.getToken();
        if ( !token.startsWith( COLON_PREFIX ) )
        {
            return false;
        }

        token = token.substring( COLON_PREFIX.length() );

        Invoices place = tokenizer.getPlace( token );
        return token.isEmpty() || place.getScrollspy() != null;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public String getScrollspy()
    {
        return scrollspy;
    }

    @Prefix( value = PREFIX )
    public static class Tokenizer
            implements PlaceTokenizer<Invoices>
    {
        @Override
        public Invoices getPlace( String token )
        {
            Invoices place = new Invoices();
            List<String> pieces = Splitter.on( "/" ).omitEmptyStrings().trimResults().splitToList( token );

            if ( pieces.contains( SCROLL ) )
            {
                int index = pieces.indexOf( SCROLL );
                String[] fullId = pieces.get( index + 1 ).split( SEPARATOR );
                place.scrollspy = scrollspy( Long.valueOf( fullId[0] ), Long.valueOf( fullId[1] ) );
            }

            if ( pieces.contains( ORDER ) )
            {
                int index = pieces.indexOf( ORDER );
                String id = pieces.get( index + 1 );
                place.orderId = Long.valueOf( id );
            }
            return place;
        }

        @Override
        public String getToken( Invoices place )
        {
            if ( place.getScrollspy() != null )
            {
                return place.getScrollspy();
            }
            if ( place.getOrderId() == null )
            {
                return "";
            }
            else
            {
                return order( place.getOrderId() );
            }
        }
    }
}
