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

public final class BillPricing
        implements RelevantNullChecker
{
    private List<PricingItem> items;

    private String rounding;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatBase;

    public List<PricingItem> getItems()
    {
        return items;
    }

    public BillPricing setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    public String getRounding()
    {
        return rounding;
    }

    public BillPricing setRounding( String rounding )
    {
        this.rounding = rounding;
        return this;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public BillPricing setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public BillPricing setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public BillPricing setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( items, rounding );
    }
}
