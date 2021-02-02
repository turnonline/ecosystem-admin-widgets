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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import biz.turnonline.ecosystem.widget.shared.rest.payment.CategoryFilter;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Operation;
import biz.turnonline.ecosystem.widget.shared.rest.payment.PropertyName;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.InlineLabel;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnCategoryFilters
        extends WidgetColumn<Category, MaterialContainer>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static final Map<String, String> nameMap = new HashMap<>();
    private static final Map<String, String> operationsMap = new HashMap<>();

    static
    {
        nameMap.put( PropertyName.NAME.name(), messages.labelPropertyNameName() );
        nameMap.put( PropertyName.AMOUNT.name(), messages.labelPropertyNameAmount() );
        nameMap.put( PropertyName.CURRENCY.name(), messages.labelPropertyNameCurrency() );
        nameMap.put( PropertyName.CREDIT.name(), messages.labelPropertyNameCredit() );
        nameMap.put( PropertyName.COUNTERPARTY_IBAN.name(), messages.labelPropertyNameCounterpartyIban() );
        nameMap.put( PropertyName.REFERENCE.name(), messages.labelPropertyNameCounterpartyIban() );

        operationsMap.put( Operation.EQ.name(), "=" );
        operationsMap.put( Operation.LT.name(), "<" );
        operationsMap.put( Operation.LTE.name(), "<=" );
        operationsMap.put( Operation.GT.name(), ">" );
        operationsMap.put( Operation.GTE.name(), ">=" );
        operationsMap.put( Operation.REGEXP.name(), "=~" );
    }

    @Override
    public MaterialContainer getValue( Category object )
    {
        MaterialContainer cell = new MaterialContainer();

        if ( object.getFilters() != null && !object.getFilters().isEmpty() )
        {
            for ( int i = 0; i < object.getFilters().size(); i++ )
            {
                CategoryFilter filter = object.getFilters().get( i );

                MaterialContainer row = new MaterialContainer();
                row.setMarginLeft( 10 );
                row.setDisplay( Display.INLINE_BLOCK );
                cell.add( row );

                if ( i > 0 )
                {
                    InlineLabel andLabel = new InlineLabel("AND");
                    andLabel.getElement().getStyle().setBorderColor( "#ff9800" );
                    andLabel.getElement().getStyle().setBorderWidth( 1, Style.Unit.PX );
                    andLabel.getElement().getStyle().setBorderStyle( Style.BorderStyle.SOLID);
                    andLabel.getElement().getStyle().setColor( "#ff9800" );
                    andLabel.getElement().getStyle().setProperty( "borderRadius", "5px" );
                    andLabel.getElement().getStyle().setPadding( 5, Style.Unit.PX );
                    andLabel.getElement().getStyle().setMarginRight( 10, Style.Unit.PX );
                    row.add(andLabel);
                }

                InlineLabel name = new InlineLabel( nameMap.get( filter.getPropertyName() ) );
                name.getElement().getStyle().setPaddingRight( 5, Style.Unit.PX );
                row.add( name );

                InlineLabel operation = new InlineLabel( operationsMap.get( filter.getOperation() ) );
                operation.getElement().getStyle().setBackgroundColor( "#42a5f5" );
                operation.getElement().getStyle().setColor( "#fff" );
                operation.getElement().getStyle().setProperty( "borderRadius", "5px" );
                operation.getElement().getStyle().setPadding( 5, Style.Unit.PX );
                operation.getElement().getStyle().setMarginRight( 5, Style.Unit.PX );
                row.add( operation );

                InlineLabel value = new InlineLabel( filter.getPropertyValue() );
                value.getElement().getStyle().setFontWeight( Style.FontWeight.BOLD );
                row.add( value );
            }
        }

        return cell;
    }
}
