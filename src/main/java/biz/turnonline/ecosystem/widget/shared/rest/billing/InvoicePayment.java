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

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

import java.util.Date;

public final class InvoicePayment
        implements RelevantNullChecker
{
    private BankAccount bankAccount;

    private Date dueDate;

    private String key;

    private Double totalAmount;

    private String type;

    private Long variableSymbol;

    public BankAccount getBankAccount()
    {
        return bankAccount;
    }

    public InvoicePayment setBankAccount( BankAccount bankAccount )
    {
        this.bankAccount = bankAccount;
        return this;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public InvoicePayment setDueDate( Date dueDate )
    {
        this.dueDate = dueDate;
        return this;
    }

    public String getKey()
    {
        return key;
    }

    public InvoicePayment setKey( String key )
    {
        this.key = key;
        return this;
    }

    public Double getTotalAmount()
    {
        return totalAmount;
    }

    public InvoicePayment setTotalAmount( Double totalAmount )
    {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getType()
    {
        return type;
    }

    public InvoicePayment setType( String type )
    {
        this.type = type;
        return this;
    }

    public Long getVariableSymbol()
    {
        return variableSymbol;
    }

    public InvoicePayment setVariableSymbol( Long variableSymbol )
    {
        this.variableSymbol = variableSymbol;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( bankAccount, dueDate, key, type );
    }
}
