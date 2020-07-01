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
 */

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

import java.util.List;
import java.util.Map;

public final class PricingItem
        implements RelevantNullChecker
{
    private Double amount;

    private Boolean checkedIn;

    private String currency;

    private Boolean discountApplied;

    private String discountCode;

    private Boolean discountCodeDefined;

    private Boolean discountDefined;

    private Double finalPrice;

    private Double finalPriceExclVat;

    private Double finalValueAddedTax;

    private String finalVat;

    private Double finalVatAmount;

    private Double finalVatBase;

    private Long id;

    private String inline;

    private String itemName;

    private String itemType;

    private String itemUrl;

    private List<PricingItem> items;

    private ProductMetaFields metaFields;

    private Integer order;

    private String parentKey;

    private Double priceExclVat;

    private PricingProduct product;

    private String snippet;

    private Map<String, Object> subsidiary;

    private String thumbnailUrl;

    private String unit;

    private Double valueAddedTax;

    private String vat;

    /**
     * The requested amount (quantity) of this item to be invoiced. For non leaf items only the integer of amount is being taken into account as it reflects number of child items. Higher precision is suitable only for leaf items.
     **/
    public Double getAmount()
    {
        return amount;
    }

    public PricingItem setAmount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    /**
     * The boolean indication whether this pricing item to include in to final price calculation or not.
     **/
    public Boolean getCheckedIn()
    {
        return checkedIn;
    }

    public PricingItem setCheckedIn( Boolean checkedIn )
    {
        this.checkedIn = checkedIn;
        return this;
    }

    /**
     * The currency alphabetic code based on the ISO 4217. If not set, the account's default currency will be set.
     **/
    public String getCurrency()
    {
        return currency;
    }

    public PricingItem setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * The boolean indication whether this pricing item has already successfully applied for discounted price.
     **/
    public Boolean getDiscountApplied()
    {
        return discountApplied;
    }

    public PricingItem setDiscountApplied( Boolean discountApplied )
    {
        this.discountApplied = discountApplied;
        return this;
    }

    /**
     * The discount code provided by the client in order to apply for price discount. If discount has been defined and there is a match, the property discountApplied will return true.
     **/
    public String getDiscountCode()
    {
        return discountCode;
    }

    public PricingItem setDiscountCode( String discountCode )
    {
        this.discountCode = discountCode;
        return this;
    }

    /**
     * The boolean indication whether associated product has defined a discount code.
     **/
    public Boolean getDiscountCodeDefined()
    {
        return discountCodeDefined;
    }

    public PricingItem setDiscountCodeDefined( Boolean discountCodeDefined )
    {
        this.discountCodeDefined = discountCodeDefined;
        return this;
    }

    /**
     * The boolean indication whether associated product has defined a discount.
     **/
    public Boolean getDiscountDefined()
    {
        return discountDefined;
    }

    public PricingItem setDiscountDefined( Boolean discountDefined )
    {
        this.discountDefined = discountDefined;
        return this;
    }

    /**
     * The final price including VAT as a result of the calculation of the item price excluding VAT (VAT base), selected VAT and amount.
     **/
    public Double getFinalPrice()
    {
        return finalPrice;
    }

    public PricingItem setFinalPrice( Double finalPrice )
    {
        this.finalPrice = finalPrice;
        return this;
    }

    /**
     * The final price excluding VAT as the result of the calculation of the item price (excluding VAT) and amount.
     **/
    public Double getFinalPriceExclVat()
    {
        return finalPriceExclVat;
    }

    public PricingItem setFinalPriceExclVat( Double finalPriceExclVat )
    {
        this.finalPriceExclVat = finalPriceExclVat;
        return this;
    }

    /**
     * The VAT value, associated with the final VAT rate.
     **/
    public Double getFinalValueAddedTax()
    {
        return finalValueAddedTax;
    }

    public PricingItem setFinalValueAddedTax( Double finalValueAddedTax )
    {
        this.finalValueAddedTax = finalValueAddedTax;
        return this;
    }

    /**
     * The context sensitive (customer country etc) VAT code, taken from the VAT rate code book. The final VAT to be used for final price calculation.
     **/
    public String getFinalVat()
    {
        return finalVat;
    }

    public PricingItem setFinalVat( String finalVat )
    {
        this.finalVat = finalVat;
        return this;
    }

    /**
     * The final amount of VAT as a result of the calculation of this item and its amount including target rounding mode.
     **/
    public Double getFinalVatAmount()
    {
        return finalVatAmount;
    }

    public PricingItem setFinalVatAmount( Double finalVatAmount )
    {
        this.finalVatAmount = finalVatAmount;
        return this;
    }

    /**
     * The final VAT base as the result of the calculation of the item price excluding VAT and amount including rounding based on the rounding mode value.
     **/
    public Double getFinalVatBase()
    {
        return finalVatBase;
    }

    public PricingItem setFinalVatBase( Double finalVatBase )
    {
        this.finalVatBase = finalVatBase;
        return this;
    }

    /**
     * The pricing item identification. Unique only with combination of the parent key.  The pricing resource might be a calculated in memory, thus no corresponding ID will be provided in this case.
     **/
    public Long getId()
    {
        return id;
    }

    public PricingItem setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * The inline item placed as a line at invoice, solely assembled by the service. Optionally, the content is language (Accept-Language) sensitive, but not for price. Will be present only for relevant 'itemType' (OrderItem , BillingItem).
     **/
    public String getInline()
    {
        return inline;
    }

    public PricingItem setInline( String inline )
    {
        this.inline = inline;
        return this;
    }

    /**
     * The product name, a very short summary that will be used at invoice. By default taken from the associated product if any. If provided by the client, it will take precedence. However, specific behaviour might be different based on the concrete type of 'itemType' property.
     **/
    public String getItemName()
    {
        return itemName;
    }

    public PricingItem setItemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    /**
     * The one of the supported item type. The type has an impact how item will be handled and might have a various configuration and properties (by subsidiary).  The root pricing item of the pricing tree structure must be one of the type: Standard, OrderItem, or BillingItem. Otherwise validation error will be returned as a bad request.
     **/
    public String getItemType()
    {
        return itemType;
    }

    public PricingItem setItemType( String itemType )
    {
        this.itemType = itemType;
        return this;
    }

    /**
     * The product page URL once associated product has been published.
     **/
    public String getItemUrl()
    {
        return itemUrl;
    }

    public PricingItem setItemUrl( String itemUrl )
    {
        this.itemUrl = itemUrl;
        return this;
    }

    /**
     * The pricing items tree. A non empty list defines a price of this item by its children as a sum of all its items. The price is calculated recursively.
     **/
    public List<PricingItem> getItems()
    {
        return items;
    }

    public PricingItem setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    /**
     * The product specific metadata fields configuration. It's read only. Values are taken from the associated product if any. Product is the master, if you need different values, change those values at product level.
     **/
    public ProductMetaFields getMetaFields()
    {
        return metaFields;
    }

    public PricingItem setMetaFields( ProductMetaFields metaFields )
    {
        this.metaFields = metaFields;
        return this;
    }

    /**
     * The order number of the item within list if defined. Managed by the client only.
     **/
    public Integer getOrder()
    {
        return order;
    }

    public PricingItem setOrder( Integer order )
    {
        this.order = order;
        return this;
    }

    /**
     * The unique identification either of the parent pricing item or parent Order (Invoice etc). Pricing item resource might be imbedded item of another pricing item infinitely.  The pricing resource might be a calculated in memory, thus no corresponding ID will be provided in this case.
     **/
    public String getParentKey()
    {
        return parentKey;
    }

    public PricingItem setParentKey( String parentKey )
    {
        this.parentKey = parentKey;
        return this;
    }

    /**
     * The product price for a single unit (meaning amount 1.0 as a quantity). The price is excluding VAT in case company is VAT payer, otherwise price is final.
     **/
    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public PricingItem setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    /**
     * The matching product from the product catalog (solely identified by ID). If found, product's properties will be used to populate this pricing item. If not found or missing, the provided pricing item properties will be processed as a non catalog product. Note, for this 'product' property only subset of the product's properties will be placed here (at least ID).
     **/
    public PricingProduct getProduct()
    {
        return product;
    }

    public PricingItem setProduct( PricingProduct product )
    {
        this.product = product;
        return this;
    }

    /**
     * The product short description, a brief overview (not appearing at invoice). The plain text only. Values taken from the associated product if any.
     **/
    public String getSnippet()
    {
        return snippet;
    }

    public PricingItem setSnippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

    /**
     * An optional subsidiary properties with single level (flat) structure - Map of values. Initial values taken from the associated product if any. Client is free to change it and manage it.
     **/
    public Map<String, Object> getSubsidiary()
    {
        return subsidiary;
    }

    public PricingItem setSubsidiary( Map<String, Object> subsidiary )
    {
        this.subsidiary = subsidiary;
        return this;
    }

    /**
     * The thumbnail URL of the associated product picture once product has been published. Served from the content delivery network (CDN).
     **/
    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public PricingItem setThumbnailUrl( String thumbnailUrl )
    {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    /**
     * The code from the billing unit codebook - unit of measure at invoice.
     **/
    public String getUnit()
    {
        return unit;
    }

    public PricingItem setUnit( String unit )
    {
        this.unit = unit;
        return this;
    }

    /**
     * The current numeric representation of the value added tax (VAT) codebook item.
     **/
    public Double getValueAddedTax()
    {
        return valueAddedTax;
    }

    public PricingItem setValueAddedTax( Double valueAddedTax )
    {
        this.valueAddedTax = valueAddedTax;
        return this;
    }

    /**
     * The VAT rate codebook value to be applied to calculate the final price of the product for domestic (seller) to domestic customer sale.
     **/
    public String getVat()
    {
        return vat;
    }

    public PricingItem setVat( String vat )
    {
        this.vat = vat;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( amount,
                checkedIn,
                currency,
                discountCode,
                itemName,
                itemType,
                items,
                order,
                priceExclVat,
                product,
                subsidiary,
                unit,
                vat
        );
    }
}
