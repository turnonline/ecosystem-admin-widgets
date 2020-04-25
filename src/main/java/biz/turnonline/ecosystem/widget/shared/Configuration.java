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

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import gwt.material.design.client.api.ApiRegistry;
import gwt.material.design.incubator.client.google.addresslookup.api.AddressLookupApi;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.ServiceRoots;

import static org.ctoolkit.gwt.client.Constants.REST_DATE_FORMAT;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Configuration
{
    public static final String CONFIGURATION_OBJECT = "Configuration";

    public static final String DOMICILE = "DOMICILE";

    public static final String CURRENCY = "CURRENCY";

    public static final String VAT = "VAT";

    public static final String LOGIN_ID = "LOGIN_ID";

    public static final String LOGO = "LOGO";

    public static final String ACCOUNT_STEWARD_STORAGE = "ACCOUNT_STEWARD_STORAGE";

    public static final String PRODUCT_BILLING_STORAGE = "PRODUCT_BILLING_STORAGE";

    public static final String BILLING_PROCESSOR_STORAGE = "BILLING_PROCESSOR_STORAGE";

    public static final String ACCOUNT_STEWARD_API_ROOT = "ACCOUNT_STEWARD_API_ROOT";

    public static final String PRODUCT_BILLING_API_ROOT = "PRODUCT_BILLING_API_ROOT";

    public static final String BILLING_PROCESSOR_API_ROOT = "BILLING_PROCESSOR_API_ROOT";

    public static final String PAYMENT_PROCESSOR_API_ROOT = "PAYMENT_PROCESSOR_API_ROOT";

    public static final String SEARCH_API_ROOT = "SEARCH_API_ROOT";

    public static final String MAPS_API_KEY = "MAPS_API_KEY";

    /**
     * Single item from the code-book considered as a default value
     */
    private static final String LEGAL_FORM = "ltd";

    public static Configuration instance;

    private String domicile;

    private String currency;

    private String vat;

    private String loginId;

    private String logo;

    private String mapsApiKey;

    /**
     * Builds {@link Configuration} instance taken from the {@link Dictionary}.
     *
     * @return the user profile instance
     */
    private static Configuration build()
    {
        Dictionary dictionary = Dictionary.getDictionary( CONFIGURATION_OBJECT );

        ServiceRoots.add( ACCOUNT_STEWARD_STORAGE, dictionary.get( ACCOUNT_STEWARD_STORAGE ) );
        ServiceRoots.add( PRODUCT_BILLING_STORAGE, dictionary.get( PRODUCT_BILLING_STORAGE ) );
        ServiceRoots.add( BILLING_PROCESSOR_STORAGE, dictionary.get( BILLING_PROCESSOR_STORAGE ) );
        ServiceRoots.add( ACCOUNT_STEWARD_API_ROOT, dictionary.get( ACCOUNT_STEWARD_API_ROOT ) );
        ServiceRoots.add( PRODUCT_BILLING_API_ROOT, dictionary.get( PRODUCT_BILLING_API_ROOT ) );
        ServiceRoots.add( BILLING_PROCESSOR_API_ROOT, dictionary.get( BILLING_PROCESSOR_API_ROOT ) );
        ServiceRoots.add( PAYMENT_PROCESSOR_API_ROOT, dictionary.get( PAYMENT_PROCESSOR_API_ROOT ) );
        ServiceRoots.add( SEARCH_API_ROOT, dictionary.get( SEARCH_API_ROOT ) );

        Defaults.setDateFormat( REST_DATE_FORMAT );
        Defaults.ignoreJsonNulls();

        Configuration configuration = new Configuration();
        configuration.setDomicile( dictionary.get( DOMICILE ) );
        configuration.setCurrency( dictionary.get( CURRENCY ) );
        configuration.setVat( dictionary.get( VAT ) );
        configuration.setLoginId( dictionary.get( LOGIN_ID ) );
        configuration.setLogo( dictionary.get( LOGO ) );
        configuration.setMapsApiKey( dictionary.get( MAPS_API_KEY ) );

        instance = configuration;
        return configuration;
    }

    public static Configuration get()
    {
        if ( instance == null )
        {
            instance = build();
        }

        return instance;
    }

    public AddressLookupListener initAddressLookupListener()
    {
        String mapsApiKey = get().getMapsApiKey();
        AddressLookupListener listener = new AddressLookupListener();

        ApiRegistry.register( new AddressLookupApi( mapsApiKey ), new Callback<Void, Exception>()
        {
            @Override
            public void onFailure( Exception reason )
            {
                GWT.log( "Error occur during registration google maps api", reason );
            }

            @Override
            public void onSuccess( Void result )
            {
                listener.onSuccess();
            }
        } );

        return listener;
    }

    public String getDomicile()
    {
        return domicile;
    }

    public void setDomicile( String domicile )
    {
        this.domicile = domicile;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency( String currency )
    {
        this.currency = currency;
    }

    public String getVat()
    {
        return vat;
    }

    public void setVat( String vat )
    {
        this.vat = vat;
    }

    public String getLoginId()
    {
        return loginId;
    }

    public void setLoginId( String loginId )
    {
        this.loginId = loginId;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo( String logo )
    {
        this.logo = logo;
    }

    public String getMapsApiKey()
    {
        return mapsApiKey;
    }

    public void setMapsApiKey( String mapsApiKey )
    {
        this.mapsApiKey = mapsApiKey;
    }

    public String getLegalForm()
    {
        return LEGAL_FORM;
    }
}
