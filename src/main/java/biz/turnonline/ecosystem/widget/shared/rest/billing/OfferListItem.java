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

/**
 * Offer list item.
 */
public class OfferListItem
{
    private String currency;

    private Customer customer;

    private Long id;

    private String logo;

    private Date modificationDate;

    private String snippet;

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

    public void setCurrency( String currency )
    {
        this.currency = currency;
    }

    /**
     * The identification of the customer as a recipient of the offer.
     **/
    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer( Customer customer )
    {
        this.customer = customer;
    }

    /**
     * The unique identification of the associated order.
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
     * The creditor's company logo, a full URL of the image served from CDN.
     **/
    public String getLogo()
    {
        return logo;
    }

    public void setLogo( String logo )
    {
        this.logo = logo;
    }

    /**
     * The date of the last modification of the associated order values.
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
     * The short description of the offer.  It might be a short description one of the product at order. Alternatively it might be also a direct message to the target customer. The recommended length is max. 500 characters.
     **/
    public String getSnippet()
    {
        return snippet;
    }

    public void setSnippet( String snippet )
    {
        this.snippet = snippet;
    }

    /**
     * The supplier account that represents a business at TurnOnline.biz Ecosystem.
     **/
    public Creditor getSupplier()
    {
        return supplier;
    }

    public void setSupplier( Creditor supplier )
    {
        this.supplier = supplier;
    }

    /**
     * The thumbnail URL of the associated product picture. Served from the content delivery network (CDN).
     **/
    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail( String thumbnail )
    {
        this.thumbnail = thumbnail;
    }

    /**
     * The offer title. A text that should take an user attention.
     **/
    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    /**
     * The offer total price. Including VAT.
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
     * The offer total price. Excluding VAT.
     **/
    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public void setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
    }

    /**
     * The offer total amount of VAT.
     **/
    public Double getTotalVatAmount()
    {
        return totalVatAmount;
    }

    public void setTotalVatAmount( Double totalVatAmount )
    {
        this.totalVatAmount = totalVatAmount;
    }

    /**
     * The offer total VAT base.
     **/
    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public void setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
    }
}

