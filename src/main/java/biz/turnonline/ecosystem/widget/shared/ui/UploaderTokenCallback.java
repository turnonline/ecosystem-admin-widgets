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

import org.ctoolkit.gwt.client.facade.TokenCallback;
import org.fusesource.restygwt.client.ServiceRoots;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Uploader callback that takes a configured upload URL and current token.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface UploaderTokenCallback
        extends TokenCallback
{
    String UPLOAD_BASE_PATH = "upload";

    /**
     * Returns the full upload URL as a composition of the service root associated with given key and upload base path.
     *
     * @param key the service root key
     * @return the full upload URL
     */
    static String url( @Nonnull String key )
    {
        String url = ServiceRoots.get( key );
        if ( url == null )
        {
            throw new IllegalArgumentException( "No service root associated with key '" + key + "'" );
        }
        return url + UPLOAD_BASE_PATH;
    }

    /**
     * Notification with full upload URL and current token.
     * If token is {@code null}, user does not have an authenticated Firebase session.
     *
     * @param url the full upload URL incl. token
     */
    void done( @Nonnull String url, @Nullable String token );

    /**
     * If key parameter is {@code null} the final URL will be relative.
     */
    @Override
    default void then( @Nullable String token, @Nullable String key )
    {
        String url = key == null ? "/" + UPLOAD_BASE_PATH : url( key );
        done( url, token );
    }
}
