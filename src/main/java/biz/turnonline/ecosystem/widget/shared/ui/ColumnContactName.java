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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnContactName<T extends Contact>
        extends NotSafeHtmlColumn<T>
{
    @Override
    public String getValue( T object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getBusinessName() != null && !"".equals( object.getBusinessName() ) )
        {
            sb.append( "<b>" );
            sb.append( object.getBusinessName() );
            sb.append( "</b>" );

            sb.append( " " );
            sb.append( "<span class='white-text badge green' style='position:relative;right:0;top:-1px;font-size:0.9em;border-radius:3px;'>" );
            sb.append( object.getCompanyId() );
            sb.append( "</span>" );

            sb.append( "<br/>" );
        }

        if ( object.getFirstName() != null || object.getLastName() != null )
        {
            if ( object.getPrefix() != null )
            {
                sb.append( object.getPrefix() );
                sb.append( " " );
            }
            sb.append( object.getFirstName() );
            sb.append( " " );
            sb.append( object.getLastName() );

            if ( object.getSuffix() != null )
            {
                sb.append( " " );
                sb.append( object.getSuffix() );
            }
        }

        return sb.toString();
    }
}
