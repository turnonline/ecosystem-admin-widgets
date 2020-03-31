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

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import org.ctoolkit.gwt.client.facade.FirebaseAuthFacade;
import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.callback.DefaultFilterawareRequestCallback;

/**
 * The authentication dispatcher that populates every REST request with credential.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class FirebaseAuthDispatcher
        implements Dispatcher
{
    public static final FirebaseAuthDispatcher INSTANCE = new FirebaseAuthDispatcher();

    private final FirebaseAuthFacade authFacade = new FirebaseAuthFacade();

    @Override
    public Request send( Method method, RequestBuilder builder )
    {
        AppEventBus.get().fireEvent( new RestCallEvent( RestCallEvent.Direction.OUT ) );

        DefaultFilterawareRequestCallback filtered = new DefaultFilterawareRequestCallback( method );
        filtered.addFilter( ( m, response, callback ) -> {
            AppEventBus.get().fireEvent( new RestCallEvent( RestCallEvent.Direction.IN ) );
            return callback;
        } );

        builder.setCallback( filtered );
        authFacade.send( builder );
        return null;
    }
}
