/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared.rest.account;

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

import java.util.Date;

/**
 * The set of account default invoicing rules. These values might be overriden.
 **/
public class InvoicingConfig
        implements RelevantNullChecker
{
    private String currency;

    private String introductoryText;

    private String finalText;

    private Date modificationDate;

    private Integer numberOfDays;

    private Boolean hasBillingAddress;

    private Image stamp;

    private InvoicingConfigBillingAddress billingAddress;

    private InvoicingConfigBillingContact billingContact;

    /**
     * The currency code (alphabetic code) based on the ISO 4217. If not set, the country default will be set.
     **/
    public InvoicingConfig currency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency( String currency )
    {
        this.currency = currency;
    }

    /**
     * A default introductory text to be placed at invoice usually at top of the billing items. Use this to communicate a message to the invoice recipient.
     **/
    public String getIntroductoryText()
    {
        return introductoryText;
    }

    public void setIntroductoryText( String introductoryText )
    {
        this.introductoryText = introductoryText;
    }

    /**
     * A default final text to be placed at invoice usually at the bottom. Use this to communicate a message to the invoice recipient.
     **/
    public String getFinalText()
    {
        return finalText;
    }

    public void setFinalText( String finalText )
    {
        this.finalText = finalText;
    }

    /**
     * The date and time of the last modification of invoicing configuration values. Populated by the service.
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
     * The default value of number of days for calculation of the invoice due date.
     **/
    public InvoicingConfig numberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    public void setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
    }

    /**
     * If true, the billing address is not same as the company address and must be provided.
     **/
    public InvoicingConfig hasBillingAddress( Boolean hasBillingAddress )
    {
        this.hasBillingAddress = hasBillingAddress;
        return this;
    }

    public Boolean getHasBillingAddress()
    {
        return hasBillingAddress;
    }

    public void setHasBillingAddress( Boolean hasBillingAddress )
    {
        this.hasBillingAddress = hasBillingAddress;
    }

    /**
     * An optional image used to be placed at invoice as a stamp or sign.
     **/
    public Image getStamp()
    {
        return stamp;
    }

    public void setStamp( Image stamp )
    {
        this.stamp = stamp;
    }

    /**
     * The billing address details. The address is being ignored until property “hasBillingAddress” is set to true.
     **/
    public InvoicingConfigBillingAddress getBillingAddress()
    {
        return billingAddress;
    }

    public void setBillingAddress( InvoicingConfigBillingAddress billingAddress )
    {
        this.billingAddress = billingAddress;
    }

    /**
     * The contact person related to billing. It might be presented at invoice or in email communication related to billing.
     **/
    public InvoicingConfigBillingContact getBillingContact()
    {
        return billingContact;
    }

    public void setBillingContact( InvoicingConfigBillingContact billingContact )
    {
        this.billingContact = billingContact;
    }

    public boolean setBillingAddressIf( InvoicingConfigBillingAddress billingAddress )
    {
        return setIfNotAllNull( this::setBillingAddress, billingAddress );
    }

    public boolean setBillingContactIf( InvoicingConfigBillingContact billingContact )
    {
        return setIfNotAllNull( this::setBillingContact, billingContact );
    }

    @Override
    public boolean allNull()
    {
        return allNull( currency, introductoryText, finalText, numberOfDays, stamp, hasBillingAddress, billingAddress,
                billingContact );
    }
}

