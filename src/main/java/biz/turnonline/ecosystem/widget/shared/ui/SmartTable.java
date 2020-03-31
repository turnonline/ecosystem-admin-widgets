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
import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SmartTable<T>
        extends MaterialDataTable<T>
{
    public SmartTable()
    {
        setSelectionType( SelectionType.NONE );

        getTableTitle().setText( "" );
        getScaffolding().getTableBody().getElement().getStyle().setHeight( 100, Style.Unit.PCT );
        ( ( AbstractDataTable.DefaultTableScaffolding ) getScaffolding() ).getXScrollPanel().removeFromParent();
    }

    public void configure( DataSource<T> dataSource )
    {
        setDataSource( dataSource );

        MaterialDataPager<T> pager = new MaterialDataPager<T>( this, dataSource )
        {
            @Override
            protected void updateUi()
            {
                super.updateUi();

                int firstRow = offset + 1;
                int lastRow = ( isExcess() & isLastPage() ) ? totalRows : ( offset + limit );
                getActionsPanel().getActionLabel().setText( firstRow + "-" + lastRow );
            }
        };
        add( pager );
    }

    public void refresh()
    {
        if ( getDataSource() instanceof RefreshableDataSource )
        {
            ( ( RefreshableDataSource ) getDataSource() ).refresh();
        }
    }

    @Override
    protected void build()
    {
        super.build();

        getScaffolding().getTopPanel().setVisible( false );
    }
}
