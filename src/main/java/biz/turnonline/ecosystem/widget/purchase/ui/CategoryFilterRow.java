/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.CategoryFilter;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableRow;


/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CategoryFilterRow
        extends TableRow
        implements TakesValue<CategoryFilter>
{
    private final CategoryFilterPropertyNameComboBox propertyName = new CategoryFilterPropertyNameComboBox();

    private final CategoryFilterOperationComboBox operation = new CategoryFilterOperationComboBox();

    private final MaterialTextBox propertyValue = new MaterialTextBox();

    private final MaterialButton remove = new MaterialButton();

    private CategoryFilter model;

    public CategoryFilterRow(  )
    {
        propertyName.setPaddingRight( 20 );

        remove.setIconType( IconType.DELETE );
        remove.setBackgroundColor( Color.RED );
        remove.setIconColor( Color.WHITE );
        remove.setType( ButtonType.FLOATING );
        remove.setWaves( WavesType.DEFAULT );
        remove.addClickHandler( event -> removeFromParent() );

        TableData columnName = column( propertyName );
        columnName.setPaddingLeft( 0 );
        columnName.setWidth( "25%" );

        TableData columnOperation = column( operation );
        columnOperation.setPaddingLeft( 0 );
        columnOperation.setWidth( "10%" );

        TableData columnPropertyValue = column( propertyValue );
        columnPropertyValue.setPaddingLeft( 0 );
        columnPropertyValue.setWidth( "60%" );

        TableData columnRemove = column( remove );
        columnRemove.setPaddingRight( 0 );
        columnRemove.setWidth( "5%" );
    }

    @Override
    public CategoryFilter getValue()
    {
        model.setPropertyName( propertyName.getSingleValueByCode() );
        model.setOperation( operation.getSingleValueByCode() );
        model.setPropertyValue( propertyValue.getValue() );

        return model;
    }

    @Override
    public void setValue( CategoryFilter item )
    {
        this.model = item;

        propertyName.setSingleValueByCode( item.getPropertyName() );
        operation.setSingleValueByCode( item.getOperation() );
        propertyValue.setValue( item.getPropertyValue() );
    }

    // -- private helpers

    private TableData column( Widget widget )
    {
        TableData cell = new TableData();
        cell.add( widget );
        add( cell );

        return cell;
    }
}
