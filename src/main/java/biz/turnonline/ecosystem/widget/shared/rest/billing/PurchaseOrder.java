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

    private List<IncomingInvoice> invoices;

    private String invoiceType;

    private List<PricingItem> items;

    private Date lastBillingDate;

    private Date modificationDate;

    private Date nextBillingDate;

    private Integer numberOfDays;

    private String periodicity;

    private String status;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatAmount;

    private Double totalVatBase;

    /**
     * The date of first invoice issue. Might be selected by the user.
     **/
    public Date getBeginOn()
    {
        return beginOn;
    }

    public PurchaseOrder setBeginOn( Date beginOn )
    {
        this.beginOn = beginOn;
        return this;
    }

    /**
     * A date when purchase order has been created. Populated by the service.
     **/
    public Date getCreatedDate()
    {
        return createdDate;
    }

    public PurchaseOrder setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * The account that represents a business at TurnOnline.biz Ecosystem to whom money will be credited.
     **/
    public Creditor getCreditor()
    {
        return creditor;
    }

    public PurchaseOrder setCreditor( Creditor creditor )
    {
        this.creditor = creditor;
        return this;
    }

    /**
     * The currency alphabetic code based on the ISO 4217. The value is being based on the order item that is first within the list. If list is empty, none currency will be set.
     **/
    public String getCurrency()
    {
        return currency;
    }

    public PurchaseOrder setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * Invoiced customer.
     **/
    public Customer getCustomer()
    {
        return customer;
    }

    public PurchaseOrder setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    /**
     * The unique purchase order identification.
     **/
    public Long getId()
    {
        return id;
    }

    public PurchaseOrder setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * List of already issued invoices based on this Order, where number of records is limited by value from the query parameter. By default no invoices will be included in the response until the query parameter 'invoices' will be present with a positive integer value. This number represents a limit of max number of expected incoming invoices to be included in the result.  If no incoming invoice yet has been issued, the property 'invoices' will be missing in the result whatever was the value of query parameter.  Ordered by incoming invoice modification date (descending).
     **/
    public List<IncomingInvoice> getInvoices()
    {
        return invoices;
    }

    public PurchaseOrder setInvoices( List<IncomingInvoice> invoices )
    {
        this.invoices = invoices;
        return this;
    }

    /**
     * Get invoiceType
     **/
    public String getInvoiceType()
    {
        return invoiceType;
    }

    public PurchaseOrder setInvoiceType( String invoiceType )
    {
        this.invoiceType = invoiceType;
        return this;
    }

    /**
     * The list of order items to be placed at invoice. Taken either as a product from the product catalog or a custom definition.  The root pricing item of the pricing tree structure must be either type of OrderItem or Standard (PricingItem ‘itemType’ property value). Otherwise validation error will be returned as a bad request.
     **/
    public List<PricingItem> getItems()
    {
        return items;
    }

    public PurchaseOrder setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    /**
     * The last billing date. An invoicing that has been already executed.  Set by the service.
     **/
    public Date getLastBillingDate()
    {
        return lastBillingDate;
    }

    public PurchaseOrder setLastBillingDate( Date lastBillingDate )
    {
        this.lastBillingDate = lastBillingDate;
        return this;
    }

    /**
     * The date of the last modification of purchase order values. Managed solely by the service. RFC 3339
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public PurchaseOrder setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    /**
     * The next (future) billing date. A concrete value is being based on the periodicity and evaluated by the service.
     **/
    public Date getNextBillingDate()
    {
        return nextBillingDate;
    }

    public PurchaseOrder setNextBillingDate( Date nextBillingDate )
    {
        this.nextBillingDate = nextBillingDate;
        return this;
    }

    /**
     * The number of days to calculate due date. An user defined value.
     **/
    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    public PurchaseOrder setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
        return this;
    }

    /**
     * The current periodicity of the purchase order.
     **/
    public String getPeriodicity()
    {
        return periodicity;
    }

    public PurchaseOrder setPeriodicity( String periodicity )
    {
        this.periodicity = periodicity;
        return this;
    }

    /**
     * The current status of the purchase order.  Client is allowed to set only one of the following value: TRIALING, ACTIVE, SUSPENDED.
     **/
    public String getStatus()
    {
        return status;
    }

    public PurchaseOrder setStatus( String status )
    {
        this.status = status;
        return this;
    }

    /**
     * The purchase order total price as a sum of all checked in order items and its amount including target rounding mode. Including VAT.
     **/
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public PurchaseOrder setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    /**
     * The purchase order total price as a sum of all checked in order items and its amount including target rounding mode. The price is excluding VAT in case company is VAT payer, otherwise price is final and same as value of totalPrice property.
     **/
    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public PurchaseOrder setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    /**
     * The purchase order total amount of VAT as a sum of all checked in order items including target rounding mode.
     **/
    public Double getTotalVatAmount()
    {
        return totalVatAmount;
    }

    public PurchaseOrder setTotalVatAmount( Double totalVatAmount )
    {
        this.totalVatAmount = totalVatAmount;
        return this;
    }

    /**
     * The purchase order total VAT base as a sum of all checked in order items and its amount.
     **/
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
