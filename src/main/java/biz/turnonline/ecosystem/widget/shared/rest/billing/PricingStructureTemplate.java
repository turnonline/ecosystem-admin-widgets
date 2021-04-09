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

import java.util.List;
import java.util.Map;

public final class PricingStructureTemplate
{
    private Double amount;

    private Boolean checkedIn;

    private Integer id;

    private String itemName;

    private String itemType;

    private List<PricingStructureTemplate> items;

    private Integer order;

    private Double priceExclVat;

    private Map<String, Object> subsidiary;

    private String unit;

    /**
     * The initial amount (quantity) of the pricing item property.
     **/
    public Double getAmount()
    {
        return amount;
    }

    public PricingStructureTemplate setAmount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    /**
     * The initial state of the pricing item property. If not provided, true will be used as a default value.
     **/
    public Boolean getCheckedIn()
    {
        return checkedIn;
    }

    public PricingStructureTemplate setCheckedIn( Boolean checkedIn )
    {
        this.checkedIn = checkedIn;
        return this;
    }

    /**
     * The identification of the structure item, unique only within product.
     **/
    public Integer getId()
    {
        return id;
    }

    public PricingStructureTemplate setId( Integer id )
    {
        this.id = id;
        return this;
    }

    /**
     * The product partial item name, a very short summary that might be placed at invoice. The plain text only. Overrides the product itemName if defined.
     **/
    public String getItemName()
    {
        return itemName;
    }

    public PricingStructureTemplate setItemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    /**
     * Pricing item tree structure definition. Important note; once you provide the structure, an update on items property must always provide the full expected structure, it does not support the PATCH semantics. However if items property won't be provided at all, the backend structure remains.
     **/
    public List<PricingStructureTemplate> getItems()
    {
        return items;
    }

    public PricingStructureTemplate setItems( List<PricingStructureTemplate> items )
    {
        this.items = items;
        return this;
    }

    /**
     * The expected item's type of the pricing item property.
     **/
    public String getItemType()
    {
        return itemType;
    }

    public PricingStructureTemplate setItemType( String itemType )
    {
        this.itemType = itemType;
        return this;
    }

    /**
     * The initial order number of the item within list if defined.
     **/
    public Integer getOrder()
    {
        return order;
    }

    public PricingStructureTemplate setOrder( Integer order )
    {
        this.order = order;
        return this;
    }

    /**
     * The partial price for a single unit (meaning amount 1.0 as a quantity). The price is excluding VAT in case company is VAT payer, otherwise price is final. Only the leaf items defines the price, the rest are being calculated recursively based on the children (items property) if defined.
     **/
    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public PricingStructureTemplate setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    /**
     * An initial (and optional) subsidiary properties of the product item. A flat, single level structure (Map of values) in JSON format that will be saved as it is given. It will be returned as part of the pricing item if defined.
     **/
    public Map<String, Object> getSubsidiary()
    {
        return subsidiary;
    }

    public PricingStructureTemplate setSubsidiary( Map<String, Object> subsidiary )
    {
        this.subsidiary = subsidiary;
        return this;
    }

    /**
     * The billing unit code. If not provided, parent's item value will be used.
     **/
    public String getUnit()
    {
        return unit;
    }

    public PricingStructureTemplate setUnit( String unit )
    {
        this.unit = unit;
        return this;
    }
}
