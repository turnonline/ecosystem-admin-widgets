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

public final class ExpenditurePricing
{
    private String currency;

    private List<PricingItem> items;

    private Double totalPrice;

    private Double totalVat;

    private Double totalVatBase;

    private List<VatRateRow> vatRows;

    public String getCurrency()
    {
        return currency;
    }

    public ExpenditurePricing setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public List<PricingItem> getItems()
    {
        return items;
    }

    public ExpenditurePricing setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public ExpenditurePricing setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    public Double getTotalVat()
    {
        return totalVat;
    }

    public ExpenditurePricing setTotalVat( Double totalVat )
    {
        this.totalVat = totalVat;
        return this;
    }

    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public ExpenditurePricing setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }

    public List<VatRateRow> getVatRows()
    {
        return vatRows;
    }

    public ExpenditurePricing setVatRows( List<VatRateRow> vatRows )
    {
        this.vatRows = vatRows;
        return this;
    }
}
