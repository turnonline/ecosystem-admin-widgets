/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared.rest.account;

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

/**
 * Logo image details.
 **/
public class Logo
        implements RelevantNullChecker
{
    private String storageName;

    private String servingUrl;

    /**
     * The full path to the logo. It's an identification in the underlying storage.
     **/
    public Logo storageName( String storageName )
    {
        this.storageName = storageName;
        return this;
    }

    public String getStorageName()
    {
        return storageName;
    }

    public void setStorageName( String storageName )
    {
        this.storageName = storageName;
    }

    /**
     * The full URL of the logo served from the content delivery network (CDN). Provided by the service once an image ('storageName') has been uploaded.
     **/
    public Logo servingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
        return this;
    }

    public String getServingUrl()
    {
        return servingUrl;
    }

    public void setServingUrl( String servingUrl )
    {
        this.servingUrl = servingUrl;
    }

    @Override
    public boolean allNull()
    {
        return allNull( storageName );
    }
}

