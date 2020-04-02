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

package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductDiscount;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.client.ui.table.Table;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableRow;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Discounts
        extends Composite
        implements TakesValue<List<ProductDiscount>>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private MaterialWidget tbody = new MaterialWidget( DOM.createTBody() );

    private List<ProductDiscount> values = new ArrayList<>();

    public Discounts()
    {
        FlowPanel root = new FlowPanel();
        initWidget( root );

        FlowPanel actions = new FlowPanel();
        root.add( actions );
        Table itemsRoot = new Table();
        root.add( itemsRoot );

        // -- actions

        actions.getElement().setAttribute( "style", "margin: 10px 10px;padding-bottom: 20px;" );

        MaterialButton btnAdd = new MaterialButton( messages.labelAdd(), IconType.ADD_CIRCLE, ButtonType.OUTLINED );
        btnAdd.setIconColor( Color.GREEN );
        btnAdd.setTextColor( Color.GREEN );
        btnAdd.setWaves( WavesType.GREEN );
        btnAdd.setMarginRight( 10 );
        btnAdd.addClickHandler( event -> newRow() );
        actions.add( btnAdd );

        // -- table
        itemsRoot.addStyleName( "table" );

        // header

        MaterialWidget thead = new MaterialWidget( DOM.createTHead() );
        itemsRoot.addHead( thead );

        TableRow thRow = new TableRow();
        thRow.add( header( messages.labelActive(), "5%" ) );
        thRow.add( header( messages.labelValue(), "15%" ) );
        thRow.add( header( messages.labelDiscountType(), "15%" ) );
        thRow.add( header( messages.labelDiscountRule(), "20%" ) );
        thRow.add( header( messages.labelCodes(), "40%" ) );
        thRow.add( header( "", "5%" ) );
        thead.add( thRow );

        // body
        itemsRoot.addBody( tbody );
    }

    @Override
    public List<ProductDiscount> getValue()
    {
        List<ProductDiscount> discounts = new ArrayList<>();

        for ( int i = 0; i < values.size(); i++ )
        {
            DiscountItem item = ( DiscountItem ) tbody.getWidget( i );
            discounts.add( item.getValue() );
        }

        return discounts;
    }

    @Override
    public void setValue( @Nullable List<ProductDiscount> value )
    {
        values.clear();
        tbody.clear();

        if ( value != null && !value.isEmpty() )
        {
            value.forEach( this::createRow );
        }
    }

    private void newRow()
    {
        ProductDiscount discount = new ProductDiscount();
        discount.setEnabled( true );
        createRow( discount );
    }

    private TableData header( String label, String width )
    {
        TableHeader header = new TableHeader();
        header.add( new Label( label ) );
        header.setWidth( width );
        return header;
    }

    private void createRow( ProductDiscount discount )
    {
        DiscountItem item = new DiscountItem();
        item.setValue( discount );

        item.getBtnDelete().addClickHandler( event -> {
            values.remove( discount );
            item.removeFromParent();
        } );

        tbody.add( item );
        values.add( discount );
    }
}
