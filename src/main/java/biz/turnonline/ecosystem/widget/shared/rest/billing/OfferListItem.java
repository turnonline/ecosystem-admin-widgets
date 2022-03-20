/*
 *  Copyright (c) 2022 TurnOnline.biz s.r.o.
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
 */

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import java.util.Date;

/**
 * Model definition for OfferListItem.
 */
public class OfferListItem
{
    private String currency;

    private Customer customer;

    private Date expiration;

    private Long id;

    private String logo;

    private Date modificationDate;

    private Boolean multipleRecipients;

    private String snippet;

    private Sponsor sponsor;

    private Creditor supplier;

    private String thumbnail;

    private String title;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatAmount;

    private Double totalVatBase;

    /**
     * The currency alphabetic code based on the ISO 4217.
     **/
    public String getCurrency()
    {
        return currency;
    }

    public OfferListItem setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * The identification of the customer as a recipient of the offer.
     **/
    public Customer getCustomer()
    {
        return customer;
    }

    public OfferListItem setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    public Date getExpiration()
    {
        return expiration;
    }

    public OfferListItem setExpiration( Date expiration )
    {
        this.expiration = expiration;
        return this;
    }

    /**
     * The unique identification of the associated order.
     **/
    public Long getId()
    {
        return id;
    }

    public OfferListItem setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * The creditor's company logo, a full URL of the image served from CDN.
     **/
    public String getLogo()
    {
        return logo;
    }

    public OfferListItem setLogo( String logo )
    {
        this.logo = logo;
        return this;
    }

    /**
     * The date of the last modification of the associated order values.
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public OfferListItem setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public Boolean getMultipleRecipients()
    {
        return multipleRecipients;
    }

    public OfferListItem setMultipleRecipients( Boolean multipleRecipients )
    {
        this.multipleRecipients = multipleRecipients;
        return this;
    }

    /**
     * The short description of the offer.  It might be a short description one of the product at order. Alternatively it might be also a direct message to the target customer. The recommended length is max. 500 characters.
     **/
    public String getSnippet()
    {
        return snippet;
    }

    public OfferListItem setSnippet( String snippet )
    {
        this.snippet = snippet;
        return this;
    }

    public Sponsor getSponsor()
    {
        return sponsor;
    }

    public OfferListItem setSponsor( Sponsor sponsor )
    {
        this.sponsor = sponsor;
        return this;
    }

    /**
     * The supplier account that represents a business at TurnOnline.biz Ecosystem.
     **/
    public Creditor getSupplier()
    {
        return supplier;
    }

    public OfferListItem setSupplier( Creditor supplier )
    {
        this.supplier = supplier;
        return this;
    }

    /**
     * The thumbnail URL of the associated product picture. Served from the content delivery network (CDN).
     **/
    public String getThumbnail()
    {
        return thumbnail;
    }

    public OfferListItem setThumbnail( String thumbnail )
    {
        this.thumbnail = thumbnail;
        return this;
    }

    /**
     * The offer title. A text that should take an user attention.
     **/
    public String getTitle()
    {
        return title;
    }

    public OfferListItem setTitle( String title )
    {
        this.title = title;
        return this;
    }

    /**
     * The offer total price. Including VAT.
     **/
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public OfferListItem setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    /**
     * The offer total price. Excluding VAT.
     **/
    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public OfferListItem setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    /**
     * The offer total amount of VAT.
     **/
    public Double getTotalVatAmount()
    {
        return totalVatAmount;
    }

    public OfferListItem setTotalVatAmount( Double totalVatAmount )
    {
        this.totalVatAmount = totalVatAmount;
        return this;
    }

    /**
     * The offer total VAT base.
     **/
    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public OfferListItem setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }
}
