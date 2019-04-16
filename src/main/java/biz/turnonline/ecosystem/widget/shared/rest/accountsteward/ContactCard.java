package biz.turnonline.ecosystem.widget.shared.rest.accountsteward;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ContactCard
        implements Contact
{
    private Long id = null;

    private String contactEmail = null;

    private String ccEmail = null;

    private String contactPhone = null;

    private String locale = null;

    private String prefix = null;

    private String firstName = null;

    private String lastName = null;

    private String suffix = null;

    private String street = null;

    private String city = null;

    private String country = null;

    private String postcode = null;

    private Double latitude = null;

    private Double longitude = null;

    private Boolean hasPostalAddress = false;

    private ContactCardPostalAddress postalAddress = null;

    private Boolean company = false;

    private String businessName = null;

    private String companyId = null;

    private String taxId = null;

    private String vatId = null;

    private Boolean vatPayer = false;

    private Boolean newsletter = null;

    private Integer numberOfDays = null;

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail( String contactEmail )
    {
        this.contactEmail = contactEmail;
    }

    public String getCcEmail()
    {
        return ccEmail;
    }

    public void setCcEmail( String ccEmail )
    {
        this.ccEmail = ccEmail;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone( String contactPhone )
    {
        this.contactPhone = contactPhone;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale( String locale )
    {
        this.locale = locale;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix( String suffix )
    {
        this.suffix = suffix;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode( String postcode )
    {
        this.postcode = postcode;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude( Double latitude )
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude( Double longitude )
    {
        this.longitude = longitude;
    }

    public Boolean getHasPostalAddress()
    {
        return hasPostalAddress;
    }

    public void setHasPostalAddress( Boolean hasPostalAddress )
    {
        this.hasPostalAddress = hasPostalAddress;
    }

    public ContactCardPostalAddress getPostalAddress()
    {
        return postalAddress;
    }

    public void setPostalAddress( ContactCardPostalAddress postalAddress )
    {
        this.postalAddress = postalAddress;
    }

    public Boolean getCompany()
    {
        return company;
    }

    public void setCompany( Boolean company )
    {
        this.company = company;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName( String businessName )
    {
        this.businessName = businessName;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( String companyId )
    {
        this.companyId = companyId;
    }

    public String getTaxId()
    {
        return taxId;
    }

    public void setTaxId( String taxId )
    {
        this.taxId = taxId;
    }

    public String getVatId()
    {
        return vatId;
    }

    public void setVatId( String vatId )
    {
        this.vatId = vatId;
    }

    public Boolean getVatPayer()
    {
        return vatPayer;
    }

    public void setVatPayer( Boolean vatPayer )
    {
        this.vatPayer = vatPayer;
    }

    public Boolean getNewsletter()
    {
        return newsletter;
    }

    public void setNewsletter( Boolean newsletter )
    {
        this.newsletter = newsletter;
    }

    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    public void setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
    }
}
