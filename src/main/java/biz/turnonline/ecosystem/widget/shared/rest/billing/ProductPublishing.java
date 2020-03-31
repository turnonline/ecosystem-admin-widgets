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

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

import java.util.Date;
import java.util.List;

public final class ProductPublishing
        implements RelevantNullChecker
{
    private ProductDomain at;

    private Boolean comingSoon;

    private Date createdDate;

    private String description;

    private Boolean facebookLike;

    private Boolean googlePlus;

    private Boolean linkedInShare;

    private Date modificationDate;

    private List<ProductPicture> pictures;

    private Boolean published;

    public ProductDomain getAt()
    {
        return at;
    }

    public ProductPublishing setAt( ProductDomain at )
    {
        this.at = at;
        return this;
    }

    public Boolean getComingSoon()
    {
        return comingSoon;
    }

    public ProductPublishing setComingSoon( Boolean comingSoon )
    {
        this.comingSoon = comingSoon;
        return this;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public ProductPublishing setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public ProductPublishing setDescription( String description )
    {
        this.description = description;
        return this;
    }

    public Boolean getFacebookLike()
    {
        return facebookLike;
    }

    public ProductPublishing setFacebookLike( Boolean facebookLike )
    {
        this.facebookLike = facebookLike;
        return this;
    }

    public Boolean getGooglePlus()
    {
        return googlePlus;
    }

    public ProductPublishing setGooglePlus( Boolean googlePlus )
    {
        this.googlePlus = googlePlus;
        return this;
    }

    public Boolean getLinkedInShare()
    {
        return linkedInShare;
    }

    public ProductPublishing setLinkedInShare( Boolean linkedInShare )
    {
        this.linkedInShare = linkedInShare;
        return this;
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public ProductPublishing setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public List<ProductPicture> getPictures()
    {
        return pictures;
    }

    public ProductPublishing setPictures( List<ProductPicture> pictures )
    {
        this.pictures = pictures;
        return this;
    }

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
        return allNull( at, description, pictures );
    }
}
