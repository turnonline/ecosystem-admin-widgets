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
