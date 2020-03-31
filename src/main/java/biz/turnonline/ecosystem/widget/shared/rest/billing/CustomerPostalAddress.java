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

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

public final class CustomerPostalAddress
        implements RelevantNullChecker
{
    private String businessName;

    private String city;

    private String country;

    private String firstName;

    private String lastName;

    private String postcode;

    private String prefix;

    private String street;

    private String suffix;

    public String getBusinessName()
    {
        return businessName;
    }

    public CustomerPostalAddress setBusinessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    public String getCity()
    {
        return city;
    }

    public CustomerPostalAddress setCity( String city )
    {
        this.city = city;
        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public CustomerPostalAddress setCountry( String country )
    {
        this.country = country;
        return this;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public CustomerPostalAddress setFirstName( String firstName )
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public CustomerPostalAddress setLastName( String lastName )
    {
        this.lastName = lastName;
        return this;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public CustomerPostalAddress setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public CustomerPostalAddress setPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public CustomerPostalAddress setStreet( String street )
    {
        this.street = street;
        return this;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public CustomerPostalAddress setSuffix( String suffix )
    {
        this.suffix = suffix;
        return this;
    }

    @Override
    public boolean allNull()
    {
        // country is excluded from the check.
        // If only country property has set (default) it means no user input so ignore.
        return allNull( businessName,
                city,
                firstName,
                lastName,
                postcode,
                prefix,
                street,
                suffix
        );
    }
}
