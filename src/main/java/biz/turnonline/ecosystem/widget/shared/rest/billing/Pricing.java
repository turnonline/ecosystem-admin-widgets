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

import java.util.List;

public final class Pricing
{
    private Boolean appliedReverseCharge;

    private PricingCustomer customer;

    private Boolean discountApplied;

    private Boolean discountDefined;

    private List<PricingItem> items;

    private Double noDiscountTotalPrice;

    private Double noDiscountTotalPriceExclVat;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatBase;

    public Boolean getAppliedReverseCharge()
    {
        return appliedReverseCharge;
    }

    public Pricing setAppliedReverseCharge( Boolean appliedReverseCharge )
    {
        this.appliedReverseCharge = appliedReverseCharge;
        return this;
    }

    public PricingCustomer getCustomer()
    {
        return customer;
    }

    public Pricing setCustomer( PricingCustomer customer )
    {
        this.customer = customer;
        return this;
    }

    public Boolean getDiscountApplied()
    {
        return discountApplied;
    }

    public Pricing setDiscountApplied( Boolean discountApplied )
    {
        this.discountApplied = discountApplied;
        return this;
    }

    public Boolean getDiscountDefined()
    {
        return discountDefined;
    }

    public Pricing setDiscountDefined( Boolean discountDefined )
    {
        this.discountDefined = discountDefined;
        return this;
    }

    public List<PricingItem> getItems()
    {
        return items;
    }

    public Pricing setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    public Double getNoDiscountTotalPrice()
    {
        return noDiscountTotalPrice;
    }

    public Pricing setNoDiscountTotalPrice( Double noDiscountTotalPrice )
    {
        this.noDiscountTotalPrice = noDiscountTotalPrice;
        return this;
    }

    public Double getNoDiscountTotalPriceExclVat()
    {
        return noDiscountTotalPriceExclVat;
    }

    public Pricing setNoDiscountTotalPriceExclVat( Double noDiscountTotalPriceExclVat )
    {
        this.noDiscountTotalPriceExclVat = noDiscountTotalPriceExclVat;
        return this;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public Pricing setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public Pricing setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public Pricing setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }
}
