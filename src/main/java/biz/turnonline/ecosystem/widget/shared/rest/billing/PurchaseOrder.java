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

import java.util.Date;
import java.util.List;

public final class PurchaseOrder
{
    private Date beginOn;

    private Date createdDate;

    private Creditor creditor;

    private String currency;

    private Customer customer;

    private Long id;

    private String invoiceType;

    private List<IncomingInvoice> invoices;

    private List<PricingItem> items;

    private Date lastBillingDate;

    private Date modificationDate;

    private Date nextBillingDate;

    private Integer numberOfDays;

    private String periodicity;

    private String status;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatBase;

    public Date getBeginOn()
    {
        return beginOn;
    }

    public PurchaseOrder setBeginOn( Date beginOn )
    {
        this.beginOn = beginOn;
        return this;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public PurchaseOrder setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    public Creditor getCreditor()
    {
        return creditor;
    }

    public PurchaseOrder setCreditor( Creditor creditor )
    {
        this.creditor = creditor;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public PurchaseOrder setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public PurchaseOrder setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public PurchaseOrder setId( Long id )
    {
        this.id = id;
        return this;
    }

    public String getInvoiceType()
    {
        return invoiceType;
    }

    public PurchaseOrder setInvoiceType( String invoiceType )
    {
        this.invoiceType = invoiceType;
        return this;
    }

    public List<IncomingInvoice> getInvoices()
    {
        return invoices;
    }

    public PurchaseOrder setInvoices( List<IncomingInvoice> invoices )
    {
        this.invoices = invoices;
        return this;
    }

    public List<PricingItem> getItems()
    {
        return items;
    }

    public PurchaseOrder setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    public Date getLastBillingDate()
    {
        return lastBillingDate;
    }

    public PurchaseOrder setLastBillingDate( Date lastBillingDate )
    {
        this.lastBillingDate = lastBillingDate;
        return this;
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public PurchaseOrder setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public Date getNextBillingDate()
    {
        return nextBillingDate;
    }

    public PurchaseOrder setNextBillingDate( Date nextBillingDate )
    {
        this.nextBillingDate = nextBillingDate;
        return this;
    }

    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    public PurchaseOrder setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public String getPeriodicity()
    {
        return periodicity;
    }

    public PurchaseOrder setPeriodicity( String periodicity )
    {
        this.periodicity = periodicity;
        return this;
    }

    public String getStatus()
    {
        return status;
    }

    public PurchaseOrder setStatus( String status )
    {
        this.status = status;
        return this;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public PurchaseOrder setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public PurchaseOrder setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public PurchaseOrder setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }

    /**
     * Returns the formatted name of the purchase order, a message for end user.
     * The composition of the creditor business name and the purchase order ID.
     *
     * @return the purchase order formatted name
     */
    public String formattedName()
    {
        StringBuilder builder = new StringBuilder();

        if ( creditor != null && creditor.getBusinessName() != null )
        {
            builder.append( creditor.getBusinessName() );
        }

        if ( id != null )
        {
            if ( builder.length() == 0 )
            {
                builder.append( id );
            }
            else
            {
                builder.append( " (" );
                builder.append( id );
                builder.append( ")" );
            }
        }

        return builder.toString();
    }
}
