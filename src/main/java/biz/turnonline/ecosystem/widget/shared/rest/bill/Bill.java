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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

import java.util.Date;
import java.util.List;

/**
 * A document that represent a scanned receipt or incoming invoice. The content of a bill (receipt) interpreted as data.
 */
public class Bill
        implements RelevantNullChecker
{
    private Boolean approved;

    private String billNumber;

    private Date createdDate;

    private String currency;

    private Date dateOfIssue;

    private String description;

    private Long id;

    private List<Item> items;

    private Date modificationDate;

    private Supplier supplier;

    private Double totalPrice;

    private Double totalVat;

    private Double totalVatBase;

    private Long transactionId;

    private TypeEnum type;

    private List<Scan> scans;

    private List<VatRateRow> vatRows;

    /**
     * The indication whether the bill has been approved to be sent to accountant or not.
     **/
    @JsonProperty( "approved" )
    public Boolean isApproved()
    {
        return approved;
    }

    public void setApproved( Boolean approved )
    {
        this.approved = approved;
    }

    /**
     * Bill (receipt) number, or in case of incoming invoice an invoice number
     **/
    public String getBillNumber()
    {
        return billNumber;
    }

    public void setBillNumber( String billNumber )
    {
        this.billNumber = billNumber;
    }

    /**
     * A date when bill record has been created. Managed solely by the service. RFC 3339
     **/
    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
    }

    /**
     * The date when the cash register document has been issued. If not provided, the current date will be used. RFC 3339
     **/
    public Date getDateOfIssue()
    {
        return dateOfIssue;
    }

    public void setDateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
    }

    /**
     * The unique bill identification.
     **/
    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * Bill name
     **/
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * Get items
     **/
    public List<Item> getItems()
    {
        return items;
    }

    public void setItems( List<Item> items )
    {
        this.items = items;
    }

    /**
     * The date of the last modification of the bill resource values. Managed solely by the service. RFC 3339
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public void setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
    }

    /**
     * Get supplier
     **/
    public Supplier getSupplier()
    {
        return supplier;
    }

    public void setSupplier( Supplier supplier )
    {
        this.supplier = supplier;
    }

    /**
     * The total price as stated at bill.
     **/
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
    }

    /**
     * The total VAT as stated at bill (in case the supplier is a VAT payer).
     **/
    public Double getTotalVat()
    {
        return totalVat;
    }

    public void setTotalVat( Double totalVat )
    {
        this.totalVat = totalVat;
    }

    /**
     * The total VAT base as stated at bill (in case the supplier is a VAT payer).
     **/
    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public void setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
    }

    /**
     * The identification of the transaction within Product Billing service associated with this bill if it has been already matched.
     **/
    public Long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId( Long transactionId )
    {
        this.transactionId = transactionId;
    }

    /**
     * Type of the bill document.
     **/
    public TypeEnum getType()
    {
        return type;
    }

    public void setType( TypeEnum type )
    {
        this.type = type;
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

    /**
     * The list of scans associated with this bill.
     **/
    public List<Scan> getScans()
    {
        return scans;
    }

    public void setScans( List<Scan> scans )
    {
        this.scans = scans;
    }

    /**
     * Summary per VAT rate.
     **/
    public List<VatRateRow> getVatRows()
    {
        return vatRows;
    }

    public void setVatRows( List<VatRateRow> vatRows )
    {
        this.vatRows = vatRows;
    }

    @Override
    public String toString()
    {
        return "class Bill {\n" +
                "    billNumber: " + toIndentedString( billNumber ) + "\n" +
                "    createdDate: " + toIndentedString( createdDate ) + "\n" +
                "    currency: " + toIndentedString( currency ) + "\n" +
                "    dateOfIssue: " + toIndentedString( dateOfIssue ) + "\n" +
                "    id: " + toIndentedString( id ) + "\n" +
                "    description: " + toIndentedString( description ) + "\n" +
                "    items: " + toIndentedString( items ) + "\n" +
                "    modificationDate: " + toIndentedString( modificationDate ) + "\n" +
                "    supplier: " + toIndentedString( supplier ) + "\n" +
                "    totalPrice: " + toIndentedString( totalPrice ) + "\n" +
                "    totalVat: " + toIndentedString( totalVat ) + "\n" +
                "    totalVatBase: " + toIndentedString( totalVatBase ) + "\n" +
                "    type: " + toIndentedString( type ) + "\n" +
                "    scans: " + toIndentedString( scans ) + "\n" +
                "    transactionId: " + toIndentedString( transactionId ) + "\n" +
                "    vatRows: " + toIndentedString( vatRows ) + "\n" +
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

    public boolean setSupplierIf( Supplier supplier )
    {
        return setIfNotAllNull( this::setSupplier, supplier );
    }

    @Override
    public boolean allNull()
    {
        return allNull( billNumber, createdDate, currency, dateOfIssue, id, description, items, modificationDate,
                supplier, totalPrice, totalVat, totalVatBase, type, scans, transactionId, vatRows );
    }

    /**
     * Type of the bill document.
     */
    public enum TypeEnum
    {
        RECEIPT( "RECEIPT" ),

        INVOICE( "INVOICE" );

        private final String value;

        TypeEnum( String value )
        {
            this.value = value;
        }

        public static TypeEnum fromValue( String text )
        {
            for ( TypeEnum b : TypeEnum.values() )
            {
                if ( String.valueOf( b.value ).equals( text ) )
                {
                    return b;
                }
            }
            return null;
        }

        @Override
        public String toString()
        {
            return String.valueOf( value );
        }
    }
}

