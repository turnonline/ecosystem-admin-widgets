package biz.turnonline.ecosystem.widget.shared;

import com.google.gwt.i18n.client.Dictionary;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.ServiceRoots;

import static org.ctoolkit.gwt.client.Constants.REST_DATE_FORMAT;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Configuration
{
    public static Configuration instance;

    public static final String CONFIGURATION_OBJECT = "Configuration";

    public static final String DOMICILE = "DOMICILE";

    public static final String CURRENCY = "CURRENCY";

    public static final String VAT = "VAT";

    public static final String LOGIN_ID = "LOGIN_ID";

    public static final String ACCOUNT_STEWARD_API_ROOT = "ACCOUNT_STEWARD_API_ROOT";

    public static final String PRODUCT_BILLING_API_ROOT = "PRODUCT_BILLING_API_ROOT";

    public static final String MAPS_API_KEY = "MAPS_API_KEY";

    private String domicile;

    private String currency;

    private String vat;

    private String loginId;

    private String mapsApiKey;

    /**
     * Builds {@link Configuration} instance taken from the {@link Dictionary}.
     *
     * @return the user profile instance
     */
    private static Configuration build()
    {
        Dictionary dictionary = Dictionary.getDictionary( CONFIGURATION_OBJECT );

        ServiceRoots.add( ACCOUNT_STEWARD_API_ROOT, dictionary.get( ACCOUNT_STEWARD_API_ROOT ) );
        ServiceRoots.add( PRODUCT_BILLING_API_ROOT, dictionary.get( PRODUCT_BILLING_API_ROOT ) );

        Defaults.setDateFormat( REST_DATE_FORMAT );
        Defaults.ignoreJsonNulls();

        Configuration configuration = new Configuration();
        configuration.setDomicile( dictionary.get( DOMICILE ) );
        configuration.setCurrency( dictionary.get( CURRENCY ) );
        configuration.setVat( dictionary.get( VAT ) );
        configuration.setLoginId( dictionary.get( LOGIN_ID ) );
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

    public String getMapsApiKey()
    {
        return mapsApiKey;
    }

    public void setMapsApiKey( String mapsApiKey )
    {
        this.mapsApiKey = mapsApiKey;
    }
}
