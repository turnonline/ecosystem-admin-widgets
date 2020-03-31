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

package biz.turnonline.ecosystem.widget.product.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditProduct
        extends Place
{
    private Long id;

    private String tab;

    public EditProduct( Long id, String tab )
    {
        this.id = id;
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

    public Long getId()
    {
        return id;
    }

    public String getTab()
    {
        return tab;
    }

    @Prefix( value = "edit-product" )
    public static class Tokenizer
            implements PlaceTokenizer<EditProduct>
    {
        @Override
        public EditProduct getPlace( String token )
        {
            Long id = null;
            String tab = null;

            if ( !token.isEmpty() )
            {
                String[] tokens = token.split( "\\|" );
                if ( tokens.length == 1 )
                {
                    id = tryParseId( tokens[0] );
                    if ( id == null )
                    {
                        tab = tokens[0];
                    }
                }
                else if ( tokens.length == 2 )
                {
                    id = tryParseId( tokens[0] );
                    tab = tokens[1];
                }
            }

            return new EditProduct( id, tab );
        }

        @Override
        public String getToken( EditProduct place )
        {
            String token = "";
            if ( place.getId() != null )
            {
                token += place.getId();
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
