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

package biz.turnonline.ecosystem.widget.shared.rest.search;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class SearchContact
        implements Contact
{
    private Long accountId;

    private String id = null;

    private String firstName = null;

    private String middleName = null;

    private String lastName = null;

    private String companyId = null;

    private String businessName = null;

    private String owner = null;

    @Override
    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId( Long accountId )
    {
        this.accountId = accountId;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName( String middleName )
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    @Override
    public String getPostcode()
    {
        return null;
    }

    @Override
    public String getPrefix()
    {
        return null;
    }

    @Override
    public String getStreet()
    {
        return null;
    }

    @Override
    public String getSuffix()
    {
        return null;
    }

    @Override
    public String getTaxId()
    {
        return null;
    }

    @Override
    public String getVatId()
    {
        return null;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( String companyId )
    {
        this.companyId = companyId;
    }

    @Override
    public String getContactEmail()
    {
        return null;
    }

    @Override
    public String getContactPhone()
    {
        return null;
    }

    @Override
    public String getCountry()
    {
        return null;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName( String businessName )
    {
        this.businessName = businessName;
    }

    @Override
    public String getCcEmail()
    {
        return null;
    }

    @Override
    public String getCity()
    {
        return null;
    }

    @Override
    public Boolean getCompany()
    {
        return null;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner( String owner )
    {
        this.owner = owner;
    }
}
