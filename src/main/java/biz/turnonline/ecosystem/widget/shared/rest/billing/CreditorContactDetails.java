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

public final class CreditorContactDetails
{
    private String email;

    private String name;

    private String phone;

    private String website;

    public String getEmail()
    {
        return email;
    }

    public CreditorContactDetails setEmail( String email )
    {
        this.email = email;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public CreditorContactDetails setName( String name )
    {
        this.name = name;
        return this;
    }

    public String getPhone()
    {
        return phone;
    }

    public CreditorContactDetails setPhone( String phone )
    {
        this.phone = phone;
        return this;
    }

    public String getWebsite()
    {
        return website;
    }

    public CreditorContactDetails setWebsite( String website )
    {
        this.website = website;
        return this;
    }
}
