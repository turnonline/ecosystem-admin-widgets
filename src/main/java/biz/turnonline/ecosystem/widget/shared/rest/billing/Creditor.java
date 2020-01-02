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
 * on 2019-12-26 at 05:51:20 UTC
 * Modify at your own risk.
 */

package biz.turnonline.ecosystem.widget.shared.rest.billing;

/**
 * Model definition for Creditor.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Product Billing. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
public final class Creditor
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
    private String city;

    /**
     * The value may be {@code null}.
     */
    private String companyId;

    /**
     * The value may be {@code null}.
     */
    private CreditorContactDetails contact;

    /**
     * The value may be {@code null}.
     */
    private String country;

    /**
     * The value may be {@code null}.
     */
    private Double latitude;

    /**
     * The value may be {@code null}.
     */
    private String legalForm;

    /**
     * The value may be {@code null}.
     */
    private String logoServingUrl;

    /**
     * The value may be {@code null}.
     */
    private Double longitude;

    /**
     * The value may be {@code null}.
     */
    private String postcode;

    /**
     * The value may be {@code null}.
     */
    private String street;

    /**
     * The value may be {@code null}.
     */
    private String taxId;

    /**
     * The value may be {@code null}.
     */
    private String vatId;

    /**
     * The value may be {@code null}.
     */
    private Boolean vatPayer;

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
    public Creditor setAccount( Long account )
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
    public Creditor setBusinessName( String businessName )
    {
        this.businessName = businessName;
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
    public Creditor setCity( String city )
    {
        this.city = city;
        return this;
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
    public Creditor setCompanyId( String companyId )
    {
        this.companyId = companyId;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public CreditorContactDetails getContact()
    {
        return contact;
    }

    /**
     * @param contact contact or {@code null} for none
     */
    public Creditor setContact( CreditorContactDetails contact )
    {
        this.contact = contact;
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
    public Creditor setCountry( String country )
    {
        this.country = country;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getLatitude()
    {
        return latitude;
    }

    /**
     * @param latitude latitude or {@code null} for none
     */
    public Creditor setLatitude( Double latitude )
    {
        this.latitude = latitude;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getLegalForm()
    {
        return legalForm;
    }

    /**
     * @param legalForm legalForm or {@code null} for none
     */
    public Creditor setLegalForm( String legalForm )
    {
        this.legalForm = legalForm;
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
    public Creditor setLogoServingUrl( String logoServingUrl )
    {
        this.logoServingUrl = logoServingUrl;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getLongitude()
    {
        return longitude;
    }

    /**
     * @param longitude longitude or {@code null} for none
     */
    public Creditor setLongitude( Double longitude )
    {
        this.longitude = longitude;
        return this;
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
    public Creditor setPostcode( String postcode )
    {
        this.postcode = postcode;
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
    public Creditor setStreet( String street )
    {
        this.street = street;
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
    public Creditor setTaxId( String taxId )
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
    public Creditor setVatId( String vatId )
    {
        this.vatId = vatId;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Boolean getVatPayer()
    {
        return vatPayer;
    }

    /**
     * @param vatPayer vatPayer or {@code null} for none
     */
    public Creditor setVatPayer( Boolean vatPayer )
    {
        this.vatPayer = vatPayer;
        return this;
    }
}