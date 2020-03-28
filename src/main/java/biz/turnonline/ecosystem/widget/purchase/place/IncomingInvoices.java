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

package biz.turnonline.ecosystem.widget.purchase.place;

import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import com.google.common.base.Splitter;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.gwt.user.client.History;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Incoming invoices place.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoices
        extends Place
{
    public static final String PREFIX = "invoices";

    private static final String COLON_PREFIX = PREFIX + ":";

    private static final String SCROLL = "scroll";

    private static final String SEPARATOR = "::";

    private static final String ORDER = "order";

    private Long orderId;

    private String scrollspy;

    public IncomingInvoices()
    {
    }

    public IncomingInvoices( String scrollspy )
    {
        this.scrollspy = scrollspy;
    }

    public IncomingInvoices( Long orderId )
    {
        this.orderId = orderId;
    }

    public static String getScrollspy( @Nullable IncomingInvoice invoice )
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

        IncomingInvoices place = tokenizer.getPlace( token );
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
            implements PlaceTokenizer<IncomingInvoices>
    {
        @Override
        public IncomingInvoices getPlace( String token )
        {
            IncomingInvoices place = new IncomingInvoices();
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
        public String getToken( IncomingInvoices place )
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
