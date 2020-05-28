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
 * Transaction that represents either a credit or debit operation.
 */
public final class Transaction
{
    private Double amount;

    private Double balance;

    private TransactionBank bankAccount;

    private Bill bill;

    private Double billAmount;

    private String billCurrency;

    private Date completedAt;

    private Boolean credit;

    private String currency;

    private String key;

    private Merchant merchant;

    private String reference;

    private String status;

    private Long transactionId;

    private String type;

    /**
     * The transaction amount absolute value.
     **/
    public Double getAmount()
    {
        return amount;
    }

    public Transaction setAmount( Double amount )
    {
        this.amount = amount;
        return this;
    }

    /**
     * The balance after the transaction.
     **/
    public Double getBalance()
    {
        return balance;
    }

    public Transaction setBalance( Double balance )
    {
        this.balance = balance;
        return this;
    }

    /**
     * The bank account associated with this transaction.
     **/
    public TransactionBank getBankAccount()
    {
        return bankAccount;
    }

    public Transaction setBankAccount( TransactionBank bankAccount )
    {
        this.bankAccount = bankAccount;
        return this;
    }

    /**
     * The bill or invoice document settled by this transaction.
     **/
    public Bill getBill()
    {
        return bill;
    }

    public Transaction setBill( Bill bill )
    {
        this.bill = bill;
        return this;
    }

    public Double getBillAmount()
    {
        return billAmount;
    }

    public Transaction setBillAmount( Double billAmount )
    {
        this.billAmount = billAmount;
        return this;
    }

    public String getBillCurrency()
    {
        return billCurrency;
    }

    public Transaction setBillCurrency( String billCurrency )
    {
        this.billCurrency = billCurrency;
        return this;
    }

    /**
     * The date when the transaction was completed (status COMPLETED).
     **/
    public Date getCompletedAt()
    {
        return completedAt;
    }

    public Transaction setCompletedAt( Date completedAt )
    {
        this.completedAt = completedAt;
        return this;
    }

    /**
     * The boolean indicating whether the payment has positive or negative amount; true - credit, false - debit.
     **/
    public Boolean getCredit()
    {
        return credit;
    }

    public Transaction setCredit( Boolean credit )
    {
        this.credit = credit;
        return this;
    }

    /**
     * The payment currency alphabetic code based on the ISO 4217.
     **/
    public String getCurrency()
    {
        return currency;
    }

    public Transaction setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * The unique payment identification related to the associated bill.
     **/
    public String getKey()
    {
        return key;
    }

    public Transaction setKey( String key )
    {
        this.key = key;
        return this;
    }

    public Merchant getMerchant()
    {
        return merchant;
    }

    public Transaction setMerchant( Merchant merchant )
    {
        this.merchant = merchant;
        return this;
    }

    /**
     * A user provided payment reference.
     **/
    public String getReference()
    {
        return reference;
    }

    public Transaction setReference( String reference )
    {
        this.reference = reference;
        return this;
    }

    /**
     * The transaction status.
     **/
    public String getStatus()
    {
        return status;
    }

    public Transaction setStatus( String status )
    {
        this.status = status;
        return this;
    }

    /**
     * The identification of the transaction within payment processor service unique for single Ecosystem account.
     */
    public Long getTransactionId()
    {
        return transactionId;
    }

    public Transaction setTransactionId( Long transactionId )
    {
        this.transactionId = transactionId;
        return this;
    }

    /**
     * The payment type that has been used to make this payment.
     **/
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
