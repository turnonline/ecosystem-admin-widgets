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

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Incoming invoice details place.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceDetails
        extends Place
{
    private final Long orderId;

    private final Long invoiceId;

    private final String tab;

    public IncomingInvoiceDetails( Long orderId, Long invoiceId, String tab )
    {
        this.orderId = orderId;
        this.invoiceId = invoiceId;
        this.tab = tab;
    }

    private static Long tryParseId( String id )
    {
        try
        {
            return Long.valueOf( id );
        }
        catch ( NumberFormatException e )
        {
            return null;
        }
    }

    public Long getInvoiceId()
    {
        return invoiceId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public String getTab()
    {
        return tab;
    }

    @Prefix( value = "invoice-detail" )
    public static class Tokenizer
            implements PlaceTokenizer<IncomingInvoiceDetails>
    {
        @Override
        public IncomingInvoiceDetails getPlace( String token )
        {
            Long orderId = null;
            Long invoiceId = null;
            String tab = null;

            if ( !token.isEmpty() )
            {
                String[] tokens = token.split( "\\|" );
                if ( tokens.length == 1 )
                {
                    tab = tokens[0];
                }
                else if ( tokens.length == 3 )
                {
                    orderId = tryParseId( tokens[0] );
                    invoiceId = tryParseId( tokens[1] );
                    tab = tokens[2];
                }
            }

            return new IncomingInvoiceDetails( orderId, invoiceId, tab );
        }

        @Override
        public String getToken( IncomingInvoiceDetails place )
        {
            String token = "";

            if ( place.getOrderId() != null )
            {
                token += place.getOrderId();
            }
            if ( place.getInvoiceId() != null )
            {
                if ( !token.isEmpty() )
                {
                    token += "|";
                }
                token += place.getInvoiceId();
            }
            if ( place.getTab() != null )
            {
                if ( !token.isEmpty() )
                {
                    token += "|";
                }
                token += place.getTab();
            }

            return token;
        }
    }
}
