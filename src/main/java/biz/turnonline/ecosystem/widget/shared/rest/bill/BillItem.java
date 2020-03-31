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

import java.util.Objects;

/**
 * The single row of the pricing item at a bill.
 */
public class BillItem
{
    private Double amount;

    private String currency;

    private Double finalPrice;

    private String finalVat;

    private Long id;

    private String itemName;

    private Double priceExclVat;

    private String parentKey;

    private String unit;

    public BillItem amount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    /**
     * The amount (quantity) of the single item at bill.
     **/
    public Double getAmount()
    {
        return amount;
    }

    public void setAmount( Double amount )
    {
        this.amount = amount;
    }

    public BillItem currency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * The currency alphabetic code based on the ISO 4217. If not set, the account’s default currency will be set.
     **/
    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency( String currency )
    {
        this.currency = currency;
    }

    public BillItem finalPrice( Double finalPrice )
    {
        this.finalPrice = finalPrice;
        return this;
    }

    /**
     * The final price including VAT as a result of the calculation of the item price excluding VAT (VAT base), selected VAT and amount.
     **/
    public Double getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice( Double finalPrice )
    {
        this.finalPrice = finalPrice;
    }

    public BillItem finalVat( String finalVat )
    {
        this.finalVat = finalVat;
        return this;
    }

    /**
     * The context sensitive (customer country etc) VAT code, taken from the VAT rate code book.
     **/
    public String getFinalVat()
    {
        return finalVat;
    }

    public void setFinalVat( String finalVat )
    {
        this.finalVat = finalVat;
    }

    public BillItem id( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * The bill item identification. Unique only with combination of the parent ID (see parent key).
     **/
    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public BillItem itemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    /**
     * Billing item name
     **/
    public String getItemName()
    {
        return itemName;
    }

    public void setItemName( String itemName )
    {
        this.itemName = itemName;
    }

    public BillItem priceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    /**
     * The product price for a single unit (meaning amount 1.0 as a quantity). The price is excluding VAT in case company is VAT payer, otherwise price is final.
     **/
    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public void setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
    }

    public BillItem parentKey( String parentKey )
    {
        this.parentKey = parentKey;
        return this;
    }

    /**
     * The unique identification incl. the parent Bill identification.
     **/
    public String getParentKey()
    {
        return parentKey;
    }

    public void setParentKey( String parentKey )
    {
        this.parentKey = parentKey;
    }

    public BillItem unit( String unit )
    {
        this.unit = unit;
        return this;
    }

    /**
     * The code from the billing unit codebook - unit of measure at invoice.
     **/
    public String getUnit()
    {
        return unit;
    }

    public void setUnit( String unit )
    {
        this.unit = unit;
    }


    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        BillItem billItem = ( BillItem ) o;
        return Objects.equals( this.amount, billItem.amount ) &&
                Objects.equals( this.currency, billItem.currency ) &&
                Objects.equals( this.finalPrice, billItem.finalPrice ) &&
                Objects.equals( this.finalVat, billItem.finalVat ) &&
                Objects.equals( this.id, billItem.id ) &&
                Objects.equals( this.itemName, billItem.itemName ) &&
                Objects.equals( this.priceExclVat, billItem.priceExclVat ) &&
                Objects.equals( this.parentKey, billItem.parentKey ) &&
                Objects.equals( this.unit, billItem.unit );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( amount, currency, finalPrice, finalVat, id, itemName, priceExclVat, parentKey, unit );
    }


    @Override
    public String toString()
    {
        return "class BillItem {\n" +
                "    amount: " + toIndentedString( amount ) + "\n" +
                "    currency: " + toIndentedString( currency ) + "\n" +
                "    finalPrice: " + toIndentedString( finalPrice ) + "\n" +
                "    finalVat: " + toIndentedString( finalVat ) + "\n" +
                "    id: " + toIndentedString( id ) + "\n" +
                "    itemName: " + toIndentedString( itemName ) + "\n" +
                "    priceExclVat: " + toIndentedString( priceExclVat ) + "\n" +
                "    parentKey: " + toIndentedString( parentKey ) + "\n" +
                "    unit: " + toIndentedString( unit ) + "\n" +
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
}

