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

public final class ProductOverview
{
    private String about;

    private String productUrl;

    private Boolean published;

    private String thumbnailUrl;

    public String getAbout()
    {
        return about;
    }

    public ProductOverview setAbout( String about )
    {
        this.about = about;
        return this;
    }

    public String getProductUrl()
    {
        return productUrl;
    }

    public ProductOverview setProductUrl( String productUrl )
    {
        this.productUrl = productUrl;
        return this;
    }

    public Boolean getPublished()
    {
        return published;
    }

    public ProductOverview setPublished( Boolean published )
    {
        this.published = published;
        return this;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public ProductOverview setThumbnailUrl( String thumbnailUrl )
    {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }
}
