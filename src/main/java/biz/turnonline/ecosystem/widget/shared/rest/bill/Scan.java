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

package biz.turnonline.ecosystem.widget.shared.rest.bill;

import java.util.Objects;

/**
 * Scan
 */
public class Scan
{
    private Integer order;

    private String servingUrl;

    private String storageName;

    public Scan order( Integer order )
    {
        this.order = order;
        return this;
    }

    /**
     * The order number how a scanned receipt should be organised. If it's not supplied by the client, it will be numbered in the order as it was provided. Order number must be unique within list.
     **/
    public Integer getOrder()
    {
        return order;
    }

    public void setOrder( Integer order )
    {
        this.order = order;
    }

    public Scan servingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

    /**
     * The full URL of the scanned receipt served from the content delivery network (CDN). Provided by the service once an image ('storageName') has been uploaded.
     **/
    public String getServingUrl()
    {
        return servingUrl;
    }

    public void setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
    }

    public Scan storageName( String storageName )
    {
        this.storageName = storageName;
        return this;
    }

    /**
     * The full path to the scanned receipt. It's an identification in the underlying storage. This property acts as a list item identification.
     **/
    public String getStorageName()
    {
        return storageName;
    }

    public void setStorageName( String storageName )
    {
        this.storageName = storageName;
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
        Scan scan = ( Scan ) o;
        return Objects.equals( this.order, scan.order ) &&
                Objects.equals( this.servingUrl, scan.servingUrl ) &&
                Objects.equals( this.storageName, scan.storageName );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( order, servingUrl, storageName );
    }


    @Override
    public String toString()
    {
        return "class Scan {\n" +
                "    order: " + toIndentedString( order ) + "\n" +
                "    servingUrl: " + toIndentedString( servingUrl ) + "\n" +
                "    storageName: " + toIndentedString( storageName ) + "\n" +
                "}";
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

