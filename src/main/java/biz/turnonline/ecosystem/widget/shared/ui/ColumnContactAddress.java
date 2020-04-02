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
import biz.turnonline.ecosystem.widget.shared.rest.account.Country;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import static biz.turnonline.ecosystem.widget.shared.util.Formatter.formatPostcode;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnContactAddress<T extends Contact>
        extends WidgetColumn<T, FlowPanel>
{
    @Override
    public FlowPanel getValue( T contact )
    {
        FlowPanel cell = new FlowPanel();
        cell.getElement().getStyle().setPaddingTop( 10, Style.Unit.PX );
        cell.getElement().getStyle().setPaddingBottom( 10, Style.Unit.PX );

        if ( contact.getStreet() != null )
        {
            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );

            InlineLabel street = new InlineLabel( contact.getStreet() );
            street.getElement().getStyle().setFontWeight( Style.FontWeight.BOLD );
            cellItem.add( street );
        }

        if ( contact.getCity() != null )
        {
            StringBuilder sb = new StringBuilder();
            if ( contact.getPostcode() != null )
            {
                sb.append( formatPostcode( contact.getPostcode() ) );
                sb.append( " " );
            }

            sb.append( contact.getCity() );

            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );
            cellItem.add( new InlineLabel( sb.toString() ) );
        }

        if ( contact.getCountry() != null )
        {
            CodeBookReadOnlyBox<Country> country = new CodeBookReadOnlyBox<>( contact.getCountry(), Country.class );
            country.getElement().addClassName( "grey-text" );

            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );
            cellItem.add( country );
        }

        return cell;
    }
}
