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
 * Purchase order detail place.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderDetail
        extends Place
{
    private Long id;

    public PurchaseOrderDetail( Long id )
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    @Prefix( value = "order-detail" )
    public static class Tokenizer
            implements PlaceTokenizer<PurchaseOrderDetail>
    {
        @Override
        public PurchaseOrderDetail getPlace( String token )
        {
            return new PurchaseOrderDetail( "".equals( token ) ? null : Long.valueOf( token ) );
        }

        @Override
        public String getToken( PurchaseOrderDetail place )
        {
            return place.getId() != null ? place.getId().toString() : "";
        }
    }
}
