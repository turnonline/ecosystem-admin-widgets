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

public final class VatRateRow
{
    private Double priceInclVat;

    private Double vat;

    private Double vatBase;

    private Double vatRate;

    /**
     * The total price per rate as stated at bill.
     **/
    public Double getPriceInclVat()
    {
        return priceInclVat;
    }

    public VatRateRow setPriceInclVat( Double priceInclVat )
    {
        this.priceInclVat = priceInclVat;
        return this;
    }

    /**
     * The sum of VAT per rate as stated at bill.
     **/
    public Double getVat()
    {
        return vat;
    }

    public VatRateRow setVat( Double vat )
    {
        this.vat = vat;
        return this;
    }

    /**
     * The sum of VAT base per rate as stated at bill.
     **/
    public Double getVatBase()
    {
        return vatBase;
    }

    public VatRateRow setVatBase( Double vatBase )
    {
        this.vatBase = vatBase;
        return this;
    }

    /**
     * VAT rate (in percentage) as stated at bill.
     **/
    public Double getVatRate()
    {
        return vatRate;
    }

    public VatRateRow setVatRate( Double vatRate )
    {
        this.vatRate = vatRate;
        return this;
    }
}
