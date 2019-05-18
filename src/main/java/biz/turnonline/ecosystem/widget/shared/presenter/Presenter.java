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

package biz.turnonline.ecosystem.widget.shared.presenter;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialToast;
import org.ctoolkit.gwt.client.presenter.BinderyPresenter;
import org.ctoolkit.gwt.client.view.IView;

/**
 * Specific presenter injecting customized {@link EventBus}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public abstract class Presenter<V extends IView, E extends EventBus>
        extends BinderyPresenter<V>
{
    protected AppMessages messages = AppMessages.INSTANCE;

    public Presenter( E eventBus, V view, PlaceController placeController )
    {
        super( eventBus, view, placeController );

        setTitle( "TurnOnline.biz Administration" );
    }

    @Override
    protected final E bus()
    {
        return ( E ) super.bus();
    }

    /**
     * <p>Set title of page. Call this method in {@link org.ctoolkit.gwt.client.presenter.BinderyPresenter#onBackingObject()}
     * method to ensure that title will be rendered on every page correctly.</p>
     *
     * @param titleText text which will be shown in the browser window title
     */
    public void setTitle( String titleText )
    {
        Window.setTitle( titleText );
    }

    /**
     * <p>Show info message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public void info( String msg )
    {
        MaterialToast.fireToast( msg, "cyan" );
    }

    /**
     * <p>Show success message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public void success( String msg )
    {
        MaterialToast.fireToast( msg, "green" );
    }

    /**
     * <p>Show info message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public void warn( String msg )
    {
        MaterialToast.fireToast( msg, "amber" );
    }

    /**
     * <p>Show info message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public void error( String msg )
    {
        MaterialToast.fireToast( msg, "red" );
    }
}
