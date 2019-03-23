package org.ctoolkit.turnonline.widget.shared.ui;

import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.pager.actions.RowSelection;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SmartTable<T>
        extends MaterialDataTable<T>
{
    public void configure( DataSource<T> dataSource )
    {
        MaterialIcon btnRefresh = new MaterialIcon( IconType.REFRESH );
        btnRefresh.addStyleName( "waves-effect waves-light circle" );

        setDataSource( dataSource );
        getTableTitle().setText( "" );
        getScaffolding().getTopPanel().addStyleName( "top-panel grey lighten-5 grey-text text-darken-3" );
        getScaffolding().getTableBody().getElement().getStyle().setHeight( 100, Style.Unit.PCT );
        getScaffolding().getToolPanel().add( btnRefresh );
        ( ( AbstractDataTable.DefaultTableScaffolding ) getScaffolding() ).getXScrollPanel().removeFromParent();

        MaterialDataPager<T> pager = new MaterialDataPager<>( this, dataSource );
        pager.setRowSelection( new RowSelection( new MaterialDataPager<>( this, dataSource ) ) );
        add( pager );
    }
}
