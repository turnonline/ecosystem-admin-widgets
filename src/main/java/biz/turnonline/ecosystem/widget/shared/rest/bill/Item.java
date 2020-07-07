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

/**
 * The single row (optional) of the item.
 */
public class Item
{
    private Double finalPrice;

    private Double finalVatAmount;

    private Long id;

    private String itemName;

    private String parentKey;

    private Double priceExclVat;

    private String vat;

    public Item finalPrice( Double finalPrice )
    {
        this.finalPrice = finalPrice;
        return this;
    }

    /**
     * The final price including VAT.
     **/
    public Double getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice( Double finalPrice )
    {
        this.finalPrice = finalPrice;
    }

    public Item finalVatAmount( Double finalVatAmount )
    {
        this.finalVatAmount = finalVatAmount;
        return this;
    }

    /**
     * The final amount of VAT.
     **/
    public Double getFinalVatAmount()
    {
        return finalVatAmount;
    }

    public void setFinalVatAmount( Double finalVatAmount )
    {
        this.finalVatAmount = finalVatAmount;
    }

    public Item id( Long id )
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

    public Item itemName( String itemName )
    {
        this.itemName = itemName;
        return this;
    }

    /**
     * Single item name
     **/
    public String getItemName()
    {
        return itemName;
    }

    public void setItemName( String itemName )
    {
        this.itemName = itemName;
    }

    public Item parentKey( String parentKey )
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

    public Item priceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    /**
     * The price excluding VAT in case company is VAT payer, otherwise price is final.
     **/
    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public void setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
    }

    public Item vat( String vat )
    {
        this.vat = vat;
        return this;
    }

    /**
     * The VAT code taken from the VAT rate code book.
     **/
    public String getVat()
    {
        return vat;
    }

    public void setVat( String vat )
    {
        this.vat = vat;
    }

    @Override
    public String toString()
    {
        return "class Item {\n" +
                "    finalPrice: " + toIndentedString( finalPrice ) + "\n" +
                "    finalVatAmount: " + toIndentedString( finalVatAmount ) + "\n" +
                "    id: " + toIndentedString( id ) + "\n" +
                "    itemName: " + toIndentedString( itemName ) + "\n" +
                "    parentKey: " + toIndentedString( parentKey ) + "\n" +
                "    priceExclVat: " + toIndentedString( priceExclVat ) + "\n" +
                "    vat: " + toIndentedString( vat ) + "\n" +
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
