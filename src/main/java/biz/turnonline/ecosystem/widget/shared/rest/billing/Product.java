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

public final class Product
        implements RelevantNullChecker
{
    private Date createdDate;

    private Event event;

    private Long id;

    private ProductInvoicing invoicing;

    private String itemName;

    private ProductMetaFields metaFields;

    private Date modificationDate;

    private ProductOverview overview;

    private ProductPricing pricing;

    private ProductPublishing publishing;

    private String snippet;

    /**
     * A date when the product has been created. Populated by the service.
     **/
    public Date getCreatedDate()
    {
        return createdDate;
    }

    public Product setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * The product extension by event specification.
     **/
    public Event getEvent()
    {
        return event;
    }

    public Product setEvent( Event event )
    {
        this.event = event;
        return this;
    }

    public void setEventIf( Event event )
    {
        setIfNotAllNull( this::setEvent, event );
    }

    /**
     * The unique product identification.
     **/
    public Long getId()
    {
        return id;
    }

    public Product setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * The set of invoicing rules to be used once product will be placed at the invoice.
     **/
    public ProductInvoicing getInvoicing()
    {
        return invoicing;
    }

    public Product setInvoicing( ProductInvoicing invoicing )
    {
        this.invoicing = invoicing;
        return this;
    }

    public void setInvoicingIf( ProductInvoicing invoicing )
    {
        setIfNotAllNull( this::setInvoicing, invoicing );
    }

    /**
     * The product name, very short summary that might be placed at invoice as a billing item or used as a title at web page if published.
     **/
    public String getItemName()
    {
        return itemName;
    }

    public Product setItemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    /**
     * The product specific metadata fields configuration.
     **/
    public ProductMetaFields getMetaFields()
    {
        return metaFields;
    }

    public Product setMetaFields( ProductMetaFields metaFields )
    {
        this.metaFields = metaFields;
        return this;
    }

    public void setMetaFieldsIf( ProductMetaFields metaFields )
    {
        setIfNotAllNull( this::setMetaFields, metaFields );
    }

    /**
     * The last modification date of the product properties. Populated by the service.
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public Product setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    /**
     * Product overview, a highlighted properties that might be taken from the sub resources.
     **/
    public ProductOverview getOverview()
    {
        return overview;
    }

    public Product setOverview( ProductOverview overview )
    {
        this.overview = overview;
        return this;
    }

    /**
     * The product pricing.
     **/
    public ProductPricing getPricing()
    {
        return pricing;
    }

    public Product setPricing( ProductPricing pricing )
    {
        this.pricing = pricing;
        return this;
    }

    /**
     * The product's additional configuration for public use.
     **/
    public ProductPublishing getPublishing()
    {
        return publishing;
    }

    public Product setPublishing( ProductPublishing publishing )
    {
        this.publishing = publishing;
        return this;
    }

    public void setPublishingIf( ProductPublishing publishing )
    {
        setIfNotAllNull( this::setPublishing, publishing );
    }

    /**
     * The product short description, a brief overview (not appearing at invoice). The plain text only.
     **/
    public String getSnippet()
    {
        return snippet;
    }

    public Product setSnippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( event, invoicing, itemName, metaFields, pricing, publishing, snippet );
    }
}
