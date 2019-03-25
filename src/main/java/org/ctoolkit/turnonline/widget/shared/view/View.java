/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.ctoolkit.turnonline.widget.shared.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialRow;
import org.ctoolkit.gwt.client.view.BinderyView;
import org.ctoolkit.turnonline.widget.shared.AppMessages;
import org.ctoolkit.turnonline.widget.shared.ui.ScaffoldHeader;
import org.ctoolkit.turnonline.widget.shared.ui.ScaffoldNavBar;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class View<T>
        extends BinderyView
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

    private T model;

    public View( EventBus eventBus )
    {
        super( eventBus );

        // body content
        root = new FlowPanel();
        initWidget( root );

        // header
        scaffoldHeader = new ScaffoldHeader();
        root.add( scaffoldHeader );

        // side bar
        scaffoldNavBar = new ScaffoldNavBar();
        root.add( scaffoldNavBar );

        // content
        MaterialContainer contentContainer = new MaterialContainer();
        root.add( contentContainer );

        content = new MaterialRow(  );
        content.setPaddingTop( 65 );
        contentContainer.add( content );
    }

    @Override
    public T getModel()
    {
        bind();
        return model;
    }

    protected T getRawModel()
    {
        return model;
    }

    @Override
    public void setModel( T model )
    {
        this.model = model;
        fill();
    }

    protected void bind()
    {
    }

    protected void fill()
    {
    }

    public static native JavaScriptObject newWindow( String url, String name, String features )/*-{
        var window = $wnd.open( url, name, features );
        return window;
    }-*/;

    public static native void setWindowTarget( JavaScriptObject window, String target )/*-{
        window.location = target;
    }-*/;

    protected final EventBus bus()
    {
        return super.bus();
    }

    public void add( IsWidget w )
    {
        content.add( w );
    }
}