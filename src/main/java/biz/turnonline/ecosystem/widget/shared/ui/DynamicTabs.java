package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
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
public class DynamicTabs
        extends MaterialTab
{
    protected static final AppMessages messages = AppMessages.INSTANCE;

    public DynamicTabs()
    {
        setBackgroundColor( Color.GREY_LIGHTEN_3 );
        setIndicatorColor( Color.BLACK );
        setType( TabType.ICON );
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
