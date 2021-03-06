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

    public Double getAmount()
    {
        return amount;
    }

    public PricingStructureTemplate setAmount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    public Boolean getCheckedIn()
    {
        return checkedIn;
    }

    public PricingStructureTemplate setCheckedIn( Boolean checkedIn )
    {
        this.checkedIn = checkedIn;
        return this;
    }

    public Integer getId()
    {
        return id;
    }

    public PricingStructureTemplate setId( Integer id )
    {
        this.id = id;
        return this;
    }

    public String getItemName()
    {
        return itemName;
    }

    public PricingStructureTemplate setItemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    public String getItemType()
    {
        return itemType;
    }

    public PricingStructureTemplate setItemType( String itemType )
    {
        this.itemType = itemType;
        return this;
    }

    public List<PricingStructureTemplate> getItems()
    {
        return items;
    }

    public PricingStructureTemplate setItems( List<PricingStructureTemplate> items )
    {
        this.items = items;
        return this;
    }

    public Integer getOrder()
    {
        return order;
    }

    public PricingStructureTemplate setOrder( Integer order )
    {
        this.order = order;
        return this;
    }

    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public PricingStructureTemplate setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    public Map<String, Object> getSubsidiary()
    {
        return subsidiary;
    }

    public PricingStructureTemplate setSubsidiary( Map<String, Object> subsidiary )
    {
        this.subsidiary = subsidiary;
        return this;
    }

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
