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

public final class ProductDomain
        implements RelevantNullChecker
{
    private String domain;

    private String name;

    private String subdomain;

    private String uri;

    private String url;

    public String getDomain()
    {
        return domain;
    }

    public ProductDomain setDomain( String domain )
    {
        this.domain = domain;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public ProductDomain setName( String name )
    {
        this.name = name;
        return this;
    }

    public String getSubdomain()
    {
        return subdomain;
    }

    public ProductDomain setSubdomain( String subdomain )
    {
        this.subdomain = subdomain;
        return this;
    }

    public String getUri()
    {
        return uri;
    }

    public ProductDomain setUri( String uri )
    {
        this.uri = uri;
        return this;
    }

    public String getUrl()
    {
        return url;
    }

    public ProductDomain setUrl( String url )
    {
        this.url = url;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( name, uri );
    }
}
