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

import biz.turnonline.ecosystem.widget.shared.rest.Contact;
import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

public final class Customer
        implements Contact, RelevantNullChecker
{
    private Long account;

    private String businessName;

    private String ccEmail;

    private String city;

    private String companyId;

    private String contactEmail;

    private String contactPhone;

    private String country;

    private String email;

    private String firstName;

    private String lastName;

    private String locale;

    private String logoServingUrl;

    private CustomerPostalAddress postalAddress;

    private String postcode;

    private String prefix;

    private String street;

    private String suffix;

    private String taxId;

    private String vatId;

    public Customer()
    {
    }

    public Customer( Contact contact )
    {
        this.account = contact.getAccountId();
        this.businessName = contact.getBusinessName();
        this.ccEmail = contact.getCcEmail();
        this.city = contact.getCity();
        this.companyId = contact.getCompanyId();
        this.contactEmail = contact.getContactEmail();
        this.contactPhone = contact.getContactPhone();
        this.country = contact.getCountry();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.postcode = contact.getPostcode();
        this.prefix = contact.getPrefix();
        this.street = contact.getStreet();
        this.suffix = contact.getSuffix();
        this.taxId = contact.getTaxId();
        this.vatId = contact.getVatId();
    }

    public Long getAccount()
    {
        return account;
    }

    public Customer setAccount( Long account )
    {
        this.account = account;
        return this;
    }

    @Override
    public Long getAccountId()
    {
        return getAccount();
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public Customer setBusinessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    public String getCcEmail()
    {
        return ccEmail;
    }

    public Customer setCcEmail( String ccEmail )
    {
        this.ccEmail = ccEmail;
        return this;
    }

    public String getCity()
    {
        return city;
    }

    public Customer setCity( String city )
    {
        this.city = city;
        return this;
    }

    @Override
    public Boolean getCompany()
    {
        return businessName != null && !"".equals( businessName.trim() );
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public Customer setCompanyId( String companyId )
    {
        this.companyId = companyId;
        return this;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public Customer setContactEmail( String contactEmail )
    {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public Customer setContactPhone( String contactPhone )
    {
        this.contactPhone = contactPhone;
        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public Customer setCountry( String country )
    {
        this.country = country;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public Customer setEmail( String email )
    {
        this.email = email;
        return this;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public Customer setFirstName( String firstName )
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Customer setLastName( String lastName )
    {
        this.lastName = lastName;
        return this;
    }

    public String getLocale()
    {
        return locale;
    }

    public Customer setLocale( String locale )
    {
        this.locale = locale;
        return this;
    }

    public String getLogoServingUrl()
    {
        return logoServingUrl;
    }

    public Customer setLogoServingUrl( String logoServingUrl )
    {
        this.logoServingUrl = logoServingUrl;
        return this;
    }

    public CustomerPostalAddress getPostalAddress()
    {
        return postalAddress;
    }

    public Customer setPostalAddress( CustomerPostalAddress postalAddress )
    {
        this.postalAddress = postalAddress;
        return this;
    }

    public boolean setPostalAddressIf( CustomerPostalAddress postalAddress )
    {
        return setIfNotAllNull( this::setPostalAddress, postalAddress );
    }

    /**
     * @return value or {@code null} for none
     */
    public String getPostcode()
    {
        return postcode;
    }

    public Customer setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public Customer setPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public Customer setStreet( String street )
    {
        this.street = street;
        return this;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public Customer setSuffix( String suffix )
    {
        this.suffix = suffix;
        return this;
    }

    public String getTaxId()
    {
        return taxId;
    }

    public Customer setTaxId( String taxId )
    {
        this.taxId = taxId;
        return this;
    }

    public String getVatId()
    {
        return vatId;
    }

    public Customer setVatId( String vatId )
    {
        this.vatId = vatId;
        return this;
    }

    @Override
    public boolean allNull()
    {
        // country is excluded from the check.
        // If only country property has set (default) it means no user input so ignore.
        return allNull( account,
                businessName,
                ccEmail,
                city,
                companyId,
                contactEmail,
                contactPhone,
                email,
                firstName,
                lastName,
                locale,
                postalAddress,
                postcode,
                prefix,
                street,
                suffix,
                taxId,
                vatId
        );
    }
}
