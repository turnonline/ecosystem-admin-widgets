/*
 *  Copyright (c) 2022 TurnOnline.biz s.r.o.
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
 */

package biz.turnonline.ecosystem.widget.shared.rest.billing;

/**
 * Model definition for TransactionCounterparty.
 */
public final class TransactionCounterparty
{
    private String bic;

    private String iban;

    private String name;

    public String getBic()
    {
        return bic;
    }

    public TransactionCounterparty setBic( String bic )
    {
        this.bic = bic;
        return this;
    }

    public String getIban()
    {
        return iban;
    }

    public TransactionCounterparty setIban( String iban )
    {
        this.iban = iban;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public TransactionCounterparty setName( String name )
    {
        this.name = name;
        return this;
    }
}
