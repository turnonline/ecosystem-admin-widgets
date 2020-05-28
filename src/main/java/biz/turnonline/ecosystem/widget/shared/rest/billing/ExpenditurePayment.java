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

/**
 * Expense payment details.
 */
public final class ExpenditurePayment
{
    private BankAccount bankAccount;

    private Date dueDate;

    private Double totalAmount;

    private Long transactionId;

    private String type;

    /**
     * Creditor's bank account. The bank and IBAN that has been (or expected to be) used to transfer money.
     **/
    public BankAccount getBankAccount()
    {
        return bankAccount;
    }

    public ExpenditurePayment setBankAccount( BankAccount bankAccount )
    {
        this.bankAccount = bankAccount;
        return this;
    }

    /**
     * The optional bill due date.
     **/
    public Date getDueDate()
    {
        return dueDate;
    }

    public ExpenditurePayment setDueDate( Date dueDate )
    {
        this.dueDate = dueDate;
        return this;
    }

    public Double getTotalAmount()
    {
        return totalAmount;
    }

    public ExpenditurePayment setTotalAmount( Double totalAmount )
    {
        this.totalAmount = totalAmount;
        return this;
    }

    public Long getTransactionId()
    {
        return transactionId;
    }

    public ExpenditurePayment setTransactionId( Long transactionId )
    {
        this.transactionId = transactionId;
        return this;
    }

    /**
     * The payment type that has been (or expected to be) used.
     **/
    public String getType()
    {
        return type;
    }

    public ExpenditurePayment setType( String type )
    {
        this.type = type;
        return this;
    }
}
