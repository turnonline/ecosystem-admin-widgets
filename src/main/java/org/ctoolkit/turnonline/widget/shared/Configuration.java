package org.ctoolkit.turnonline.widget.shared;

import com.google.gwt.i18n.client.Dictionary;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.ServiceRoots;

import static org.ctoolkit.gwt.client.Constants.REST_DATE_FORMAT;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Configuration
{
    public static final String CONFIGURATION_OBJECT = "Configuration";

    public static final String LOGIN_ID = "LOGIN_ID";

    public static final String ACCOUNT_STEWARD_API_ROOT = "ACCOUNT_STEWARD_API_ROOT";

    private String loginId;

    private Configuration( String loginId )
    {
        this.loginId = loginId;
    }

    /**
     * Builds {@link Configuration} instance taken from the {@link Dictionary}.
     *
     * @return the user profile instance
     */
    public static Configuration build()
    {
        Dictionary dictionary = Dictionary.getDictionary( CONFIGURATION_OBJECT );
        String loginId = dictionary.get( LOGIN_ID );

        ServiceRoots.add( ACCOUNT_STEWARD_API_ROOT, dictionary.get( ACCOUNT_STEWARD_API_ROOT ) );

        Defaults.setDateFormat( REST_DATE_FORMAT );
        Defaults.ignoreJsonNulls();

        return new Configuration( loginId );
    }

    public String getLoginId()
    {
        return loginId;
    }
}
