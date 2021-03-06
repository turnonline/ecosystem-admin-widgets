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

import java.util.Date;
import java.util.Objects;

/**
 * The domain as a representation where a certain product will be publicly available. As a first step a validated (ownership) domain must be created (naked domain) as a parent to the all of the sub-domains and product domains.  **There are 3 types of domains:** * NAKED - only the client defined property “domain” has a value as a composition of the domain name and TLD. * SUBDOMAIN - additonally “subdomain” is being defined along side with “domain” property. * PRODUCT - these client defined properties “domain”, “subdomain”, and “uri” represents a certain product that is publicly available as a web site.
 **/
public class Domain
{
    private String name;

    private String domain;

    private String subdomain;

    private Date modificationDate;

    private String uri;

    private String url;

    private Boolean verified;

    /**
     * The name that represents an unique domain specification. Only this is the value required to be present. The rest of the domain properties will be populated by the service.
     **/
    public Domain name( String name )
    {
        this.name = name;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * The domain unique across the globe. As a composition of the domain name and TLD. It will always be saved in lowercase letters. In case the domain property is being provided together with subdomain (can be provided even as an URL), only the naked domain part will be accepted for this property.
     **/
    public Domain domain( String domain )
    {
        this.domain = domain;
        return this;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain( String domain )
    {
        this.domain = domain;
    }

    /**
     * The optional sub-domain. If missing, this Domain represents a naked domain. It will always be saved in lowercase letters.
     **/
    public Domain subdomain( String subdomain )
    {
        this.subdomain = subdomain;
        return this;
    }

    public String getSubdomain()
    {
        return subdomain;
    }

    public void setSubdomain( String subdomain )
    {
        this.subdomain = subdomain;
    }

    /**
     * The date and time of the last modification of entity values. Populated by the service.
     **/
    public Domain modificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public void setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
    }

    /**
     * The optional path of a product (slash \"/\" prefix will be ignored). It will always be saved in lowercase letters.
     **/
    public Domain uri( String uri )
    {
        this.uri = uri;
        return this;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri( String uri )
    {
        this.uri = uri;
    }

    /**
     * The final URL, rendered by the service.
     **/
    public Domain url( String url )
    {
        this.url = url;
        return this;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    /**
     * The boolean indicating whether this domain has the verified ownership.
     **/
    public Domain verified( Boolean verified )
    {
        this.verified = verified;
        return this;
    }

    public Boolean getVerified()
    {
        return verified;
    }

    public void setVerified( Boolean verified )
    {
        this.verified = verified;
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
        Domain domain = ( Domain ) o;
        return Objects.equals( name, domain.name );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( name );
    }
}

