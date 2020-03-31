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

package biz.turnonline.ecosystem.widget.shared.view;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldHeader;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldNavBar;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialRow;
import org.ctoolkit.gwt.client.view.BinderyView;
import org.ctoolkit.gwt.client.view.IView;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public abstract class View<T>
        extends BinderyView<T>
        implements IView<T>
{
    protected AppMessages messages = AppMessages.INSTANCE;

    protected ScaffoldHeader scaffoldHeader;

    protected ScaffoldNavBar scaffoldNavBar;

    protected MaterialRow content;

    /**
     * This panel represents root of all widgets on page. Add every child widget on page to this panel.
     */
    protected FlowPanel root;

    public View()
    {
        super( AppEventBus.get() );

        // body content
        root = new FlowPanel();
        initWidget( root );

        // header
        scaffoldHeader = new ScaffoldHeader( bus() );
        root.add( scaffoldHeader );

        // side bar
        scaffoldNavBar = new ScaffoldNavBar();
        root.add( scaffoldNavBar );

        // content
        MaterialContainer contentContainer = new MaterialContainer();
        root.add( contentContainer );

        content = new MaterialRow();
        content.getElement().setAttribute( "style", "padding-top:65px" );
        ViewPort.when( Resolution.ALL_MOBILE ).then( e -> content.getElement().setAttribute( "style", "padding-top:55px" ) );

        contentContainer.add( content );
    }

    public static native JavaScriptObject newWindow( String url, String name, String features )/*-{
        return $wnd.open( url, name, features );
    }-*/;

    public static native void setWindowTarget( JavaScriptObject window, String target )/*-{
        window.location = target;
    }-*/;

    protected void setActive( Route route )
    {
        scaffoldNavBar.setActive( route );
        scaffoldHeader.setActive( route );
    }

    protected final EventBus bus()
    {
        return super.bus();
    }

    public void add( IsWidget w )
    {
        content.add( w );
    }
}