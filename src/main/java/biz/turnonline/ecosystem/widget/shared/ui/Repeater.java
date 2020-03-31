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

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.table.Table;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableRow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class Repeater<T>
        extends Table
        implements TakesValue<List<T>>
{
    private List<T> value;

    private MaterialWidget tbody = new MaterialWidget( DOM.createTBody() );

    private MaterialWidget thead = new MaterialWidget( DOM.createTHead() );

    public Repeater()
    {
        setStyleName( "table" );

        addHead( thead );
        thead.add( new TableRow() );

        addBody( tbody );
    }

    @SuppressWarnings( "unchecked" )
    public List<T> getValue()
    {
        if ( !tbody.getChildrenList().isEmpty() && value == null )
        {
            value = new ArrayList<>();
        }

        if ( value != null )
        {
            value.clear();

            tbody.forEach( widget -> {
                TakesValue<T> row = ( TakesValue<T> ) widget;
                value.add( row.getValue() );
            } );
        }

        return value;
    }

    @Override
    public void setValue( List<T> value )
    {
        this.value = value;
        tbody.clear();

        if ( value != null )
        {
            value.forEach( this::addRow );
        }
    }

    @SuppressWarnings( "unchecked" )
    public Widget addRow( T data )
    {
        Widget rowData = newItem();
        ( ( TakesValue<T> ) rowData ).setValue( data );
        tbody.add( rowData );

        return rowData;
    }

    public TableHeader addHeader( String header, String width )
    {
        TableHeader th = new TableHeader();
        th.setHeader( header );
        th.setVerticalAlign( Style.VerticalAlign.MIDDLE );
        th.setWidth( width );
        ( ( TableRow ) thead.getChildren().get( 0 ) ).add( th );

        return th;
    }

    public TableHeader addHeader( Widget header, String width )
    {
        TableHeader th = new TableHeader();
        th.add( header );
        th.setVerticalAlign( Style.VerticalAlign.MIDDLE );
        th.setWidth( width );
        ( ( TableRow ) thead.getChildren().get( 0 ) ).add( th );

        return th;
    }

    @SuppressWarnings( "unchecked" )
    public void insertAfter( T data, int index )
    {
        Widget rowData = newItem();
        ( ( TakesValue<T> ) rowData ).setValue( data );
        tbody.insert( rowData, index + 1 );
    }

    public MaterialWidget getThead()
    {
        return thead;
    }

    public void select( Widget widgetToSelect )
    {
        tbody.forEach( widget -> {
            widget.getElement().getStyle().setProperty( "borderLeft", null );
            widget.getElement().getStyle().setFontWeight( Style.FontWeight.NORMAL );
        } );

        if ( widgetToSelect != null )
        {
            widgetToSelect.getElement().getStyle().setProperty( "borderLeft", "2px solid #2196f3" );
            widgetToSelect.getElement().getStyle().setFontWeight( Style.FontWeight.BOLD );
        }
    }

    public void select( int index )
    {
        select( getItem( index ) );
    }

    public int itemsSize()
    {
        return tbody.getChildren().size();
    }

    public Widget getItem( int index )
    {
        try
        {
            return tbody.getChildrenList().get( index );
        }
        catch ( IndexOutOfBoundsException e )
        {
            // ignore
        }

        return null;
    }

    public MaterialWidget getTbody()
    {
        return tbody;
    }

    protected abstract Widget newItem();
}
