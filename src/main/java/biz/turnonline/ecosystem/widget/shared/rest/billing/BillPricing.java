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

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

import java.util.List;

public final class BillPricing
        implements RelevantNullChecker
{
    private List<PricingItem> items;

    private String rounding;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatAmount;

    private Double totalVatBase;

    /**
     * The list of billing items placed at bill (pricing item with 'itemType' property value BillingItem, OrderItem or Standard for new item). Otherwise validation error will be returned as a bad request.  Once processed by the service it will be always returned as a flat structure of billing items (no tree), a simple list of items listed at bill.   All of the values as a snapshot of the calculated values at the time of the billing item creation. Billing item values might change while invoice has a status NEW.
     **/
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

    /**
     * The invoice total amount of VAT as a sum of all checked in billing items including target rounding mode.
     **/
    public Double getTotalVatAmount()
    {
        return totalVatAmount;
    }

    public BillPricing setTotalVatAmount( Double totalVatAmount )
    {
        this.totalVatAmount = totalVatAmount;
        return this;
    }

    /**
     * The invoice total VAT base as a sum of all billing items and its amount including target rounding mode.
     **/
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
