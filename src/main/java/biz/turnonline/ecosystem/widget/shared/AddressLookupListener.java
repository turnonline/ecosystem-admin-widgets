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

package biz.turnonline.ecosystem.widget.shared;

import gwt.material.design.client.api.ApiFeature;
import gwt.material.design.client.api.ApiRegistry;

import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * As Google Maps JavaScript by {@link ApiRegistry#register(ApiFeature, com.google.gwt.core.client.Callback)}
 * cannot be included multiple times on a single page, initialization must be guaranteed to be executed only once.
 * <p>
 * Make sure in your module this listener will be a {@link javax.inject.Singleton},
 * consider following in your dagger module:
 * <pre>
 * {@code
 * @Singleton
 * @Provides
 * static AddressLookupListener provideAddressLookupListener( Configuration config )
 * {
 *     String mapsApiKey = config.getMapsApiKey();
 *     AddressLookupListener listener = new AddressLookupListener();
 *
 *     ApiRegistry.register( new AddressLookupApi( mapsApiKey ), new Callback<Void, Exception>()
 *     {
 *         @Override
 *         public void onFailure( Exception reason )
 *         {
 *             GWT.log( "Error occur during registration google maps api", reason );
 *         }
 *
 *         @Override
 *         public void onSuccess( Void result )
 *         {
 *             listener.onSuccess();
 *         }
 *     } );
 *
 *     return listener;
 * }
 * }
 * </pre>
 * and then let it inject in to your View implementation
 * <pre>
 * {@code
 * @Inject
 * public MyView( EventBus eventBus, AddressLookupListener addressLookup )
 * {
 *     super( eventBus );
 *
 *     // Loading google map API
 *     addressLookup.onLoad( () -> street.load() );
 * }
 * }
 * </pre>
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class AddressLookupListener
{
    private List<Callback> callbacks = new ArrayList<>();

    /**
     * Delegated callback, gets notification when address lookup completes successfully.
     */
    public void onLoad( Callback callback )
    {
        callbacks.add( checkNotNull( callback ) );
    }

    /**
     * Called when address lookup completes successfully.
     */
    public void onSuccess()
    {
        for ( Callback callback : callbacks )
        {
            callback.onSuccess();
        }
    }

    public interface Callback
    {
        void onSuccess();
    }
}
