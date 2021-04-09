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

public final class PricingCustomer
{
    private String country;

    private String email;

    private Boolean vatPayer;

    /**
     * The customer country code (ISO 3166 alpha-2). It’s case insensitive.
     **/
    public String getCountry()
    {
        return country;
    }

    public PricingCustomer setCountry( String country )
    {
        this.country = country;
        return this;
    }

    /**
     * The login email address within address book as the customer's identification.
     **/
    public String getEmail()
    {
        return email;
    }

    public PricingCustomer setEmail( String email )
    {
        this.email = email;
        return this;
    }

    /**
     * The boolean indication whether customer's company is registered as VAT payer. The missing value or false means company it’s not a VAT payer.
     **/
    public Boolean getVatPayer()
    {
        return vatPayer;
    }

    public PricingCustomer setVatPayer( Boolean vatPayer )
    {
        this.vatPayer = vatPayer;
        return this;
    }
}
