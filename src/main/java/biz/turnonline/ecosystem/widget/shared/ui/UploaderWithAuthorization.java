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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import com.google.gwt.core.client.JavaScriptObject;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import org.ctoolkit.gwt.client.facade.FirebaseAuthFacade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * The uploader that handles authentication by default and additional header parameters via {@link Headers}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 * @see UploaderTokenCallback
 */
public class UploaderWithAuthorization
        extends MaterialFileUploader
{
    private final String urlKey;

    /**
     * Constructor.
     *
     * @param urlKey a key to lookup for configured service URL in {@link Configuration}
     */
    public UploaderWithAuthorization( @Nonnull String urlKey )
    {
        this.urlKey = checkNotNull( urlKey, "URL key can't be null" );
    }

    @Override
    public void load()
    {
        new FirebaseAuthFacade().getIdToken( ( UploaderTokenCallback ) this::urlDone, urlKey );
    }

    private void urlDone( @Nonnull String url, @Nullable String token )
    {
        Headers headers = ( Headers ) getHeaders();
        if ( headers == null )
        {
            // Make sure Uploader will always use the same instance created at the time of the initialization.
            // Otherwise changes will be ignored (unknown reason).
            headers = Headers.createObject();
            setHeaders( headers );
        }

        if ( token != null )
        {
            headers.setAuthorization( token );
        }

        append( headers );

        // First set URL and than load widget, otherwise firebase will be executed after widget initialization
        setUrl( url );
        super.load();
    }

    /**
     * Override if you need to add an additional header key/value pair.
     * Called right before {@link #load()} will be executed.
     *
     * @param headers instance to be populated
     */
    protected void append( @Nonnull Headers headers )
    {
    }

    /**
     * The {@link MaterialFileUploader} related headers:
     * <ul>
     *     <li>Authorization - Bearer token</li>
     *     <li>vnd.turnon.cloud.associated-id - ID as an identification of a record
     *     that will be associated with the uploaded data</li>
     *     <li>vnd.turnon.cloud.logo-image
     *     - boolean value indicating whether uploaded image is being intended to be a logo</li>
     *     <li>vnd.turnon.cloud.stamp-image
     *     - boolean value indicating whether uploaded image is being intended to be a stamp</li>
     * </ul>
     */
    public static class Headers
            extends JavaScriptObject
    {
        protected Headers()
        {
        }

        public static native Headers createObject() /*-{
            return {};
        }-*/;

        public final native void setAuthorization( String token ) /*-{
            this['Authorization'] = "Bearer " + token;
        }-*/;

        public final native void setAssociatedId( String billId ) /*-{
            this['vnd.turnon.cloud.associated-id'] = billId;
        }-*/;

        public final native void setLogoImage( String is ) /*-{
            this['vnd.turnon.cloud.logo-image'] = is;
        }-*/;

        public final native void setStampImage( String is ) /*-{
            this['vnd.turnon.cloud.stamp-image'] = is;
        }-*/;
    }
}
