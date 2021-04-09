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

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import java.util.Objects;

public final class ProductPicture
{
    private Integer order;

    private String servingUrl;

    private String storageName;

    /**
     * The order number how pictures should be organised. If it's not supplied by the client, it will be numbered in the order as it was provided. Order number must be unique within list.
     **/
    public Integer getOrder()
    {
        return order;
    }

    public ProductPicture setOrder( Integer order )
    {
        this.order = order;
        return this;
    }

    /**
     * The full URL of the picture served from the content delivery network (CDN). Provided by the service once an image (storageName) has been uploaded.
     **/
    public String getServingUrl()
    {
        return servingUrl;
    }

    public ProductPicture setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

    /**
     * The full path to the picture. It's an identification in the underlying storage. This property acts as a list item identification.
     **/
    public String getStorageName()
    {
        return storageName;
    }

    public ProductPicture setStorageName( String storageName )
    {
        this.storageName = storageName;
        return this;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( !( o instanceof ProductPicture ) ) return false;
        ProductPicture that = ( ProductPicture ) o;
        return storageName.equals( that.storageName );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( storageName );
    }
}
