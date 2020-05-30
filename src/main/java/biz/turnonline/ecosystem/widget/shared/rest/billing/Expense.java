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

public final class Expense
{
    private Bill bill;

    private String billNumber;

    private String currency;

    private Date dateOfIssue;

    private Date dateOfTaxable;

    private List<PricingItem> items;

    private BillPayment payment;

    private String pin;

    private String servingUrl;

    private Creditor supplier;

    private String through;

    private Double totalPrice;

    private Double totalPriceExclVat;

    private Double totalVatBase;

    /**
     * Identification of the bill (receipt) or invoice document.
     * Valid invoice identification includes order identification too.
     **/
    public Bill getBill()
    {
        return bill;
    }

    public Expense setBill( Bill bill )
    {
        this.bill = bill;
        return this;
    }

    /**
     * Bill (receipt) number, or in case of incoming invoice its invoice number.
     **/
    public String getBillNumber()
    {
        return billNumber;
    }

    public Expense setBillNumber( String billNumber )
    {
        this.billNumber = billNumber;
        return this;
    }

    /**
     * The currency alphabetic code based on the ISO 4217.
     */
    public String getCurrency()
    {
        return currency;
    }

    public Expense setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * The bill issue date. RFC 3339
     **/
    public Date getDateOfIssue()
    {
        return dateOfIssue;
    }

    public Expense setDateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    /**
     * The date of taxable supplies. If not provided the date of issue will be used.
     **/
    public Date getDateOfTaxable()
    {
        return dateOfTaxable;
    }

    public Expense setDateOfTaxable( Date dateOfTaxable )
    {
        this.dateOfTaxable = dateOfTaxable;
        return this;
    }

    /**
     * Expense pricing item list.
     **/
    public List<PricingItem> getItems()
    {
        return items;
    }

    public Expense setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    /**
     * Expense payment details.
     **/
    public BillPayment getPayment()
    {
        return payment;
    }

    public Expense setPayment( BillPayment payment )
    {
        this.payment = payment;
        return this;
    }

    public String getPin()
    {
        return pin;
    }

    public Expense setPin( String pin )
    {
        this.pin = pin;
        return this;
    }

    public String getServingUrl()
    {
        return servingUrl;
    }

    public Expense setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

    public Creditor getSupplier()
    {
        return supplier;
    }

    public Expense setSupplier( Creditor supplier )
    {
        this.supplier = supplier;
        return this;
    }

    public String getThrough()
    {
        return through;
    }

    public Expense setThrough( String through )
    {
        this.through = through;
        return this;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public Expense setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    public Expense setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    public Expense setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }
}
