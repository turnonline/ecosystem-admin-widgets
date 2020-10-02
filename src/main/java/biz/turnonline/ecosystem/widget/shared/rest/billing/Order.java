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

    private String invoiceType;

    private List<Invoice> invoices;

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

    public Order setBeginOn( Date beginOn )
    {
        this.beginOn = beginOn;
        return this;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public Order setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public Order setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

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

    public Long getId()
    {
        return id;
    }

    public Order setId( Long id )
    {
        this.id = id;
        return this;
    }

    public String getInvoiceType()
    {
        return invoiceType;
    }

    public Order setInvoiceType( String invoiceType )
    {
        this.invoiceType = invoiceType;
        return this;
    }

    public List<Invoice> getInvoices()
    {
        return invoices;
    }

    public Order setInvoices( List<Invoice> invoices )
    {
        this.invoices = invoices;
        return this;
    }

    public List<PricingItem> getItems()
    {
        return items;
    }

    public Order setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    public Date getLastBillingDate()
    {
        return lastBillingDate;
    }

    public Order setLastBillingDate( Date lastBillingDate )
    {
        this.lastBillingDate = lastBillingDate;
        return this;
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public Order setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public Date getNextBillingDate()
    {
        return nextBillingDate;
    }

    public Order setNextBillingDate( Date nextBillingDate )
    {
        this.nextBillingDate = nextBillingDate;
        return this;
    }

    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    public Order setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public String getPeriodicity()
    {
        return periodicity;
    }

    public Order setPeriodicity( String periodicity )
    {
        this.periodicity = periodicity;
        return this;
    }

    public String getStatus()
    {
        return status;
    }

    public Order setStatus( String status )
    {
        this.status = status;
        return this;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public Order setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public Order setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

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

    public enum Status
    {
        /**
         * A service or subscription in trialing state, a trial run.
         */
        TRIALING,

        /**
         * A new or active order. It is not in a trial and there is no overdue payment.
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
         * A non recurring purchase order that has been completed. Generally it means an ordered product has been paid.
         * A subscription that has been expired due to running its normal life cycle.
         */
        FINISHED
    }
}
