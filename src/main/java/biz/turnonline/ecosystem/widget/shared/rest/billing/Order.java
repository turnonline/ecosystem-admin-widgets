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

import java.util.Date;
import java.util.List;

public final class Order
        implements RelevantNullChecker
{
    private Date beginOn;

    private Date createdDate;

    private String currency;

    private Customer customer;

    private Long id;

    private List<Invoice> invoices;

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

    public Order setBeginOn( Date beginOn )
    {
        this.beginOn = beginOn;
        return this;
    }

    /**
     * A date when order has been created. Populated by the service.
     **/
    public Date getCreatedDate()
    {
        return createdDate;
    }

    public Order setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * The currency alphabetic code based on the ISO 4217. The value is being based on the order item that is first within the list. If list is empty, none currency will be set.
     **/
    public String getCurrency()
    {
        return currency;
    }

    public Order setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * A customer to be invoiced.
     **/
    public Customer getCustomer()
    {
        return customer;
    }

    public Order setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    public void setCustomerIf( Customer customer )
    {
        setIfNotAllNull( this::setCustomer, customer );
    }

    /**
     * The unique order identification.
     **/
    public Long getId()
    {
        return id;
    }

    public Order setId( Long id )
    {
        this.id = id;
        return this;
    }


    /**
     * List of already issued invoices based on this Order, where number of records is limited by value from the query parameter. By default no invoices will be included in the response until the query parameter 'invoices' will be present with a positive integer value (check whether it's supported by endpoint). This number represents a limit of max number of expected invoices to be included in the result.  If no invoice yet has been issued, the property 'invoices' will be missing in the result whatever was the value of query parameter.  Ordered by invoice modification date (descending).
     **/
    public List<Invoice> getInvoices()
    {
        return invoices;
    }

    public Order setInvoices( List<Invoice> invoices )
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

    public Order setInvoiceType( String invoiceType )
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

    public Order setItems( List<PricingItem> items )
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

    public Order setLastBillingDate( Date lastBillingDate )
    {
        this.lastBillingDate = lastBillingDate;
        return this;
    }

    /**
     * The date of the last modification of order values. Managed solely by the service. RFC 3339
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public Order setModificationDate( Date modificationDate )
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

    public Order setNextBillingDate( Date nextBillingDate )
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

    public Order setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
        return this;
    }

    /**
     * The current periodicity of the order.
     **/
    public String getPeriodicity()
    {
        return periodicity;
    }

    public Order setPeriodicity( String periodicity )
    {
        this.periodicity = periodicity;
        return this;
    }

    /**
     * The current status of the order.  Client is allowed to set only one of the following value: TRIALING, ACTIVE, SUSPENDED.
     **/
    public String getStatus()
    {
        return status;
    }

    public Order setStatus( String status )
    {
        this.status = status;
        return this;
    }

    /**
     * The order total price as a sum of all checked in order items and its amount including target rounding mode. Including VAT.
     **/
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public Order setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    /**
     * The order total price as a sum of all checked in order items and its amount including target rounding mode. The price is excluding VAT in case company is VAT payer, otherwise price is final and same as value of totalPrice property.
     **/
    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public Order setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    /**
     * The order total amount of VAT as a sum of all checked in order items including target rounding mode.
     **/
    public Double getTotalVatAmount()
    {
        return totalVatAmount;
    }

    public Order setTotalVatAmount( Double totalVatAmount )
    {
        this.totalVatAmount = totalVatAmount;
        return this;
    }

    /**
     * The order total VAT base as a sum of all checked in order items and its amount.
     **/
    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public Order setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( beginOn,
                customer,
                invoiceType,
                items,
                numberOfDays,
                periodicity,
                status
        );
    }

    /**
     * Order statuses
     */
    public enum Status
    {
        /**
         * A service or subscription in trialing state, a trial run.
         */
        TRIALING,

        /**
         * A new or active order. It is not in a trial and there is no overdue (recurring) payment.
         */
        ACTIVE,

        /**
         * Either creditor or debtor has paused the execution.
         */
        SUSPENDED,

        /**
         * Set by system if there is an overdue payment.
         * The most recent payment for some reason has failed or debtor has refused the purchase order.
         */
        ISSUE,

        /**
         * A non-recurring purchase order that has been finished and is ready to be delivered.
         * Generally it means an ordered product has been paid.
         */
        FINISHED,

        /**
         * Order has been shipped/picked up, and receipt is confirmed.
         * The client has paid for their products, thus the order is being considered completed.
         * A subscription that has been expired due to running its normal life cycle.
         */
        COMPLETED
    }
}
