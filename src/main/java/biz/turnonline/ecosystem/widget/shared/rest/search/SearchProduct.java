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

package biz.turnonline.ecosystem.widget.shared.rest.search;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */

import java.util.Objects;

public class SearchProduct
{
    private String id = null;

    private String name = null;

    private String snippet = null;

    private String link = null;

    private String imageUrl = null;

    private String owner = null;

    /**
     * Id by which document in index will be identified
     **/
    public SearchProduct id( String id )
    {
        this.id = id;
        return this;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    /**
     * Product name
     **/
    public SearchProduct name( String name )
    {
        this.name = name;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * Short product description
     **/
    public SearchProduct snippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

    public String getSnippet()
    {
        return snippet;
    }

    public void setSnippet( String snippet )
    {
        this.snippet = snippet;
    }

    /**
     * Product link by which product is accessible in public part of application
     **/
    public SearchProduct link( String link )
    {
        this.link = link;
        return this;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink( String link )
    {
        this.link = link;
    }

    /**
     * Image detail URL
     **/
    public SearchProduct imageUrl( String imageUrl )
    {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl( String imageUrl )
    {
        this.imageUrl = imageUrl;
    }

    /**
     * Product owner
     **/
    public SearchProduct owner( String owner )
    {
        this.owner = owner;
        return this;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner( String owner )
    {
        this.owner = owner;
    }


    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        SearchProduct product = ( SearchProduct ) o;
        return Objects.equals( id, product.id ) &&
                Objects.equals( name, product.name ) &&
                Objects.equals( snippet, product.snippet ) &&
                Objects.equals( link, product.link ) &&
                Objects.equals( imageUrl, product.imageUrl ) &&
                Objects.equals( owner, product.owner );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id, name, snippet, link, imageUrl, owner );
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "class Product {\n" );

        sb.append( "    id: " ).append( toIndentedString( id ) ).append( "\n" );
        sb.append( "    name: " ).append( toIndentedString( name ) ).append( "\n" );
        sb.append( "    snippet: " ).append( toIndentedString( snippet ) ).append( "\n" );
        sb.append( "    link: " ).append( toIndentedString( link ) ).append( "\n" );
        sb.append( "    imageUrl: " ).append( toIndentedString( imageUrl ) ).append( "\n" );
        sb.append( "    owner: " ).append( toIndentedString( owner ) ).append( "\n" );
        sb.append( "}" );
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString( Object o )
    {
        if ( o == null )
        {
            return "null";
        }
        return o.toString().replace( "\n", "\n    " );
    }
}
