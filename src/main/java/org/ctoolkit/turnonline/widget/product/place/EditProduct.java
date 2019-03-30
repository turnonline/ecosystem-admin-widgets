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

package org.ctoolkit.turnonline.widget.product.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditProduct
        extends Place
{
    private Long id;

    public EditProduct( Long id )
    {
        this.id = id;
    }

    @Prefix( value = "edit-product" )
    public static class Tokenizer
            implements PlaceTokenizer<EditProduct>
    {
        @Override
        public EditProduct getPlace( String token )
        {
            return new EditProduct( "".equals( token ) ? null : Long.valueOf( token ) );
        }

        @Override
        public String getToken( EditProduct place )
        {
            return place.getId() != null ? place.getId().toString() : "";
        }
    }

    public Long getId()
    {
        return id;
    }
}
