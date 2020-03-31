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

public final class IncomingInvoice
{
    private Date createdDate;

    private Creditor creditor;

    private String currency;

    private Date dateOfIssue;

    private Date dateOfTaxable;

    private Long id;

    private String invoiceNumber;

    private Date modificationDate;

    private Long orderId;

    private InvoicePayment payment;

    private String pin;

    private InvoicePricing pricing;

    private String servingUrl;

    private String type;

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public IncomingInvoice setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
        return this;
    }

    public Creditor getCreditor()
    {
        return creditor;
    }

    public IncomingInvoice setCreditor( Creditor creditor )
    {
        this.creditor = creditor;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public IncomingInvoice setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public Date getDateOfIssue()
    {
        return dateOfIssue;
    }

    public IncomingInvoice setDateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    public Date getDateOfTaxable()
    {
        return dateOfTaxable;
    }

    public IncomingInvoice setDateOfTaxable( Date dateOfTaxable )
    {
        this.dateOfTaxable = dateOfTaxable;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public IncomingInvoice setId( Long id )
    {
        this.id = id;
        return this;
    }

    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    public IncomingInvoice setInvoiceNumber( String invoiceNumber )
    {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public IncomingInvoice setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public IncomingInvoice setOrderId( Long orderId )
    {
        this.orderId = orderId;
        return this;
    }

    public InvoicePayment getPayment()
    {
        return payment;
    }

    public IncomingInvoice setPayment( InvoicePayment payment )
    {
        this.payment = payment;
        return this;
    }

    public String getPin()
    {
        return pin;
    }

    public IncomingInvoice setPin( String pin )
    {
        this.pin = pin;
        return this;
    }

    public InvoicePricing getPricing()
    {
        return pricing;
    }

    public IncomingInvoice setPricing( InvoicePricing pricing )
    {
        this.pricing = pricing;
        return this;
    }

    public String getServingUrl()
    {
        return servingUrl;
    }

    public IncomingInvoice setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

    public String getType()
    {
        return type;
    }

    public IncomingInvoice setType( String type )
    {
        this.type = type;
        return this;
    }
}
