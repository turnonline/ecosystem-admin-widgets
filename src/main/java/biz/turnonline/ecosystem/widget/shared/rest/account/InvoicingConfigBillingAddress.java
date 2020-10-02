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

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

public class InvoicingConfigBillingAddress
        implements RelevantNullChecker
{
    private String businessName;

    private String street;

    private String city;

    private String country;

    private String postcode;

    private Double latitude;

    private Double longitude;

    /**
     * The billing address business name.
     **/
    public InvoicingConfigBillingAddress businessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName( String businessName )
    {
        this.businessName = businessName;
    }

    /**
     * The billing address street and street number.
     **/
    public InvoicingConfigBillingAddress street( String street )
    {
        this.street = street;
        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    /**
     * The billing address city.
     **/
    public InvoicingConfigBillingAddress city( String city )
    {
        this.city = city;
        return this;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    /**
     * The billing address ISO 3166 alpha-2 country code. It's case insensitive.
     **/
    public InvoicingConfigBillingAddress country( String country )
    {
        this.country = country;
        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    /**
     * The billing address postal code.
     **/
    public InvoicingConfigBillingAddress postcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode( String postcode )
    {
        this.postcode = postcode;
    }

    /**
     * The billing address latitude geographic coordinate, generated by the service.
     **/
    public InvoicingConfigBillingAddress latitude( Double latitude )
    {
        this.latitude = latitude;
        return this;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude( Double latitude )
    {
        this.latitude = latitude;
    }

    /**
     * The billing address longitude geographic coordinate, generated by the service.
     **/
    public InvoicingConfigBillingAddress longitude( Double longitude )
    {
        this.longitude = longitude;
        return this;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude( Double longitude )
    {
        this.longitude = longitude;
    }

    @Override
    public boolean allNull()
    {
        return allNull( businessName, street, city, country, postcode );
    }
}

