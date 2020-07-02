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

import biz.turnonline.ecosystem.widget.shared.rest.bill.BillItem;
import biz.turnonline.ecosystem.widget.shared.ui.BillingUnitComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableRow;


/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillItemRow
        extends TableRow
        implements TakesValue<BillItem>
{
    private BillItem model;

    private final MaterialTextBox itemName = new MaterialTextBox();

    private final MaterialDoubleBox amount = new MaterialDoubleBox();

    private final BillingUnitComboBox unit = new BillingUnitComboBox();

    private final MaterialDoubleBox priceExclVat = new MaterialDoubleBox();

    private final VatRateComboBox vat = new VatRateComboBox();

    private final MaterialDoubleBox priceInclVat = new MaterialDoubleBox();

    private final MaterialButton remove = new MaterialButton();

    private final boolean readOnly;

    public BillItemRow( boolean readOnly )
    {
        this.readOnly = readOnly;

        remove.setIconType( IconType.DELETE );
        remove.setBackgroundColor( Color.RED );
        remove.setIconColor( Color.WHITE );
        remove.setType( ButtonType.FLOATING );
        remove.setWaves( WavesType.DEFAULT );
        remove.addClickHandler( event -> removeFromParent() );

        TableData columnName = column( itemName );
        columnName.setPaddingLeft( 0 );
        columnName.setWidth( "30%" );

        TableData columnAmount = column( amount );
        columnAmount.setPaddingLeft( 0 );
        columnAmount.setWidth( "10%" );

        TableData columnUnit = column( unit );
        columnUnit.setPaddingLeft( 0 );
        columnUnit.setWidth( "15%" );

        TableData columnVat = column( vat );
        columnVat.setPaddingLeft( 0 );
        columnVat.setWidth( "10%" );

        TableData columnPriceExclVat = column( priceExclVat );
        columnPriceExclVat.setPaddingLeft( 0 );
        columnPriceExclVat.setWidth( "15%" );

        TableData columnPriceInclVat = column( priceInclVat );
        columnPriceInclVat.setPaddingLeft( 0 );
        columnPriceInclVat.setWidth( "15%" );

        TableData columnRemove = column( remove );
        columnRemove.setPaddingRight( 0 );
        columnRemove.setWidth( "5%" );
    }

    @Override
    public BillItem getValue()
    {
        model.setItemName( itemName.getValue() );
        model.setAmount( amount.getValue() );
        model.setUnit( unit.getSingleValueByCode() );
        model.setPriceExclVat( priceExclVat.getValue() );
        model.setFinalVat( vat.getSingleValueByCode() );
        model.setFinalPrice( priceInclVat.getValue() );

        return model;
    }

    @Override
    public void setValue( BillItem item )
    {
        this.model = item;

        itemName.setValue( item.getItemName() );
        amount.setValue( item.getAmount() );
        unit.setSingleValueByCode( item.getUnit() );
        priceExclVat.setValue( item.getPriceExclVat() );
        vat.setSingleValueByCode( item.getFinalVat() );
        priceInclVat.setValue( item.getFinalPrice() );

        itemName.setReadOnly( readOnly );
        amount.setReadOnly( readOnly );
        unit.setReadOnly( readOnly );
        priceExclVat.setReadOnly( readOnly );
        vat.setReadOnly( readOnly );
        priceInclVat.setReadOnly( readOnly );
        remove.setVisible( !readOnly );
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
