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
public class ExpensePayment
{
    private BankAccount bankAccount;

    private Date dueDate;

    private String key;

    private String type;

    /**
     * Creditor's bank account. The bank and IBAN that has been (or expected to be) used to transfer money.
     **/
    public BankAccount getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount( BankAccount bankAccount )
    {
        this.bankAccount = bankAccount;
    }

    /**
     * The optional bill due date.
     **/
    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate( Date dueDate )
    {
        this.dueDate = dueDate;
    }

    /**
     * The expense payment identification.
     **/
    public String getKey()
    {
        return key;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    /**
     * The payment type that has been (or expected to be) used.
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

