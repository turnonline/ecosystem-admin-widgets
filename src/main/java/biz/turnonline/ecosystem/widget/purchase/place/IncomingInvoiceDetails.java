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
