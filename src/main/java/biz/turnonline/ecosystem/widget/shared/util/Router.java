package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.ui.Route;
import com.google.gwt.user.client.Window;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Router
{
    public static void routeToList( Route route )
    {
        routeTo( route, route.getListToken(), new String[]{} );
    }

    public static void routeToDetail( Route route, String[] tokens )
    {
        routeTo( route, route.getDetailToken(), tokens );
    }

    public static void routeTo( Route route, String routePrefix, String[] tokens )
    {
        StringBuilder url = new StringBuilder();
        url.append( route.url() );
        url.append( "#" ).append( routePrefix ).append( ":" );

        int tokensApplied = 0;
        for ( String token : tokens )
        {
            if ( tokensApplied > 0 )
            {
                url.append( "|" );
            }
            tokensApplied++;

            url.append( token );
        }

        Window.Location.replace( url.toString() );
    }
}
