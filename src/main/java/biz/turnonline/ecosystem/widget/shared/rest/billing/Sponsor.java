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
 * Model definition for Sponsor.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Product Billing. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
public final class Sponsor
{
    private Long account;

    private String businessName;

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

    public Long getAccount()
    {
        return account;
    }

    public Sponsor setAccount( Long account )
    {
        this.account = account;
        return this;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public Sponsor setBusinessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    public String getCity()
    {
        return city;
    }

    public Sponsor setCity( String city )
    {
        this.city = city;
        return this;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public Sponsor setCompanyId( String companyId )
    {
        this.companyId = companyId;
        return this;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public Sponsor setContactEmail( String contactEmail )
    {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public Sponsor setContactPhone( String contactPhone )
    {
        this.contactPhone = contactPhone;
        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public Sponsor setCountry( String country )
    {
        this.country = country;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public Sponsor setEmail( String email )
    {
        this.email = email;
        return this;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public Sponsor setFirstName( String firstName )
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Sponsor setLastName( String lastName )
    {
        this.lastName = lastName;
        return this;
    }

    public String getLocale()
    {
        return locale;
    }

    public Sponsor setLocale( String locale )
    {
        this.locale = locale;
        return this;
    }

    public String getLogoServingUrl()
    {
        return logoServingUrl;
    }

    public Sponsor setLogoServingUrl( String logoServingUrl )
    {
        this.logoServingUrl = logoServingUrl;
        return this;
    }

    public CustomerPostalAddress getPostalAddress()
    {
        return postalAddress;
    }

    public Sponsor setPostalAddress( CustomerPostalAddress postalAddress )
    {
        this.postalAddress = postalAddress;
        return this;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public Sponsor setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public Sponsor setPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public Sponsor setStreet( String street )
    {
        this.street = street;
        return this;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public Sponsor setSuffix( String suffix )
    {
        this.suffix = suffix;
        return this;
    }

    public String getTaxId()
    {
        return taxId;
    }

    public Sponsor setTaxId( String taxId )
    {
        this.taxId = taxId;
        return this;
    }

    public String getVatId()
    {
        return vatId;
    }

    public Sponsor setVatId( String vatId )
    {
        this.vatId = vatId;
        return this;
    }
}
