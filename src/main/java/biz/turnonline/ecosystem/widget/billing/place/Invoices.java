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

package biz.turnonline.ecosystem.widget.billing.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.gwt.user.client.History;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class Invoices
        extends Place
{
    public static final String PREFIX = "invoices";

    private static final String COLON_PREFIX = PREFIX + ":";

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

    public Long getOrderId()
    {
        return orderId;
    }

    public String getScrollspy()
    {
        return scrollspy;
    }

    /**
     * Returns the boolean indication whether current history token represents an invoice list scrollspy.
     * An empty invoices place is being considered as a scrollspy too.
     *
     * @return {@code true} if invoice list scrollspy history token
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

    @Prefix( value = PREFIX )
    public static class Tokenizer
            implements PlaceTokenizer<Invoices>
    {
        @Override
        public Invoices getPlace( String token )
        {
            if ( !token.isEmpty() )
            {
                try
                {
                    return new Invoices( Long.valueOf( token ) );
                }
                catch ( NumberFormatException e )
                {
                    return new Invoices( token );
                }
            }
            else
            {
                return new Invoices();
            }
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
                return place.getOrderId().toString();
            }
        }
    }
}
