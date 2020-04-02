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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TabType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
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
