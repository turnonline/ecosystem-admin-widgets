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
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.base.UploadResponse;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import org.ctoolkit.gwt.client.facade.FirebaseAuthFacade;
import org.ctoolkit.gwt.client.facade.UploadItem;
import org.ctoolkit.gwt.client.facade.UploadItemsResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

    private final List<BeforeUploaderInitCallback> beforeInitCallbacks = new ArrayList<>();

    private final List<AppendHeadersCallback> headerCallbacks = new ArrayList<>();

    private final List<SuccessCallback> successCallbacks = new ArrayList<>();

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
        // this must be before header callbacks
        beforeInitCallbacks.forEach( BeforeUploaderInitCallback::beforeInit );

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

        Headers finalHeaders = headers;
        headerCallbacks.forEach( c -> c.append( finalHeaders ) );

        // First set URL and than load widget, otherwise firebase will be executed after widget initialization
        setUrl( url );
        super.load();
        reset();
    }

    /**
     * Adds callback that will be called right before {@link #load()} will be executed and URL set.
     */
    public void addBeforeUploaderInitCallback( BeforeUploaderInitCallback callback )
    {
        beforeInitCallbacks.add( callback );
    }

    /**
     * Adds callback that will be called right before {@link #load()} will be executed and URL set
     * with possibility to adjust request headers.
     */
    public void addAppendHeadersCallback( AppendHeadersCallback callback )
    {
        headerCallbacks.add( callback );
    }

    /**
     * Adds success callback to get a notification once upload has been successfully processed and its response parsed.
     * <p>
     * It ignores responses if
     * <ul>
     *     <li>response is Unauthorized (401)</li>
     *     <li>response is not valid,  it's different as 201</li>
     * </ul>
     */
    public void addSuccessCallback( SuccessCallback callback )
    {
        successCallbacks.add( callback );
        addSuccessHandler( this::onSuccess );
    }

    private void onSuccess( SuccessEvent<UploadFile> event )
    {
        UploadResponse response = event.getResponse();
        UploadItem uploadItem;

        if ( response.getCode() == 401 )
        {
            GWT.log( "Unauthorized" );
        }

        if ( response.getCode() != 201 )
        {
            GWT.log( "Response code: " + response.getCode() );
        }

        UploadItemsResponse json = JsonUtils.safeEval( response.getBody() );
        if ( json.getItems().length() > 0 )
        {
            uploadItem = json.getItems().get( 0 );
        }
        else
        {
            uploadItem = null;
        }

        if ( uploadItem != null )
        {
            String associatedId = uploadItem.getAssociatedId();
            if ( !Strings.isNullOrEmpty( associatedId ) )
            {
                try
                {
                    Long id = Long.valueOf( associatedId );
                    successCallbacks.forEach( e -> e.onSuccess( new UploadItemSuccessEvent( uploadItem, id ) ) );
                }
                catch ( NumberFormatException e )
                {
                    GWT.log( "Parsing of Associated ID failed for " + associatedId );
                    successCallbacks.forEach( sc -> sc.onSuccess( new UploadItemSuccessEvent( uploadItem ) ) );
                }
            }
        }
    }

    public interface BeforeUploaderInitCallback
    {
        /**
         * Pre-set uploader to the default values right before {@link #load()} will be executed,
         * authorization headers and URL set.
         */
        void beforeInit();
    }

    public interface AppendHeadersCallback
    {
        /**
         * Called right before {@link #load()} will be executed.
         *
         * @param headers instance to be populated by client
         */
        void append( @Nonnull Headers headers );
    }

    public interface SuccessCallback
    {
        /**
         * Called only if upload has been successfully processed and its response parsed.
         *
         * @param event the upload payload response
         */
        void onSuccess( UploadItemSuccessEvent event );
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

    public static class UploadItemSuccessEvent
    {
        private final UploadItem uploadItem;

        private final Long associatedId;

        public UploadItemSuccessEvent( @Nonnull UploadItem uploadItem )
        {
            this( uploadItem, null );
        }

        public UploadItemSuccessEvent( @Nonnull UploadItem uploadItem, @Nullable Long associatedId )
        {
            this.uploadItem = checkNotNull( uploadItem, "Upload item can't be null" );
            this.associatedId = associatedId;
        }

        /**
         * Always returns upload item.
         *
         * @return the successful response of the processed upload
         */
        public UploadItem getUploadItem()
        {
            return uploadItem;
        }

        /**
         * Returns ID that represents an identification of the concrete object
         * that's being associated with the uploaded BLOB.
         *
         * @return the ID of the associated object or {@code null} if missing in the response
         */
        public Long getAssociatedId()
        {
            return associatedId;
        }
    }
}
