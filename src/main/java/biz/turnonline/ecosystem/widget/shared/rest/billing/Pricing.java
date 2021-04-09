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

    private Double totalVatAmount;

    private Double totalVatBase;

    /**
     * The boolean indication whether these pricing items are subject to the reverse charge - VAT to be accounted for by the recipient as per Article 196 of Council Directive 2006/112/EC.
     **/
    public Boolean getAppliedReverseCharge()
    {
        return appliedReverseCharge;
    }

    public Pricing setAppliedReverseCharge( Boolean appliedReverseCharge )
    {
        this.appliedReverseCharge = appliedReverseCharge;
        return this;
    }

    /**
     * The customer details mainly for the purpose of the reverse charge evaluation.
     **/
    public PricingCustomer getCustomer()
    {
        return customer;
    }

    public Pricing setCustomer( PricingCustomer customer )
    {
        this.customer = customer;
        return this;
    }

    /**
     * True if any of the pricing item has successfully applied for discounted price.
     **/
    public Boolean getDiscountApplied()
    {
        return discountApplied;
    }

    public Pricing setDiscountApplied( Boolean discountApplied )
    {
        this.discountApplied = discountApplied;
        return this;
    }

    /**
     * The boolean indication whether any of the pricing item has defined a discount.
     **/
    public Boolean getDiscountDefined()
    {
        return discountDefined;
    }

    public Pricing setDiscountDefined( Boolean discountDefined )
    {
        this.discountDefined = discountDefined;
        return this;
    }

    /**
     * The list of the pricing items to be considered for calculation. The pricing items represents a tree and price is calculated recursively.
     **/
    public List<PricingItem> getItems()
    {
        return items;
    }

    public Pricing setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    /**
     * The total price including VAT as it would be if no discount would be applied. If property discountApplied is false or missing, the sum will be the same as totalPrice.
     **/
    public Double getNoDiscountTotalPrice()
    {
        return noDiscountTotalPrice;
    }

    public Pricing setNoDiscountTotalPrice( Double noDiscountTotalPrice )
    {
        this.noDiscountTotalPrice = noDiscountTotalPrice;
        return this;
    }

    /**
     * The total price excluding VAT as it would be if no discount would be applied. If property discountApplied is false or missing, the sum will be the same as totalPriceExclVat.
     **/
    public Double getNoDiscountTotalPriceExclVat()
    {
        return noDiscountTotalPriceExclVat;
    }

    public Pricing setNoDiscountTotalPriceExclVat( Double noDiscountTotalPriceExclVat )
    {
        this.noDiscountTotalPriceExclVat = noDiscountTotalPriceExclVat;
        return this;
    }

    /**
     * The total price as a sum of all checked in pricing items and its amount. Including VAT.
     **/
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public Pricing setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    /**
     * The total price as a sum of all checked in pricing items and its amount. The price is excluding VAT in case company is VAT payer, otherwise price is final and same as value of totalPrice property.
     **/
    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public Pricing setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    /**
     * The total VAT as a result of the calculation of all checked in pricing items and its amount including target rounding mode.
     **/
    public Double getTotalVatAmount()
    {
        return totalVatAmount;
    }

    public Pricing setTotalVatAmount( Double totalVatAmount )
    {
        this.totalVatAmount = totalVatAmount;
        return this;
    }

    /**
     * The total VAT base as a result of the calculation of all checked in pricing items and its amount including target rounding mode.
     **/
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
