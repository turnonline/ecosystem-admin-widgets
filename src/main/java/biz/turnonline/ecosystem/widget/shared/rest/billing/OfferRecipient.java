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

import java.util.Date;

/**
 * Model definition for OfferRecipient.
 */
public final class OfferRecipient
{
    private Date accepted;

    private Long accountId;

    private String contactEmail;

    private String firstName;

    private String lastName;

    private String middleName;

    private Long offerId;

    public Date getAccepted()
    {
        return accepted;
    }

    public OfferRecipient setAccepted( Date accepted )
    {
        this.accepted = accepted;
        return this;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public OfferRecipient setAccountId( Long accountId )
    {
        this.accountId = accountId;
        return this;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public OfferRecipient setContactEmail( String contactEmail )
    {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public OfferRecipient setFirstName( String firstName )
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public OfferRecipient setLastName( String lastName )
    {
        this.lastName = lastName;
        return this;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public OfferRecipient setMiddleName( String middleName )
    {
        this.middleName = middleName;
        return this;
    }

    public Long getOfferId()
    {
        return offerId;
    }

    public OfferRecipient setOfferId( Long offerId )
    {
        this.offerId = offerId;
        return this;
    }
}
