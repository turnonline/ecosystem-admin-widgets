/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2018-10-08 17:45:39 UTC)
 * on 2019-03-14 at 19:18:29 UTC
 * Modify at your own risk.
 */

package org.ctoolkit.turnonline.widget.shared.rest.productbilling;

import java.util.Date;

/**
 * Model definition for Invoice.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Product Billing. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings( "javadoc" )
public final class Invoice
{

    /**
     * The value may be {@code null}.
     */
    
    private String currency;

    /**
     * The value may be {@code null}.
     */
    
    private Customer customer;

    /**
     * The value may be {@code null}.
     */
    
    private Date dateOfIssue;

    /**
     * The value may be {@code null}.
     */
    
    private Date dateOfTaxable;

    /**
     * The value may be {@code null}.
     */
    
    private String finalText;

    /**
     * The value may be {@code null}.
     */

    private Long id;

    /**
     * The value may be {@code null}.
     */
    
    private String introductoryText;

    /**
     * The value may be {@code null}.
     */
    
    private String invoiceNumber;

    /**
     * The value may be {@code null}.
     */
    
    private Date modificationDate;

    /**
     * The value may be {@code null}.
     */
    
    private NumberSeries numberSeries;

    /**
     * The value may be {@code null}.
     */

    private Long orderId;

    /**
     * The value may be {@code null}.
     */
    
    private InvoicePayment payment;

    /**
     * The value may be {@code null}.
     */
    
    private String pin;

    /**
     * The value may be {@code null}.
     */
    
    private InvoicePricing pricing;

    /**
     * The value may be {@code null}.
     */
    
    private String servingUrl;

    /**
     * The value may be {@code null}.
     */
    
    private java.util.List<Deduction> settlements;

    /**
     * The value may be {@code null}.
     */
    
    private String status;

    /**
     * The value may be {@code null}.
     */
    
    private java.util.List<Deduction> tlements;

    /**
     * The value may be {@code null}.
     */
    
    private String type;

    /**
     * @return value or {@code null} for none
     */
    public String getCurrency()
    {
        return currency;
    }

    /**
     * @param currency currency or {@code null} for none
     */
    public Invoice setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Customer getCustomer()
    {
        return customer;
    }

    /**
     * @param customer customer or {@code null} for none
     */
    public Invoice setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getDateOfIssue()
    {
        return dateOfIssue;
    }

    /**
     * @param dateOfIssue dateOfIssue or {@code null} for none
     */
    public Invoice setDateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getDateOfTaxable()
    {
        return dateOfTaxable;
    }

    /**
     * @param dateOfTaxable dateOfTaxable or {@code null} for none
     */
    public Invoice setDateOfTaxable( Date dateOfTaxable )
    {
        this.dateOfTaxable = dateOfTaxable;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getFinalText()
    {
        return finalText;
    }

    /**
     * @param finalText finalText or {@code null} for none
     */
    public Invoice setFinalText( String finalText )
    {
        this.finalText = finalText;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id id or {@code null} for none
     */
    public Invoice setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getIntroductoryText()
    {
        return introductoryText;
    }

    /**
     * @param introductoryText introductoryText or {@code null} for none
     */
    public Invoice setIntroductoryText( String introductoryText )
    {
        this.introductoryText = introductoryText;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber invoiceNumber or {@code null} for none
     */
    public Invoice setInvoiceNumber( String invoiceNumber )
    {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getModificationDate()
    {
        return modificationDate;
    }

    /**
     * @param modificationDate modificationDate or {@code null} for none
     */
    public Invoice setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public NumberSeries getNumberSeries()
    {
        return numberSeries;
    }

    /**
     * @param numberSeries numberSeries or {@code null} for none
     */
    public Invoice setNumberSeries( NumberSeries numberSeries )
    {
        this.numberSeries = numberSeries;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Long getOrderId()
    {
        return orderId;
    }

    /**
     * @param orderId orderId or {@code null} for none
     */
    public Invoice setOrderId( Long orderId )
    {
        this.orderId = orderId;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public InvoicePayment getPayment()
    {
        return payment;
    }

    /**
     * @param payment payment or {@code null} for none
     */
    public Invoice setPayment( InvoicePayment payment )
    {
        this.payment = payment;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getPin()
    {
        return pin;
    }

    /**
     * @param pin pin or {@code null} for none
     */
    public Invoice setPin( String pin )
    {
        this.pin = pin;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public InvoicePricing getPricing()
    {
        return pricing;
    }

    /**
     * @param pricing pricing or {@code null} for none
     */
    public Invoice setPricing( InvoicePricing pricing )
    {
        this.pricing = pricing;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getServingUrl()
    {
        return servingUrl;
    }

    /**
     * @param servingUrl servingUrl or {@code null} for none
     */
    public Invoice setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.util.List<Deduction> getSettlements()
    {
        return settlements;
    }

    /**
     * @param settlements settlements or {@code null} for none
     */
    public Invoice setSettlements( java.util.List<Deduction> settlements )
    {
        this.settlements = settlements;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status status or {@code null} for none
     */
    public Invoice setStatus( String status )
    {
        this.status = status;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.util.List<Deduction> getTlements()
    {
        return tlements;
    }

    /**
     * @param tlements tlements or {@code null} for none
     */
    public Invoice setTlements( java.util.List<Deduction> tlements )
    {
        this.tlements = tlements;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type type or {@code null} for none
     */
    public Invoice setType( String type )
    {
        this.type = type;
        return this;
    }

}
