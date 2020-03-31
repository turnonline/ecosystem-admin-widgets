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

public final class Creditor
{
    private Long account;

    private String businessName;

    private String city;

    private String companyId;

    private CreditorContactDetails contact;

    private String country;

    private Double latitude;

    private String legalForm;

    private String logoServingUrl;

    private Double longitude;

    private String postcode;

    private String street;

    private String taxId;

    private String vatId;

    private Boolean vatPayer;

    public Long getAccount()
    {
        return account;
    }

    public Creditor setAccount( Long account )
    {
        this.account = account;
        return this;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public Creditor setBusinessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    public String getCity()
    {
        return city;
    }

    public Creditor setCity( String city )
    {
        this.city = city;
        return this;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public Creditor setCompanyId( String companyId )
    {
        this.companyId = companyId;
        return this;
    }

    public CreditorContactDetails getContact()
    {
        return contact;
    }

    public Creditor setContact( CreditorContactDetails contact )
    {
        this.contact = contact;
        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public Creditor setCountry( String country )
    {
        this.country = country;
        return this;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public Creditor setLatitude( Double latitude )
    {
        this.latitude = latitude;
        return this;
    }

    public String getLegalForm()
    {
        return legalForm;
    }

    public Creditor setLegalForm( String legalForm )
    {
        this.legalForm = legalForm;
        return this;
    }

    public String getLogoServingUrl()
    {
        return logoServingUrl;
    }

    public Creditor setLogoServingUrl( String logoServingUrl )
    {
        this.logoServingUrl = logoServingUrl;
        return this;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public Creditor setLongitude( Double longitude )
    {
        this.longitude = longitude;
        return this;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public Creditor setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public Creditor setStreet( String street )
    {
        this.street = street;
        return this;
    }

    public String getTaxId()
    {
        return taxId;
    }

    public Creditor setTaxId( String taxId )
    {
        this.taxId = taxId;
        return this;
    }

    public String getVatId()
    {
        return vatId;
    }

    public Creditor setVatId( String vatId )
    {
        this.vatId = vatId;
        return this;
    }

    public Boolean getVatPayer()
    {
        return vatPayer;
    }

    public Creditor setVatPayer( Boolean vatPayer )
    {
        this.vatPayer = vatPayer;
        return this;
    }
}
