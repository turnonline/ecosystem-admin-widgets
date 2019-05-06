/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2018-10-08 17:45:39 UTC)
 * on 2019-03-14 at 19:18:29 UTC
 * Modify at your own risk.
 */

package biz.turnonline.ecosystem.widget.shared.rest.productbilling;

import java.util.Date;

/**
 * Model definition for Product.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Product Billing. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings( "javadoc" )
public final class Product
{

    /**
     * The value may be {@code null}.
     */
    
    private String about;

    /**
     * The value may be {@code null}.
     */
    
    private Date createdDate;

    /**
     * The value may be {@code null}.
     */
    
    private Event event;

    /**
     * The value may be {@code null}.
     */

    private Long id;

    /**
     * The value may be {@code null}.
     */
    
    private ProductInvoicing invoicing;

    /**
     * The value may be {@code null}.
     */
    
    private String itemName;

    /**
     * The value may be {@code null}.
     */
    
    private ProductMetaFields metaFields;

    /**
     * The value may be {@code null}.
     */
    
    private Date modificationDate;

    /**
     * The value may be {@code null}.
     */
    
    private ProductPricing pricing;

    /**
     * The value may be {@code null}.
     */
    
    private ProductPublishing publishing;

    /**
     * The value may be {@code null}.
     */
    
    private String snippet;

    /**
     * @return value or {@code null} for none
     */
    public String getAbout()
    {
        return about;
    }

    /**
     * @param about about or {@code null} for none
     */
    public Product setAbout( String about )
    {
        this.about = about;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getCreatedDate()
    {
        return createdDate;
    }

    /**
     * @param createdDate createdDate or {@code null} for none
     */
    public Product setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Event getEvent()
    {
        return event;
    }

    /**
     * @param event event or {@code null} for none
     */
    public Product setEvent( Event event )
    {
        this.event = event;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id id or {@code null} for none
     */
    public Product setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public ProductInvoicing getInvoicing()
    {
        return invoicing;
    }

    /**
     * @param invoicing invoicing or {@code null} for none
     */
    public Product setInvoicing( ProductInvoicing invoicing )
    {
        this.invoicing = invoicing;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * @param itemName itemName or {@code null} for none
     */
    public Product setItemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public ProductMetaFields getMetaFields()
    {
        return metaFields;
    }

    /**
     * @param metaFields metaFields or {@code null} for none
     */
    public Product setMetaFields( ProductMetaFields metaFields )
    {
        this.metaFields = metaFields;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getModificationDate()
    {
        return modificationDate;
    }

    /**
     * @param modificationDate modificationDate or {@code null} for none
     */
    public Product setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public ProductPricing getPricing()
    {
        return pricing;
    }

    /**
     * @param pricing pricing or {@code null} for none
     */
    public Product setPricing( ProductPricing pricing )
    {
        this.pricing = pricing;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public ProductPublishing getPublishing()
    {
        return publishing;
    }

    /**
     * @param publishing publishing or {@code null} for none
     */
    public Product setPublishing( ProductPublishing publishing )
    {
        this.publishing = publishing;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getSnippet()
    {
        return snippet;
    }

    /**
     * @param snippet snippet or {@code null} for none
     */
    public Product setSnippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

}