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
