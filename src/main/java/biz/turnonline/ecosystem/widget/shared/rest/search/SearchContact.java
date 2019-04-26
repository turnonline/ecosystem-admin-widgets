package biz.turnonline.ecosystem.widget.shared.rest.search;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SearchContact
        implements Contact
{
    private String id = null;

    private String firstName = null;

    private String middleName = null;

    private String lastName = null;

    private String companyId = null;

    private String businessName = null;

    private String owner = null;

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

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getCompanyId()
    {
        return companyId;
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

    public void setCompanyId( String companyId )
    {
        this.companyId = companyId;
    }

    public String getBusinessName()
    {
        return businessName;
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

    public void setBusinessName( String businessName )
    {
        this.businessName = businessName;
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
