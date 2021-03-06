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

package biz.turnonline.ecosystem.widget.shared.rest.bill;

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

import java.util.Objects;

/**
 * Supplier
 */
public class Supplier
        implements RelevantNullChecker
{
    private String businessName;

    private String companyId;

    private String taxId;

    private String vatId;

    private String street;

    private String city;

    private String country;

    private String postcode;

    public Supplier businessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    /**
     * Supplier business name.
     **/
    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName( String businessName )
    {
        this.businessName = businessName;
    }

    public Supplier companyId( String companyId )
    {
        this.companyId = companyId;
        return this;
    }

    /**
     * Supplier business identification number.
     **/
    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( String companyId )
    {
        this.companyId = companyId;
    }

    public Supplier taxId( String taxId )
    {
        this.taxId = taxId;
        return this;
    }

    /**
     * Supplier tax payer identification number.
     **/
    public String getTaxId()
    {
        return taxId;
    }

    public void setTaxId( String taxId )
    {
        this.taxId = taxId;
    }

    public Supplier vatId( String vatId )
    {
        this.vatId = vatId;
        return this;
    }

    /**
     * Supplier vat payer identification number.
     **/
    public String getVatId()
    {
        return vatId;
    }

    public void setVatId( String vatId )
    {
        this.vatId = vatId;
    }

    public Supplier street( String street )
    {
        this.street = street;
        return this;
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

    public Supplier city( String city )
    {
        this.city = city;
        return this;
    }

    /**
     * The address city.
     **/
    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public Supplier country( String country )
    {
        this.country = country;
        return this;
    }

    /**
     * The address ISO 3166 alpha-2 country code. It’s case insensitive.
     **/
    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    public Supplier postcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    /**
     * The address post code.
     **/
    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode( String postcode )
    {
        this.postcode = postcode;
    }


    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        Supplier supplier = ( Supplier ) o;
        return Objects.equals( this.businessName, supplier.businessName ) &&
                Objects.equals( this.companyId, supplier.companyId ) &&
                Objects.equals( this.taxId, supplier.taxId ) &&
                Objects.equals( this.vatId, supplier.vatId ) &&
                Objects.equals( this.street, supplier.street ) &&
                Objects.equals( this.city, supplier.city ) &&
                Objects.equals( this.country, supplier.country ) &&
                Objects.equals( this.postcode, supplier.postcode );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( businessName, companyId, taxId, vatId, street, city, country, postcode );
    }


    @Override
    public String toString()
    {
        return "class Supplier {\n" +
                "    businessName: " + toIndentedString( businessName ) + "\n" +
                "    companyId: " + toIndentedString( companyId ) + "\n" +
                "    taxId: " + toIndentedString( taxId ) + "\n" +
                "    vatId: " + toIndentedString( vatId ) + "\n" +
                "    street: " + toIndentedString( street ) + "\n" +
                "    city: " + toIndentedString( city ) + "\n" +
                "    country: " + toIndentedString( country ) + "\n" +
                "    postcode: " + toIndentedString( postcode ) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString( Object o )
    {
        if ( o == null )
        {
            return "null";
        }
        return o.toString().replace( "\n", "\n    " );
    }

    @Override
    public boolean allNull()
    {
        return allNull( businessName, companyId, taxId, vatId, street, city, country, postcode );
    }
}

