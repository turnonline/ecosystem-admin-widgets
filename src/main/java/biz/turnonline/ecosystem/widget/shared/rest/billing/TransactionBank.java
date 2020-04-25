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

/**
 * The bank account associated with this transaction.
 */
public final class TransactionBank
{
    private String code;

    private String iban;

    /**
     * The bank identified by a bank code.
     **/
    public String getCode()
    {
        return code;
    }

    public TransactionBank setCode( String code )
    {
        this.code = code;
        return this;
    }

    /**
     * The international bank account number.
     **/
    public String getIban()
    {
        return iban;
    }

    public TransactionBank setIban( String iban )
    {
        this.iban = iban;
        return this;
    }
}
