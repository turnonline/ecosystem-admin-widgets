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
        getScaffolding().getTopPanel().addStyleName( "top-panel grey lighten-5 grey-text text-darken-3" );
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
        getScaffolding().getToolPanel().clear();
        getScaffolding().getInfoPanel().clear();
    }
}
