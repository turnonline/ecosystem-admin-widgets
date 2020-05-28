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

public final class Merchant
{
    private String category;

    private String city;

    private String name;

    public String getCategory()
    {
        return category;
    }

    public Merchant setCategory( String category )
    {
        this.category = category;
        return this;
    }

    public String getCity()
    {
        return city;
    }

    public Merchant setCity( String city )
    {
        this.city = city;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Merchant setName( String name )
    {
        this.name = name;
        return this;
    }
}
