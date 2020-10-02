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

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

public final class BankAccount
        implements RelevantNullChecker
{
    private String beneficiary;

    private String bic;

    private String currency;

    private String iban;

    public String getBeneficiary()
    {
        return beneficiary;
    }

    public BankAccount setBeneficiary( String beneficiary )
    {
        this.beneficiary = beneficiary;
        return this;
    }

    public String getBic()
    {
        return bic;
    }

    public BankAccount setBic( String bic )
    {
        this.bic = bic;
        return this;
    }

    public String getCurrency()
    {
        return currency;
    }

    public BankAccount setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    public String getIban()
    {
        return iban;
    }

    public BankAccount setIban( String iban )
    {
        this.iban = iban;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( iban, bic, beneficiary );
    }
}
