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

public final class Transaction
{
    private Double amount;

    private Double balance;

    private TransactionBank bankAccount;

    private Bill bill;

    private Date completedAt;

    private Boolean credit;

    private String currency;

    private String key;

    private String reference;

    private String status;

    private String type;

    public Double getAmount()
    {
        return amount;
    }

    public Transaction setAmount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    public Double getBalance()
    {
        return balance;
    }

    public Transaction setBalance( Double balance )
    {
        this.balance = balance;
        return this;
    }

    public TransactionBank getBankAccount()
    {
        return bankAccount;
    }

    public Transaction setBankAccount( TransactionBank bankAccount )
    {
        this.bankAccount = bankAccount;
        return this;
    }

    public Bill getBill()
    {
        return bill;
    }

    public Transaction setBill( Bill bill )
    {
        this.bill = bill;
        return this;
    }

    public Date getCompletedAt()
    {
        return completedAt;
    }

    public Transaction setCompletedAt( Date completedAt )
    {
        this.completedAt = completedAt;
        return this;
    }

    public Boolean getCredit()
    {
        return credit;
    }

    public Transaction setCredit( Boolean credit )
    {
        this.credit = credit;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public Transaction setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public String getKey()
    {
        return key;
    }

    public Transaction setKey( String key )
    {
        this.key = key;
        return this;
    }

    public String getReference()
    {
        return reference;
    }

    public Transaction setReference( String reference )
    {
        this.reference = reference;
        return this;
    }

    public String getStatus()
    {
        return status;
    }

    public Transaction setStatus( String status )
    {
        this.status = status;
        return this;
    }

    public String getType()
    {
        return type;
    }

    public Transaction setType( String type )
    {
        this.type = type;
        return this;
    }
}
