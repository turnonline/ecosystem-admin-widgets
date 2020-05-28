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

    public Double getAmount()
    {
        return amount;
    }

    public PricingItem setAmount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    public Boolean getCheckedIn()
    {
        return checkedIn;
    }

    public PricingItem setCheckedIn( Boolean checkedIn )
    {
        this.checkedIn = checkedIn;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public PricingItem setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public Boolean getDiscountApplied()
    {
        return discountApplied;
    }

    public PricingItem setDiscountApplied( Boolean discountApplied )
    {
        this.discountApplied = discountApplied;
        return this;
    }

    public String getDiscountCode()
    {
        return discountCode;
    }

    public PricingItem setDiscountCode( String discountCode )
    {
        this.discountCode = discountCode;
        return this;
    }

    public Boolean getDiscountCodeDefined()
    {
        return discountCodeDefined;
    }

    public PricingItem setDiscountCodeDefined( Boolean discountCodeDefined )
    {
        this.discountCodeDefined = discountCodeDefined;
        return this;
    }

    public Boolean getDiscountDefined()
    {
        return discountDefined;
    }

    public PricingItem setDiscountDefined( Boolean discountDefined )
    {
        this.discountDefined = discountDefined;
        return this;
    }

    public Double getFinalPrice()
    {
        return finalPrice;
    }

    public PricingItem setFinalPrice( Double finalPrice )
    {
        this.finalPrice = finalPrice;
        return this;
    }

    public Double getFinalPriceExclVat()
    {
        return finalPriceExclVat;
    }

    public PricingItem setFinalPriceExclVat( Double finalPriceExclVat )
    {
        this.finalPriceExclVat = finalPriceExclVat;
        return this;
    }

    public Double getFinalValueAddedTax()
    {
        return finalValueAddedTax;
    }

    public PricingItem setFinalValueAddedTax( Double finalValueAddedTax )
    {
        this.finalValueAddedTax = finalValueAddedTax;
        return this;
    }

    public String getFinalVat()
    {
        return finalVat;
    }

    public PricingItem setFinalVat( String finalVat )
    {
        this.finalVat = finalVat;
        return this;
    }

    public Double getFinalVatBase()
    {
        return finalVatBase;
    }

    public PricingItem setFinalVatBase( Double finalVatBase )
    {
        this.finalVatBase = finalVatBase;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public PricingItem setId( Long id )
    {
        this.id = id;
        return this;
    }

    public String getInline()
    {
        return inline;
    }

    public PricingItem setInline( String inline )
    {
        this.inline = inline;
        return this;
    }

    public String getItemName()
    {
        return itemName;
    }

    public PricingItem setItemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    public String getItemType()
    {
        return itemType;
    }

    public PricingItem setItemType( String itemType )
    {
        this.itemType = itemType;
        return this;
    }

    public String getItemUrl()
    {
        return itemUrl;
    }

    public PricingItem setItemUrl( String itemUrl )
    {
        this.itemUrl = itemUrl;
        return this;
    }

    public List<PricingItem> getItems()
    {
        return items;
    }

    public PricingItem setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    public ProductMetaFields getMetaFields()
    {
        return metaFields;
    }

    public PricingItem setMetaFields( ProductMetaFields metaFields )
    {
        this.metaFields = metaFields;
        return this;
    }

    public Integer getOrder()
    {
        return order;
    }

    public PricingItem setOrder( Integer order )
    {
        this.order = order;
        return this;
    }

    public String getParentKey()
    {
        return parentKey;
    }

    public PricingItem setParentKey( String parentKey )
    {
        this.parentKey = parentKey;
        return this;
    }

    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public PricingItem setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    public PricingProduct getProduct()
    {
        return product;
    }

    public PricingItem setProduct( PricingProduct product )
    {
        this.product = product;
        return this;
    }

    public String getSnippet()
    {
        return snippet;
    }

    public PricingItem setSnippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

    public Map<String, Object> getSubsidiary()
    {
        return subsidiary;
    }

    public PricingItem setSubsidiary( Map<String, Object> subsidiary )
    {
        this.subsidiary = subsidiary;
        return this;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public PricingItem setThumbnailUrl( String thumbnailUrl )
    {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public String getUnit()
    {
        return unit;
    }

    public PricingItem setUnit( String unit )
    {
        this.unit = unit;
        return this;
    }

    public Double getValueAddedTax()
    {
        return valueAddedTax;
    }

    public PricingItem setValueAddedTax( Double valueAddedTax )
    {
        this.valueAddedTax = valueAddedTax;
        return this;
    }

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
