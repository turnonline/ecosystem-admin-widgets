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

package biz.turnonline.ecosystem.widget.shared.util;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.text.client.DateTimeFormatRenderer;

import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
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
