package biz.turnonline.ecosystem.widget.shared.util;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.text.client.DateTimeFormatRenderer;

import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Time
{
    public static Integer dateToInteger( Date date )
    {
        if ( date != null )
        {
            DateTimeFormatRenderer renderer = new DateTimeFormatRenderer( DateTimeFormat.getFormat( "HH:mm" ) );
            String time = renderer.render( date );

            String[] units = time.split( ":" );
            int hours = Integer.parseInt( units[0] );
            int minutes = Integer.parseInt( units[1] );
            return 3600 * hours + 60 * minutes;
        }

        return null;
    }

    public static Date integerToDate( Integer time )
    {
        if ( time != null )
        {
            int hours = time / 3600;
            int minutes = ( time % 3600 ) / 60;

            return DateTimeFormat.getFormat( "HH:mm" ).parse( hours + ":" + minutes );
        }

        return null;
    }
}
