/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2018-10-08 17:45:39 UTC)
 * on 2019-12-29 at 06:03:13 UTC
 * Modify at your own risk.
 */

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;
import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

/**
 * Model definition for Customer.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Product Billing. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
public final class Customer
        implements Contact, RelevantNullChecker
{
    /**
     * The value may be {@code null}.
     */
    private Long account;

    /**
     * The value may be {@code null}.
     */
    private String businessName;

    /**
     * The value may be {@code null}.
     */
    private String ccEmail;

    /**
     * The value may be {@code null}.
     */
    private String city;

    /**
     * The value may be {@code null}.
     */
    private String companyId;

    /**
     * The value may be {@code null}.
     */
    private String contactEmail;

    /**
     * The value may be {@code null}.
     */
    private String contactPhone;

    /**
     * The value may be {@code null}.
     */
    private String country;

    /**
     * The value may be {@code null}.
     */
    private String email;

    /**
     * The value may be {@code null}.
     */
    private String firstName;

    /**
     * The value may be {@code null}.
     */
    private String middleName;

    /**
     * The value may be {@code null}.
     */
    private String lastName;

    /**
     * The value may be {@code null}.
     */
    private String locale;

    /**
     * The value may be {@code null}.
     */
    private String logoServingUrl;

    /**
     * The value may be {@code null}.
     */
    private CustomerPostalAddress postalAddress;

    /**
     * The value may be {@code null}.
     */
    private String postcode;

    /**
     * The value may be {@code null}.
     */
    private String prefix;

    /**
     * The value may be {@code null}.
     */
    private String street;

    /**
     * The value may be {@code null}.
     */
    private String suffix;

    /**
     * The value may be {@code null}.
     */
    private String taxId;

    /**
     * The value may be {@code null}.
     */
    private String vatId;

    public Customer()
    {
    }

    public Customer( Contact contact )
    {
        this.businessName = contact.getBusinessName();
        this.ccEmail = contact.getCcEmail();
        this.city = contact.getCity();
        this.companyId = contact.getCompanyId();
        this.contactEmail = contact.getContactEmail();
        this.contactPhone = contact.getContactPhone();
        this.country = contact.getCountry();
        this.firstName = contact.getFirstName();
        this.middleName = contact.getMiddleName();
        this.lastName = contact.getLastName();
        this.postcode = contact.getPostcode();
        this.prefix = contact.getPrefix();
        this.street = contact.getStreet();
        this.suffix = contact.getSuffix();
        this.taxId = contact.getTaxId();
        this.vatId = contact.getVatId();
    }


    /**
     * @return value or {@code null} for none
     */
    public Long getAccount()
    {
        return account;
    }

    /**
     * @param account account or {@code null} for none
     */
    public Customer setAccount( Long account )
    {
        this.account = account;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getBusinessName()
    {
        return businessName;
    }

    /**
     * @param businessName businessName or {@code null} for none
     */
    public Customer setBusinessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getCcEmail()
    {
        return ccEmail;
    }

    /**
     * @param ccEmail ccEmail or {@code null} for none
     */
    public Customer setCcEmail( String ccEmail )
    {
        this.ccEmail = ccEmail;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city city or {@code null} for none
     */
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

    /**
     * @return value or {@code null} for none
     */
    public String getCompanyId()
    {
        return companyId;
    }

    /**
     * @param companyId companyId or {@code null} for none
     */
    public Customer setCompanyId( String companyId )
    {
        this.companyId = companyId;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getContactEmail()
    {
        return contactEmail;
    }

    /**
     * @param contactEmail contactEmail or {@code null} for none
     */
    public Customer setContactEmail( String contactEmail )
    {
        this.contactEmail = contactEmail;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getContactPhone()
    {
        return contactPhone;
    }

    /**
     * @param contactPhone contactPhone or {@code null} for none
     */
    public Customer setContactPhone( String contactPhone )
    {
        this.contactPhone = contactPhone;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @param country country or {@code null} for none
     */
    public Customer setCountry( String country )
    {
        this.country = country;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email email or {@code null} for none
     */
    public Customer setEmail( String email )
    {
        this.email = email;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName firstName or {@code null} for none
     */
    public Customer setFirstName( String firstName )
    {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     **/
    public String getMiddleName()
    {
        return middleName;
    }

    /**
     * @param middleName middleName or {@code null} for none
     */
    public void setMiddleName( String middleName )
    {
        this.middleName = middleName;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName lastName or {@code null} for none
     */
    public Customer setLastName( String lastName )
    {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getLocale()
    {
        return locale;
    }

    /**
     * @param locale locale or {@code null} for none
     */
    public Customer setLocale( String locale )
    {
        this.locale = locale;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getLogoServingUrl()
    {
        return logoServingUrl;
    }

    /**
     * @param logoServingUrl logoServingUrl or {@code null} for none
     */
    public Customer setLogoServingUrl( String logoServingUrl )
    {
        this.logoServingUrl = logoServingUrl;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public CustomerPostalAddress getPostalAddress()
    {
        return postalAddress;
    }

    /**
     * @param postalAddress postalAddress or {@code null} for none
     */
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

    /**
     * @param postcode postcode or {@code null} for none
     */
    public Customer setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * @param prefix prefix or {@code null} for none
     */
    public Customer setPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * @param street street or {@code null} for none
     */
    public Customer setStreet( String street )
    {
        this.street = street;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getSuffix()
    {
        return suffix;
    }

    /**
     * @param suffix suffix or {@code null} for none
     */
    public Customer setSuffix( String suffix )
    {
        this.suffix = suffix;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getTaxId()
    {
        return taxId;
    }

    /**
     * @param taxId taxId or {@code null} for none
     */
    public Customer setTaxId( String taxId )
    {
        this.taxId = taxId;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getVatId()
    {
        return vatId;
    }

    /**
     * @param vatId vatId or {@code null} for none
     */
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
                middleName,
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
