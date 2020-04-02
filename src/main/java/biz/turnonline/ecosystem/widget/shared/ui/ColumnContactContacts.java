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
import gwt.material.design.client.constants.IconType;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnContactContacts<T extends Contact>
        extends NotSafeHtmlColumn<T>
{
    @Override
    public String getValue( T object )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "<div style='padding: 10px 0;'>" );
        if ( object.getContactEmail() != null && !"".equals( object.getContactEmail() ) )
        {
            constructContact( object.getContactEmail(), IconType.EMAIL, sb );
            sb.append( "<br/>" );
        }

        if ( object.getContactPhone() != null && !"".equals( object.getContactPhone() ) )
        {
            constructContact( object.getContactPhone(), IconType.PHONE, sb );
        }
        sb.append( "</div>" );
        return sb.toString();
    }

    private void constructContact( String contact, IconType icon, StringBuilder sb )
    {
        sb.append( "<i class='orange-text material-icons' style='position:relative;top: 2px;'>" ).append( icon.getCssName() ).append( "</i>" );
        sb.append( "<span style='position:relative;top: -6px;padding-left: 5px;'>" );
        sb.append( contact );
        sb.append( "</span>" );
    }
}
