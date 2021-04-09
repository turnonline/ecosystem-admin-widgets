/*
 *  Copyright (c) 2021 TurnOnline.biz s.r.o.
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

/**
 * The complete definition of the offer that is based on order.
 */
public class CompleteOffer
{
    private Date beginOn;

    private Date createdDate;

    private String currency;

    private Customer customer;

    private Long id;

    private String invoiceType;

    private List<PricingItem> items;

    private Date lastBillingDate;

    private String logo;

    private Date modificationDate;

    private Date nextBillingDate;

    private Integer numberOfDays;

    private String periodicity;

    private String picture;

    private String snippet;

    private String status;

    private Creditor supplier;

    private String title;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatAmount;

    private Double totalVatBase;

    /**
     * Proposal of the start date if applicable.
     **/
    public Date getBeginOn()
    {
        return beginOn;
    }

    public CompleteOffer setBeginOn( Date beginOn )
    {
        this.beginOn = beginOn;
        return this;
    }

    /**
     * A date when offer has been created. Populated by the service.
     **/
    public Date getCreatedDate()
    {
        return createdDate;
    }

    public CompleteOffer setCreatedDate( Date createdDate )
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

    public CompleteOffer setCurrency( String currency )
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

    public CompleteOffer setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    /**
     * The unique offer identification.
     **/
    public Long getId()
    {
        return id;
    }

    public CompleteOffer setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * Get invoiceType
     **/
    public String getInvoiceType()
    {
        return invoiceType;
    }

    public CompleteOffer setInvoiceType( String invoiceType )
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

    public CompleteOffer setItems( List<PricingItem> items )
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

    public CompleteOffer setLastBillingDate( Date lastBillingDate )
    {
        this.lastBillingDate = lastBillingDate;
        return this;
    }

    /**
     * The supplier's company logo, a full URL of the image served from CDN.
     **/
    public String getLogo()
    {
        return logo;
    }

    public CompleteOffer setLogo( String logo )
    {
        this.logo = logo;
        return this;
    }

    /**
     * The date of the last modification of offer values. Managed solely by the service. RFC 3339
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public CompleteOffer setModificationDate( Date modificationDate )
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

    public CompleteOffer setNextBillingDate( Date nextBillingDate )
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

    public CompleteOffer setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
        return this;
    }

    /**
     * The current periodicity of the offer.
     **/
    public String getPeriodicity()
    {
        return periodicity;
    }

    public CompleteOffer setPeriodicity( String periodicity )
    {
        this.periodicity = periodicity;
        return this;
    }

    /**
     * The serving URL of the picture associated with the offer. Served from the content delivery network (CDN).
     **/
    public String getPicture()
    {
        return picture;
    }

    public CompleteOffer setPicture( String picture )
    {
        this.picture = picture;
        return this;
    }

    /**
     * The short description of the offer. It might be a short description one of the product from the order. Alternatively it might be also a direct message to the target customer. The max length is set to 500 characters.
     **/
    public String getSnippet()
    {
        return snippet;
    }

    public CompleteOffer setSnippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

    /**
     * The current status of the offer.  Client is allowed to set only one of the following value: TRIALING, ACTIVE, SUSPENDED.
     **/
    public String getStatus()
    {
        return status;
    }

    public CompleteOffer setStatus( String status )
    {
        this.status = status;
        return this;
    }

    /**
     * The supplier account that represents a business at TurnOnline.biz Ecosystem.
     **/
    public Creditor getSupplier()
    {
        return supplier;
    }

    public CompleteOffer setSupplier( Creditor supplier )
    {
        this.supplier = supplier;
        return this;
    }

    /**
     * The offer title. A text that should take an user attention. The max length is set to 100 characters.
     **/
    public String getTitle()
    {
        return title;
    }

    public CompleteOffer setTitle( String title )
    {
        this.title = title;
        return this;
    }

    /**
     * The offer total price as a sum of all checked in order items and its amount including target rounding mode. Including VAT.
     **/
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public CompleteOffer setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    /**
     * The offer total price as a sum of all checked in order items and its amount including target rounding mode. The price is excluding VAT in case company is VAT payer, otherwise price is final and same as value of totalPrice property.
     **/
    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public CompleteOffer setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    /**
     * The offer total amount of VAT as a sum of all checked in order items including target rounding mode.
     **/
    public Double getTotalVatAmount()
    {
        return totalVatAmount;
    }

    public CompleteOffer setTotalVatAmount( Double totalVatAmount )
    {
        this.totalVatAmount = totalVatAmount;
        return this;
    }

    /**
     * The offer total VAT base as a sum of all checked in order items and its amount.
     **/
    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public CompleteOffer setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }
}

