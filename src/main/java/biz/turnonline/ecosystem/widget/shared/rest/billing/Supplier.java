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

public final class Supplier
{
    private String businessName;

    private String city;

    private String companyId;

    private String country;

    private String postcode;

    private String street;

    private String taxId;

    private String vatId;

    public String getBusinessName()
    {
        return businessName;
    }

    public Supplier setBusinessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    public String getCity()
    {
        return city;
    }

    public Supplier setCity( String city )
    {
        this.city = city;
        return this;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public Supplier setCompanyId( String companyId )
    {
        this.companyId = companyId;
        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public Supplier setCountry( String country )
    {
        this.country = country;
        return this;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public Supplier setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public Supplier setStreet( String street )
    {
        this.street = street;
        return this;
    }

    public String getTaxId()
    {
        return taxId;
    }

    public Supplier setTaxId( String taxId )
    {
        this.taxId = taxId;
        return this;
    }

    public String getVatId()
    {
        return vatId;
    }

    public Supplier setVatId( String vatId )
    {
        this.vatId = vatId;
        return this;
    }
}
