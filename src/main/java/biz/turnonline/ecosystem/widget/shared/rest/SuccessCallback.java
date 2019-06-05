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

package biz.turnonline.ecosystem.widget.shared.rest;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import com.google.gwt.core.client.GWT;
import gwt.material.design.client.ui.MaterialToast;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * {@link MethodCallback} turned in to {@link SuccessCallback} that supports lambda syntax.
 * <p>
 * Use this interface if you need a notification only about a success. In case of failure
 * only an error message in red colour will popup, see {@link MaterialToast#fireToast(String, String)}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@FunctionalInterface
public interface SuccessCallback<T>
        extends FacadeCallback<T>
{
    /**
     * Called when asynchronous call completes successfully.
     */
    void onSuccess( T response );

    @Override
    default void done( T response, Failure failure )
    {
        if ( failure.isSuccess() )
        {
            onSuccess( response );
        }
        else
        {
            handleError( failure );
        }
    }

    default void handleError( Failure failure )
    {
        if ( failure.isNotFound() )
        {
            MaterialToast.fireToast( AppMessages.INSTANCE.msgErrorRecordDoesNotExists(), "red" );
        }
        else
        {
            MaterialToast.fireToast( AppMessages.INSTANCE.msgErrorRemoteServiceCall(), "red" );
            GWT.log( "Exception has occurred while calling remote service: " + failure.response().getText() );
        }
    }
}
