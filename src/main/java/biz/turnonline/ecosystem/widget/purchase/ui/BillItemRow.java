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

import biz.turnonline.ecosystem.widget.shared.rest.bill.Item;
import biz.turnonline.ecosystem.widget.shared.ui.PriceLabel;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.dom.client.Style;
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

import javax.annotation.Nullable;


/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillItemRow
        extends TableRow
        implements TakesValue<Item>
{
    private final MaterialDoubleBox vatAmount = new MaterialDoubleBox();

    private final MaterialTextBox itemName = new MaterialTextBox();

    private final MaterialDoubleBox priceExclVat = new MaterialDoubleBox();

    private final VatRateComboBox vat = new VatRateComboBox();

    private final PriceLabel priceInclVat = new PriceLabel();

    private final MaterialButton remove = new MaterialButton();

    private final boolean readOnly;

    private String currency;

    private Item model;

    public BillItemRow( boolean readOnly, @Nullable String currency )
    {
        this.readOnly = readOnly;
        this.currency = currency;

        priceInclVat.setFontWeight( Style.FontWeight.BOLD );
        vatAmount.setPaddingRight( 20 );

        remove.setIconType( IconType.DELETE );
        remove.setBackgroundColor( Color.RED );
        remove.setIconColor( Color.WHITE );
        remove.setType( ButtonType.FLOATING );
        remove.setWaves( WavesType.DEFAULT );
        remove.addClickHandler( event -> removeFromParent() );

        TableData columnName = column( itemName );
        columnName.setPaddingLeft( 0 );
        columnName.setWidth( "40%" );

        TableData columnVat = column( vat );
        columnVat.setPaddingLeft( 0 );
        columnVat.setWidth( "10%" );

        TableData columnPriceExclVat = column( priceExclVat );
        columnPriceExclVat.setPaddingLeft( 0 );
        columnPriceExclVat.setWidth( "17%" );

        TableData columnVatAmount = column( vatAmount );
        columnVatAmount.setPaddingLeft( 0 );
        columnVatAmount.setWidth( "17%" );

        TableData columnPriceInclVat = column( priceInclVat );
        columnPriceInclVat.setPaddingLeft( 0 );
        columnPriceInclVat.setWidth( "11%" );

        TableData columnRemove = column( remove );
        columnRemove.setPaddingRight( 0 );
        columnRemove.setWidth( "5%" );

        priceExclVat.addChangeHandler( event -> calcRowPrice() );
        vatAmount.addChangeHandler( event -> calcRowPrice() );
    }

    private void calcRowPrice()
    {
        Double pev = priceExclVat.getValue();
        Double vat = vatAmount.getValue();

        if ( pev != null && vat != null )
        {
            priceInclVat.setValue( pev + vat, currency );
        }
    }

    @Override
    public Item getValue()
    {
        model.setItemName( itemName.getValue() );
        model.setPriceExclVat( priceExclVat.getValue() );
        model.setVat( vat.getSingleValueByCode() );
        model.setFinalVatAmount( vatAmount.getValue() );
        model.setFinalPrice( priceInclVat.getPrice() );

        return model;
    }

    @Override
    public void setValue( Item item )
    {
        this.model = item;

        itemName.setValue( item.getItemName() );
        priceExclVat.setValue( item.getPriceExclVat() );
        vat.setSingleValueByCode( item.getVat() );
        vatAmount.setValue( item.getFinalVatAmount() );
        priceInclVat.setValue( item.getFinalPrice(), currency );

        setReadOnly( readOnly );
    }

    public void setCurrency( @Nullable String currency )
    {
        this.currency = currency;
        priceInclVat.setValue( priceInclVat.getPrice(), currency );
    }

    public void setReadOnly( boolean readOnly )
    {
        itemName.setReadOnly( readOnly );
        priceExclVat.setReadOnly( readOnly );
        vat.setReadOnly( readOnly );
        vatAmount.setReadOnly( readOnly );
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
