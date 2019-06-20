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

package biz.turnonline.ecosystem.widget.shared.rest.billing;

/**
 * Model definition for PricingItem.
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
public final class PricingItem
{

    /**
     * The value may be {@code null}.
     */
    private Double amount;

    /**
     * The value may be {@code null}.
     */
    private Boolean checkedIn;

    /**
     * The value may be {@code null}.
     */
    private String currency;

    /**
     * The value may be {@code null}.
     */
    private Boolean discountApplied;

    /**
     * The value may be {@code null}.
     */
    private String discountCode;

    /**
     * The value may be {@code null}.
     */
    private Boolean discountCodeDefined;

    /**
     * The value may be {@code null}.
     */
    private Boolean discountDefined;

    /**
     * The value may be {@code null}.
     */
    private Double finalPrice;

    /**
     * The value may be {@code null}.
     */
    private Double finalPriceExclVat;

    /**
     * The value may be {@code null}.
     */
    private Double finalValueAddedTax;

    /**
     * The value may be {@code null}.
     */
    private String finalVat;

    /**
     * The value may be {@code null}.
     */
    private Double finalVatBase;

    /**
     * The value may be {@code null}.
     */
    private Long id;

    /**
     * The value may be {@code null}.
     */
    private String inline;

    /**
     * The value may be {@code null}.
     */
    private String itemName;

    /**
     * The value may be {@code null}.
     */
    private String itemType;

    /**
     * The value may be {@code null}.
     */
    private String itemUrl;

    /**
     * The value may be {@code null}.
     */
    private java.util.List<PricingItem> items;

    /**
     * The value may be {@code null}.
     */
    private ProductMetaFields metaFields;

    /**
     * The value may be {@code null}.
     */
    private Integer order;

    /**
     * The value may be {@code null}.
     */
    private Double priceExclVat;

    /**
     * The value may be {@code null}.
     */
    private PricingProduct product;

    /**
     * The value may be {@code null}.
     */
    private String snippet;

    /**
     * The value may be {@code null}.
     */
    private java.util.Map<String, Object> subsidiary;

    /**
     * The value may be {@code null}.
     */
    private String thumbnailUrl;

    /**
     * The value may be {@code null}.
     */
    private String unit;

    /**
     * The value may be {@code null}.
     */
    private Double valueAddedTax;

    /**
     * The value may be {@code null}.
     */
    private String vat;

    /**
     * @return value or {@code null} for none
     */
    public Double getAmount()
    {
        return amount;
    }

    /**
     * @param amount amount or {@code null} for none
     */
    public PricingItem setAmount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Boolean getCheckedIn()
    {
        return checkedIn;
    }

    /**
     * @param checkedIn checkedIn or {@code null} for none
     */
    public PricingItem setCheckedIn( Boolean checkedIn )
    {
        this.checkedIn = checkedIn;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getCurrency()
    {
        return currency;
    }

    /**
     * @param currency currency or {@code null} for none
     */
    public PricingItem setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Boolean getDiscountApplied()
    {
        return discountApplied;
    }

    /**
     * @param discountApplied discountApplied or {@code null} for none
     */
    public PricingItem setDiscountApplied( Boolean discountApplied )
    {
        this.discountApplied = discountApplied;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getDiscountCode()
    {
        return discountCode;
    }

    /**
     * @param discountCode discountCode or {@code null} for none
     */
    public PricingItem setDiscountCode( String discountCode )
    {
        this.discountCode = discountCode;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Boolean getDiscountCodeDefined()
    {
        return discountCodeDefined;
    }

    /**
     * @param discountCodeDefined discountCodeDefined or {@code null} for none
     */
    public PricingItem setDiscountCodeDefined( Boolean discountCodeDefined )
    {
        this.discountCodeDefined = discountCodeDefined;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Boolean getDiscountDefined()
    {
        return discountDefined;
    }

    /**
     * @param discountDefined discountDefined or {@code null} for none
     */
    public PricingItem setDiscountDefined( Boolean discountDefined )
    {
        this.discountDefined = discountDefined;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getFinalPrice()
    {
        return finalPrice;
    }

    /**
     * @param finalPrice finalPrice or {@code null} for none
     */
    public PricingItem setFinalPrice( Double finalPrice )
    {
        this.finalPrice = finalPrice;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getFinalPriceExclVat()
    {
        return finalPriceExclVat;
    }

    /**
     * @param finalPriceExclVat finalPriceExclVat or {@code null} for none
     */
    public PricingItem setFinalPriceExclVat( Double finalPriceExclVat )
    {
        this.finalPriceExclVat = finalPriceExclVat;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getFinalValueAddedTax()
    {
        return finalValueAddedTax;
    }

    /**
     * @param finalValueAddedTax finalValueAddedTax or {@code null} for none
     */
    public PricingItem setFinalValueAddedTax( Double finalValueAddedTax )
    {
        this.finalValueAddedTax = finalValueAddedTax;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getFinalVat()
    {
        return finalVat;
    }

    /**
     * @param finalVat finalVat or {@code null} for none
     */
    public PricingItem setFinalVat( String finalVat )
    {
        this.finalVat = finalVat;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getFinalVatBase()
    {
        return finalVatBase;
    }

    /**
     * @param finalVatBase finalVatBase or {@code null} for none
     */
    public PricingItem setFinalVatBase( Double finalVatBase )
    {
        this.finalVatBase = finalVatBase;
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
    public PricingItem setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getInline()
    {
        return inline;
    }

    /**
     * @param inline inline or {@code null} for none
     */
    public PricingItem setInline( String inline )
    {
        this.inline = inline;
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
    public PricingItem setItemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getItemType()
    {
        return itemType;
    }

    /**
     * @param itemType itemType or {@code null} for none
     */
    public PricingItem setItemType( String itemType )
    {
        this.itemType = itemType;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getItemUrl()
    {
        return itemUrl;
    }

    /**
     * @param itemUrl itemUrl or {@code null} for none
     */
    public PricingItem setItemUrl( String itemUrl )
    {
        this.itemUrl = itemUrl;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.util.List<PricingItem> getItems()
    {
        return items;
    }

    /**
     * @param items items or {@code null} for none
     */
    public PricingItem setItems( java.util.List<PricingItem> items )
    {
        this.items = items;
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
    public PricingItem setMetaFields( ProductMetaFields metaFields )
    {
        this.metaFields = metaFields;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Integer getOrder()
    {
        return order;
    }

    /**
     * @param order order or {@code null} for none
     */
    public PricingItem setOrder( Integer order )
    {
        this.order = order;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    /**
     * @param priceExclVat priceExclVat or {@code null} for none
     */
    public PricingItem setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public PricingProduct getProduct()
    {
        return product;
    }

    /**
     * @param product product or {@code null} for none
     */
    public PricingItem setProduct( PricingProduct product )
    {
        this.product = product;
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
    public PricingItem setSnippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.util.Map<String, Object> getSubsidiary()
    {
        return subsidiary;
    }

    /**
     * @param subsidiary subsidiary or {@code null} for none
     */
    public PricingItem setSubsidiary( java.util.Map<String, Object> subsidiary )
    {
        this.subsidiary = subsidiary;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    /**
     * @param thumbnailUrl thumbnailUrl or {@code null} for none
     */
    public PricingItem setThumbnailUrl( String thumbnailUrl )
    {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * @param unit unit or {@code null} for none
     */
    public PricingItem setUnit( String unit )
    {
        this.unit = unit;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getValueAddedTax()
    {
        return valueAddedTax;
    }

    /**
     * @param valueAddedTax valueAddedTax or {@code null} for none
     */
    public PricingItem setValueAddedTax( Double valueAddedTax )
    {
        this.valueAddedTax = valueAddedTax;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getVat()
    {
        return vat;
    }

    /**
     * @param vat vat or {@code null} for none
     */
    public PricingItem setVat( String vat )
    {
        this.vat = vat;
        return this;
    }

}
