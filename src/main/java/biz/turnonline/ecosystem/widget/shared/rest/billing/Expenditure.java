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

public final class Expenditure
{
    private Long bill;

    private String billNumber;

    private Date dateOfIssue;

    private Date dateOfTaxable;

    private List<PricingItem> items;

    private ExpenditurePayment payment;

    private Supplier supplier;

    private String type;

    public Long getBill()
    {
        return bill;
    }

    public Expenditure setBill( Long bill )
    {
        this.bill = bill;
        return this;
    }

    public String getBillNumber()
    {
        return billNumber;
    }

    public Expenditure setBillNumber( String billNumber )
    {
        this.billNumber = billNumber;
        return this;
    }

    public Date getDateOfIssue()
    {
        return dateOfIssue;
    }

    public Expenditure setDateOfIssue( Date dateOfIssue )
    {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    public Date getDateOfTaxable()
    {
        return dateOfTaxable;
    }

    public Expenditure setDateOfTaxable( Date dateOfTaxable )
    {
        this.dateOfTaxable = dateOfTaxable;
        return this;
    }

    public List<PricingItem> getItems()
    {
        return items;
    }

    public Expenditure setItems( List<PricingItem> items )
    {
        this.items = items;
        return this;
    }

    public ExpenditurePayment getPayment()
    {
        return payment;
    }

    public Expenditure setPayment( ExpenditurePayment payment )
    {
        this.payment = payment;
        return this;
    }

    public Supplier getSupplier()
    {
        return supplier;
    }

    public Expenditure setSupplier( Supplier supplier )
    {
        this.supplier = supplier;
        return this;
    }

    public String getType()
    {
        return type;
    }

    public Expenditure setType( String type )
    {
        this.type = type;
        return this;
    }
}
