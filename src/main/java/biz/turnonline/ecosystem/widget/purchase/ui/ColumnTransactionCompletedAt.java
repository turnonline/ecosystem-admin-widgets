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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.shared.DateTimeFormat;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionCompletedAt
        extends WidgetColumn<Transaction, MaterialColumn>
{
    @Override
    public MaterialColumn getValue( Transaction object )
    {
        MaterialColumn content = new MaterialColumn();
        Date completedAt = object.getCompletedAt();

        if ( completedAt != null )
        {
            MaterialIcon icon = new MaterialIcon( IconType.DATE_RANGE );
            icon.setIconColor( Color.GREY );
            content.add( icon );

            DateTimeFormat format = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM );
            MaterialLabel label = new MaterialLabel( format.format( completedAt ) );
            label.getElement().getStyle().setDisplay( Style.Display.INLINE_BLOCK );
            label.getElement().getStyle().setPosition( Style.Position.RELATIVE );
            label.getElement().getStyle().setTop( -6, Style.Unit.PX );
            content.add( label );
        }

        return content;
    }
}
