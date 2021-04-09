/*
 *  Copyright (c) 2021 TurnOnline.biz s.r.o.
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

/**
 * The pure offer definition, acts as a request.
 */
public class PureOffer
        implements RelevantNullChecker
{
    private OfferCustomer customer;

    private String picture;

    private String snippet;

    private String title;

    /**
     * The identification of the customer as a recipient of the offer.
     **/
    public OfferCustomer getCustomer()
    {
        return customer;
    }

    public void setCustomer( OfferCustomer customer )
    {
        this.customer = customer;
    }

    public void setCustomerIf( OfferCustomer customer )
    {
        setIfNotAllNull( this::setCustomer, customer );
    }

    /**
     * The storage name of the picture to be processed as a serving URL (CDN).
     **/
    public String getPicture()
    {
        return picture;
    }

    public void setPicture( String picture )
    {
        this.picture = picture;
    }

    /**
     * The short description of the offer.  It might be a short description one of the product at purchase order. Alternatively it might be also a direct message to the target customer. The max length is set to 500 characters.
     **/
    public String getSnippet()
    {
        return snippet;
    }

    public void setSnippet( String snippet )
    {
        this.snippet = snippet;
    }

    /**
     * The offer title. A text that should take an user attention.
     **/
    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    @Override
    public boolean allNull()
    {
        return allNull( customer, picture, snippet, title );
    }
}

