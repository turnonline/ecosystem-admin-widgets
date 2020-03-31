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

    public Integer getOrder()
    {
        return order;
    }

    public ProductPicture setOrder( Integer order )
    {
        this.order = order;
        return this;
    }

    public String getServingUrl()
    {
        return servingUrl;
    }

    public ProductPicture setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

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
