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

import java.util.List;
import java.util.Map;

public final class ProductPricing
        implements RelevantNullChecker
{
    private String currency;

    private List<ProductDiscount> discounts;

    private Boolean domesticDelivery;

    private PricingItem items;

    private Double priceExclVat;

    private Map<String, Object> subsidiary;

    private List<PricingStructureTemplate> template;

    private String vat;

    private String vatEU;

    private String vatNonEU;

    public String getCurrency()
    {
        return currency;
    }

    public ProductPricing setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public List<ProductDiscount> getDiscounts()
    {
        return discounts;
    }

    public ProductPricing setDiscounts( List<ProductDiscount> discounts )
    {
        this.discounts = discounts;
        return this;
    }

    public Boolean getDomesticDelivery()
    {
        return domesticDelivery;
    }

    public ProductPricing setDomesticDelivery( Boolean domesticDelivery )
    {
        this.domesticDelivery = domesticDelivery;
        return this;
    }

    public PricingItem getItems()
    {
        return items;
    }

    public ProductPricing setItems( PricingItem items )
    {
        this.items = items;
        return this;
    }

    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public ProductPricing setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    public Map<String, Object> getSubsidiary()
    {
        return subsidiary;
    }

    public ProductPricing setSubsidiary( Map<String, Object> subsidiary )
    {
        this.subsidiary = subsidiary;
        return this;
    }

    public List<PricingStructureTemplate> getTemplate()
    {
        return template;
    }

    public ProductPricing setTemplate( List<PricingStructureTemplate> template )
    {
        this.template = template;
        return this;
    }

    public String getVat()
    {
        return vat;
    }

    public ProductPricing setVat( String vat )
    {
        this.vat = vat;
        return this;
    }

    public String getVatEU()
    {
        return vatEU;
    }

    public ProductPricing setVatEU( String vatEU )
    {
        this.vatEU = vatEU;
        return this;
    }

    public String getVatNonEU()
    {
        return vatNonEU;
    }

    public ProductPricing setVatNonEU( String vatNonEU )
    {
        this.vatNonEU = vatNonEU;
        return this;
    }

    @Override
    public boolean allNull()
    {
        // there will be always at least a default values
        return false;
    }
}
