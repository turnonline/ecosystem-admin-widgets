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

package biz.turnonline.ecosystem.widget.shared.rest.account;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;

import java.util.Date;

/**
 * The contact that represents a business partner (customer).
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ContactCard
        implements Contact
{
    private Long id;

    private String contactEmail;

    private String ccEmail;

    private String contactPhone;

    private String locale;

    private String prefix;

    private String firstName;

    private String middleName;

    private String lastName;

    private String suffix;

    private String street;

    private String city;

    private String country;

    private String postcode;

    private Double latitude;

    private Double longitude;

    private Boolean hasPostalAddress = false;

    private ContactCardPostalAddress postalAddress;

    private Boolean company = false;

    private String businessName;

    private String companyId;

    private String taxId;

    private String vatId;

    private Boolean vatPayer = false;

    private Boolean newsletter;

    private Integer numberOfDays;

    private Date modificationDate;

    private Image logo;

    /**
     * The contact unique identification.
     **/
    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * The contact email used either for notification purposes or for dealing with a potential issues with order.
     **/
    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail( String contactEmail )
    {
        this.contactEmail = contactEmail;
    }

    /**
     * The contact email if defined, it will be used for notification purposes that will be used as 'CC' to copy email. Supports comma separated list.
     **/
    public String getCcEmail()
    {
        return ccEmail;
    }

    public void setCcEmail( String ccEmail )
    {
        this.ccEmail = ccEmail;
    }

    /**
     * The contact phone number used for dealing with potential issues with an order.
     **/
    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone( String contactPhone )
    {
        this.contactPhone = contactPhone;
    }

    /**
     * The preferred language of the issued invoice. If supported, the value will override the seller's account default language. ISO 639 alpha-2 or alpha-3 language code.
     **/
    public String getLocale()
    {
        return locale;
    }

    public void setLocale( String locale )
    {
        this.locale = locale;
    }

    /**
     * The personal name prefix.
     **/
    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    /**
     * The personal first name.
     **/
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    /**
     * The personal middle name.
     **/
    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName( String middleName )
    {
        this.middleName = middleName;
    }

    /**
     * The personal last name.
     **/
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    /**
     * The personal name suffix.
     **/
    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix( String suffix )
    {
        this.suffix = suffix;
    }

    /**
     * The street and street number.
     **/
    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    /**
     * The city.
     **/
    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    /**
     * The contact ISO 3166 alpha-2 country code. It's case insensitive.
     **/
    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    /**
     * The post code.
     **/
    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode( String postcode )
    {
        this.postcode = postcode;
    }

    /**
     * The contact address latitude geographic coordinate, generated by the service.
     **/
    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude( Double latitude )
    {
        this.latitude = latitude;
    }

    /**
     * The contact address longitude geographic coordinate, generated by the service.
     **/
    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude( Double longitude )
    {
        this.longitude = longitude;
    }

    /**
     * If true, the postal address is not same as the company/personal address and must be provided.
     **/
    public Boolean getHasPostalAddress()
    {
        return hasPostalAddress;
    }

    public void setHasPostalAddress( Boolean hasPostalAddress )
    {
        this.hasPostalAddress = hasPostalAddress;
    }

    /**
     * The postal address details.
     **/
    public ContactCardPostalAddress getPostalAddress()
    {
        return postalAddress;
    }

    public void setPostalAddress( ContactCardPostalAddress postalAddress )
    {
        this.postalAddress = postalAddress;
    }

    /**
     * The boolean indication whether contact represents a business entity. The missing value or false means it represents a personal contact (not business entity).
     **/
    public Boolean getCompany()
    {
        return company;
    }

    public void setCompany( Boolean company )
    {
        this.company = company;
    }

    /**
     * The company business name.
     **/
    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName( String businessName )
    {
        this.businessName = businessName;
    }

    /**
     * The company business identification number.
     **/
    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( String companyId )
    {
        this.companyId = companyId;
    }

    /**
     * The tax payer identification number of the company.
     **/
    public String getTaxId()
    {
        return taxId;
    }

    public void setTaxId( String taxId )
    {
        this.taxId = taxId;
    }

    /**
     * The value added tax identification number (VAT ID) of the company. Provided if vatPayer property is true.
     **/
    public String getVatId()
    {
        return vatId;
    }

    public void setVatId( String vatId )
    {
        this.vatId = vatId;
    }

    /**
     * The boolean indication whether company is registered as VAT payer. The missing value or false means company it's not a VAT payer.
     **/
    public Boolean getVatPayer()
    {
        return vatPayer;
    }

    public void setVatPayer( Boolean vatPayer )
    {
        this.vatPayer = vatPayer;
    }

    /**
     * The indication whether this contact has given a consent to be subscribed to the seller's newsletter.
     **/
    public Boolean getNewsletter()
    {
        return newsletter;
    }

    public void setNewsletter( Boolean newsletter )
    {
        this.newsletter = newsletter;
    }

    /**
     * The number of days for calculation of the invoice due date for this specific contact.
     **/
    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    public void setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
    }

    /**
     * The date and time of the last modification of entity values. Populated by the service.
     **/
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public void setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
    }

    /**
     * The company logo.
     **/
    public Image getLogo()
    {
        return logo;
    }

    public void setLogo( Image logo )
    {
        this.logo = logo;
    }
}
