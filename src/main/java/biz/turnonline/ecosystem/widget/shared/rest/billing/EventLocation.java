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

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

public final class EventLocation
        implements RelevantNullChecker
{
    private String city;

    private String country;

    private String infoEmail;

    private String infoPhone;

    private Double latitude;

    private Double longitude;

    private String name;

    private String postcode;

    private String street;

    public String getCity()
    {
        return city;
    }

    public EventLocation setCity( String city )
    {
        this.city = city;
        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public EventLocation setCountry( String country )
    {
        this.country = country;
        return this;
    }

    public String getInfoEmail()
    {
        return infoEmail;
    }

    public EventLocation setInfoEmail( String infoEmail )
    {
        this.infoEmail = infoEmail;
        return this;
    }

    public String getInfoPhone()
    {
        return infoPhone;
    }

    public EventLocation setInfoPhone( String infoPhone )
    {
        this.infoPhone = infoPhone;
        return this;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public EventLocation setLatitude( Double latitude )
    {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public EventLocation setLongitude( Double longitude )
    {
        this.longitude = longitude;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public EventLocation setName( String name )
    {
        this.name = name;
        return this;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public EventLocation setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public EventLocation setStreet( String street )
    {
        this.street = street;
        return this;
    }

    @Override
    public boolean allNull()
    {
        // country is excluded from the check.
        // If only country property has set (default) it means no user input so ignore.
        return allNull( city, infoEmail, infoPhone, name, postcode, street );
    }
}
