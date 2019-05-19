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

package biz.turnonline.ecosystem.widget.shared;

import biz.turnonline.ecosystem.widget.shared.util.StaticEventBus;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Generic widget entry point. Runs splash screen and adds widget source
 * in to {@code widget-content} DIV element.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public abstract class DaggerEntryPoint
        implements EntryPoint
{
    private SimplePanel container = new SimplePanel();

    @Override
    public void onModuleLoad()
    {
        // createProduct dagger context
        DaggerComponent component = component();

        // initialize static event bus
        StaticEventBus.INSTANCE = component.getEventBus();

        // remove splashcreen
        DOM.getElementById( "splashscreen" ).removeFromParent();

        // createProduct activity manager and put container in it
        RootPanel.get( "widget-content" ).add( container );
        ActivityManager activityManager = component.getActivityManager();
        activityManager.setDisplay( container );

        // handle current history
        component.placeHistoryHandler().handleCurrentHistory();
    }

    protected abstract DaggerComponent component();
}
