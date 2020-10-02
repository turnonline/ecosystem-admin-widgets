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
import java.util.Objects;

public final class Invoice
        implements RelevantNullChecker
{
    private Date createdDate;

    private String currency;

    private Customer customer;

    private Date dateOfIssue;

    private Date dateOfTaxable;

    private String finalText;

    private Long id;

    private String introductoryText;

    private String invoiceNumber;

    private Date modificationDate;

    private NumberSeries numberSeries;

    private Long orderId;

    private BillPayment payment;

    private String pin;

    private BillPricing pricing;

    private String servingUrl;

    private String status;

    private String type;

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public Invoice setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public Invoice setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public Invoice setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    public void setCustomerIf( Customer customer )
    {
        setIfNotAllNull( this::setCustomer, customer );
    }

    public Date getDateOfIssue()
    {
        return dateOfIssue;
    }

    public Invoice setDateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    public Date getDateOfTaxable()
    {
        return dateOfTaxable;
    }

    public Invoice setDateOfTaxable( Date dateOfTaxable )
    {
        this.dateOfTaxable = dateOfTaxable;
        return this;
    }

    public String getFinalText()
    {
        return finalText;
    }

    public Invoice setFinalText( String finalText )
    {
        this.finalText = finalText;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public Invoice setId( Long id )
    {
        this.id = id;
        return this;
    }

    public String getIntroductoryText()
    {
        return introductoryText;
    }

    public Invoice setIntroductoryText( String introductoryText )
    {
        this.introductoryText = introductoryText;
        return this;
    }

    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    public Invoice setInvoiceNumber( String invoiceNumber )
    {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public Invoice setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public NumberSeries getNumberSeries()
    {
        return numberSeries;
    }

    public Invoice setNumberSeries( NumberSeries numberSeries )
    {
        this.numberSeries = numberSeries;
        return this;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public Invoice setOrderId( Long orderId )
    {
        this.orderId = orderId;
        return this;
    }

    public BillPayment getPayment()
    {
        return payment;
    }

    public Invoice setPayment( BillPayment payment )
    {
        this.payment = payment;
        return this;
    }

    public boolean setPaymentIf( BillPayment payment )
    {
        return setIfNotAllNull( this::setPayment, payment );
    }

    public String getPin()
    {
        return pin;
    }

    public Invoice setPin( String pin )
    {
        this.pin = pin;
        return this;
    }

    public BillPricing getPricing()
    {
        return pricing;
    }

    public Invoice setPricing( BillPricing pricing )
    {
        this.pricing = pricing;
        return this;
    }

    public String getServingUrl()
    {
        return servingUrl;
    }

    public Invoice setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

    public String getStatus()
    {
        return status;
    }

    public Invoice setStatus( String status )
    {
        this.status = status;
        return this;
    }

    public String getType()
    {
        return type;
    }

    public Invoice setType( String type )
    {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( !( o instanceof Invoice ) ) return false;
        Invoice invoice = ( Invoice ) o;
        return Objects.equals( id, invoice.id ) &&
                Objects.equals( orderId, invoice.orderId );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id, orderId );
    }

    @Override
    public boolean allNull()
    {
        return allNull( customer,
                dateOfIssue,
                dateOfTaxable,
                finalText,
                introductoryText,
                numberSeries,
                payment,
                pricing,
                status,
                type );
    }

    public enum Status
    {
        NEW,

        SENT,

        PAID,

        CANCELED
    }
}
