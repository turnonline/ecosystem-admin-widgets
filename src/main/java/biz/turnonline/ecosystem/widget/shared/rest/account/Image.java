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

/**
 * Image details.
 **/
public class Image
        implements RelevantNullChecker
{
    private String storageName;

    private String servingUrl;

    /**
     * The full path to the image. It's an identification in the underlying storage.
     **/
    public Image storageName( String storageName )
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
     * The full URL of the image served from the content delivery network (CDN). Provided by the service once an image ('storageName') has been uploaded.
     **/
    public Image servingUrl( String servingUrl )
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

