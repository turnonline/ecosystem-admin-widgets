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

package biz.turnonline.ecosystem.widget.shared.presenter;

import org.ctoolkit.gwt.client.facade.TokenCallback;
import org.fusesource.restygwt.client.ServiceRoots;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Uploader token callback that manages a final upload URL incl. resolved {@code access_token}
 * as a query parameter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface UploaderTokenCallback
        extends TokenCallback
{
    String UPLOAD_BASE_PATH = "storage-upload";

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
     * Notification with full upload URL incl. token appended as a query parameter.
     * If none token appended, user does not have an authenticated Firebase session.
     *
     * @param url the full upload URL incl. token
     */
    void then( @Nonnull String url );

    /**
     * If key parameter is {@code null} the final URL will be relative.
     */
    @Override
    default void then( @Nullable String token, @Nullable String key )
    {
        String url = key == null ? "/" + UPLOAD_BASE_PATH : url( key );
        then( url + ( token == null ? "" : "?access_token=" + token ) );
    }
}
