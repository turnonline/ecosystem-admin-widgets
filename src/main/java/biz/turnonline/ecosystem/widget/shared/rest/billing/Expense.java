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

/**
 * Expense that represents a purchase outside of the Ecosystem.
 */
public class Expense
{
    private Long bill;

    private String billNumber;

    private Date dateOfIssue;

    private Date dateOfTaxable;

    private List<PricingItem> items;

    private ExpensePayment payment;

    private Creditor supplier;

    private String type;

    /**
     * The unique bill identification within Billing Processor service.
     **/
    public Long getBill()
    {
        return bill;
    }

    public void setBill( Long bill )
    {
        this.bill = bill;
    }

    /**
     * Bill (receipt) number, or in case of incoming invoice its invoice number.
     **/
    public String getBillNumber()
    {
        return billNumber;
    }

    public void setBillNumber( String billNumber )
    {
        this.billNumber = billNumber;
    }

    /**
     * The bill issue date. RFC 3339
     **/
    public Date getDateOfIssue()
    {
        return dateOfIssue;
    }

    public void setDateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
    }

    /**
     * The date of taxable supplies. If not provided the date of issue will be used.
     **/
    public Date getDateOfTaxable()
    {
        return dateOfTaxable;
    }

    public void setDateOfTaxable( Date dateOfTaxable )
    {
        this.dateOfTaxable = dateOfTaxable;
    }

    /**
     * Expense pricing item list.
     **/
    public List<PricingItem> getItems()
    {
        return items;
    }

    public void setItems( List<PricingItem> items )
    {
        this.items = items;
    }

    /**
     * Expense payment details.
     **/
    public ExpensePayment getPayment()
    {
        return payment;
    }

    public void setPayment( ExpensePayment payment )
    {
        this.payment = payment;
    }

    /**
     * A business that delivered a product or service. It might be an account at Ecosystem but not necessarily.
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
     * The bill document type. It's case insensitive.
     **/
    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }
}

