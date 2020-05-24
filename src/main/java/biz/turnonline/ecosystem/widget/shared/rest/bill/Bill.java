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

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A document that represent a scanned receipt or incoming invoice. The content of a bill (receipt) interpreted as data.
 */
public class Bill
        implements RelevantNullChecker
{
    private Boolean approved;

    private String billNumber;

    private Date createdDate;

    private Date dateOfIssue;

    private Long id;

    private String description;

    private List<BillItem> items;

    private Date modificationDate;

    private Supplier supplier;

    private Double totalPrice;

    private TypeEnum type;

    private String currency;

    private List<Scan> scans;

    public Bill approved( Boolean approved )
    {
        this.approved = approved;
        return this;
    }

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

    public Bill billNumber( String billNumber )
    {
        this.billNumber = billNumber;
        return this;
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

    public Bill createdDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
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

    public Bill dateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
        return this;
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

    public Bill id( Long id )
    {
        this.id = id;
        return this;
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

    public Bill description( String description )
    {
        this.description = description;
        return this;
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

    public Bill items( List<BillItem> items )
    {
        this.items = items;
        return this;
    }

    /**
     * Get items
     **/
    public List<BillItem> getItems()
    {
        return items;
    }

    public void setItems( List<BillItem> items )
    {
        this.items = items;
    }

    public Bill modificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
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

    public Bill supplier( Supplier supplier )
    {
        this.supplier = supplier;
        return this;
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

    public Bill totalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    /**
     * The total price as a sum of all checked in bill items and its amount including target rounding mode. Including VAT.
     **/
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
    }

    public Bill type( TypeEnum type )
    {
        this.type = type;
        return this;
    }

    /**
     * Type of document
     **/
    public TypeEnum getType()
    {
        return type;
    }

    public void setType( TypeEnum type )
    {
        this.type = type;
    }

    public Bill currency( String currency )
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

    public Bill scans( List<Scan> scans )
    {
        this.scans = scans;
        return this;
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
        Bill bill = ( Bill ) o;
        return Objects.equals( this.billNumber, bill.billNumber ) &&
                Objects.equals( this.createdDate, bill.createdDate ) &&
                Objects.equals( this.dateOfIssue, bill.dateOfIssue ) &&
                Objects.equals( this.id, bill.id ) &&
                Objects.equals( this.description, bill.description ) &&
                Objects.equals( this.items, bill.items ) &&
                Objects.equals( this.modificationDate, bill.modificationDate ) &&
                Objects.equals( this.supplier, bill.supplier ) &&
                Objects.equals( this.totalPrice, bill.totalPrice ) &&
                Objects.equals( this.type, bill.type ) &&
                Objects.equals( this.currency, bill.currency ) &&
                Objects.equals( this.scans, bill.scans );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( billNumber, createdDate, dateOfIssue, id, description, items, modificationDate, supplier, totalPrice, type, currency, scans );
    }

    @Override
    public String toString()
    {
        return "class Bill {\n" +
                "    billNumber: " + toIndentedString( billNumber ) + "\n" +
                "    createdDate: " + toIndentedString( createdDate ) + "\n" +
                "    dateOfIssue: " + toIndentedString( dateOfIssue ) + "\n" +
                "    id: " + toIndentedString( id ) + "\n" +
                "    description: " + toIndentedString( description ) + "\n" +
                "    items: " + toIndentedString( items ) + "\n" +
                "    modificationDate: " + toIndentedString( modificationDate ) + "\n" +
                "    supplier: " + toIndentedString( supplier ) + "\n" +
                "    totalPrice: " + toIndentedString( totalPrice ) + "\n" +
                "    type: " + toIndentedString( type ) + "\n" +
                "    currency: " + toIndentedString( currency ) + "\n" +
                "    scans: " + toIndentedString( scans ) + "\n" +
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
        return allNull( billNumber, createdDate, dateOfIssue, id, description,
                items, modificationDate, supplier, totalPrice, type, currency, scans );
    }

    /**
     * Type of document
     */
    public enum TypeEnum
    {
        RECEIPT( "RECEIPT" ),

        INVOICE( "INVOICE" );

        private String value;

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

