package org.ctoolkit.turnonline.widget.product.ui;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TabType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditProductTabs
        extends MaterialTab
{
    public EditProductTabs()
    {
        setBackgroundColor( Color.GREY_LIGHTEN_3 );
        setIndicatorColor( Color.BLACK );
        setType( TabType.ICON );

        // TODO: localize
        add( newTabItem( "Detail", "tabDetail", IconType.VISIBILITY ) );
        add( newTabItem( "Content", "tabContent", IconType.CODE ) );
        add( newTabItem( "Publishing", "tabPublishing", IconType.PUBLIC ) );
        add( newTabItem( "Pricing", "tabPricing", IconType.ATTACH_MONEY ) );
        add( newTabItem( "Invoicing", "tabInvoicing", IconType.ASSIGNMENT ) );
        add( newTabItem( "Event", "tabEvent", IconType.EVENT ) );
    }

    protected MaterialTabItem newTabItem( String text, String tab, IconType icon )
    {
        MaterialTabItem item = new MaterialTabItem();
        item.setWaves( WavesType.DEFAULT );

        MaterialLink link = new MaterialLink( text, tab, icon );
        link.setTextColor( Color.BLACK );
        item.add( link );

        link.addClickHandler( event -> {
            String current = currentState();
            int start = current.indexOf( "tab" );
            String newState = current.substring( 0, start ) + tab;

            replaceState( newState );
        } );

        return item;
    }

    private native void replaceState( String state ) /*-{
        $wnd.history.replaceState( {}, "", state );
    }-*/;

    private native String currentState() /*-{
        return decodeURIComponent( $wnd.location.href );
    }-*/;

}
