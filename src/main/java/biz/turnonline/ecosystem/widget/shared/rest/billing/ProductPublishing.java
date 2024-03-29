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

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

import java.util.Date;
import java.util.List;

public final class ProductPublishing
        implements RelevantNullChecker
{
    private ProductDomain at;

    private String category;

    private Boolean comingSoon;

    private Date createdDate;

    private String description;

    private Date modificationDate;

    private List<ProductPicture> pictures;

    private Boolean published;

    /**
     * The domain where a product will be publicly available, once published. If no value has been provided the seller’s default domain will be set. The client might select only from existing domains provided by associated account.
     **/
    public ProductDomain getAt()
    {
        return at;
    }

    public ProductPublishing setAt( ProductDomain at )
    {
        this.at = at;
        return this;
    }

    /**
     * The product categorisation from the (mobile) app user perspective. A value taken from the product category code-book.
     **/
    public String getCategory()
    {
        return category;
    }

    public ProductPublishing setCategory( String category )
    {
        this.category = category;
        return this;
    }

    /**
     * The boolean indication whether to communicate publicly the product availability as coming soon.
     **/
    public Boolean getComingSoon()
    {
        return comingSoon;
    }

    public ProductPublishing setComingSoon( Boolean comingSoon )
    {
        this.comingSoon = comingSoon;
        return this;
    }

    /**
     * A date when product's publishing has been created. Populated by the service.
     **/
    public Date getCreatedDate()
    {
        return createdDate;
    }

    public ProductPublishing setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * The product description for public use. It might be any plain text or HTML document.
     **/
    public String getDescription()
    {
        return description;
    }

    public ProductPublishing setDescription( String description )
    {
        this.description = description;
        return this;
    }

    /**
     * The last modification date of the product properties. Populated by the service.
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public ProductPublishing setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    /**
     * The list of the pictures associated with this product.
     **/
    public List<ProductPicture> getPictures()
    {
        return pictures;
    }

    public ProductPublishing setPictures( List<ProductPicture> pictures )
    {
        this.pictures = pictures;
        return this;
    }

    /**
     * The boolean indication whether to publish this product or not.
     **/
    public Boolean getPublished()
    {
        return published;
    }

    public ProductPublishing setPublished( Boolean published )
    {
        this.published = published;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( at, description, pictures, category );
    }
}
