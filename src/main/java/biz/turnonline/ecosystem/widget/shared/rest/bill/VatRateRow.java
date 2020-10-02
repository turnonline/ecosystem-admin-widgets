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

package biz.turnonline.ecosystem.widget.shared.rest.bill;

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

/**
 * Summary per VAT rate.
 */
public class VatRateRow
        implements RelevantNullChecker
{
    private Double priceInclVat;

    private Double vat;

    private Double vatBase;

    private Double vatRate;

    public VatRateRow priceInclVat( Double priceInclVat )
    {
        this.priceInclVat = priceInclVat;
        return this;
    }

    /**
     * The total price per rate as stated at bill.
     **/
    public Double getPriceInclVat()
    {
        return priceInclVat;
    }

    public void setPriceInclVat( Double priceInclVat )
    {
        this.priceInclVat = priceInclVat;
    }

    public VatRateRow vat( Double vat )
    {
        this.vat = vat;
        return this;
    }

    /**
     * The sum of VAT per rate as stated at bill.
     **/
    public Double getVat()
    {
        return vat;
    }

    public void setVat( Double vat )
    {
        this.vat = vat;
    }

    public VatRateRow vatBase( Double vatBase )
    {
        this.vatBase = vatBase;
        return this;
    }

    /**
     * The sum of VAT base per rate as stated at bill.
     **/
    public Double getVatBase()
    {
        return vatBase;
    }

    public void setVatBase( Double vatBase )
    {
        this.vatBase = vatBase;
    }

    public VatRateRow vatRate( Double vatRate )
    {
        this.vatRate = vatRate;
        return this;
    }

    /**
     * VAT rate (in percentage) as stated at bill.
     **/
    public Double getVatRate()
    {
        return vatRate;
    }

    public void setVatRate( Double vatRate )
    {
        this.vatRate = vatRate;
    }

    @Override
    public String toString()
    {
        return "class VatRateRow {\n" +
                "    priceInclVat: " + toIndentedString( priceInclVat ) + "\n" +
                "    vat: " + toIndentedString( vat ) + "\n" +
                "    vatBase: " + toIndentedString( vatBase ) + "\n" +
                "    vatRate: " + toIndentedString( vatRate ) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString( Object o )
    {
        if ( o == null )
        {
            return "null";
        }
        return o.toString().replace( "\n", "\n    " );
    }

    @Override
    public boolean allNull()
    {
        return allNull( priceInclVat, vat, vatBase, vatRate );
    }
}