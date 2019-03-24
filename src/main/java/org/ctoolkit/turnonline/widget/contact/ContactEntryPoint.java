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

package org.ctoolkit.turnonline.widget.contact;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import org.ctoolkit.turnonline.widget.contact.place.Contacts;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class ContactEntryPoint
        implements EntryPoint
{
    public static Place DEFAULT_PLACE = new Contacts();

    private SimplePanel container = new SimplePanel();

    @Override
    public void onModuleLoad()
    {
        // create dagger context
        ContactComponent component = DaggerContactComponent.create();

        // remove splashcreen
        DOM.getElementById( "splashscreen" ).removeFromParent();

        // create activity manager and put container in it
        RootPanel.get( "main-content" ).add( container );
        ActivityManager activityManager = component.getActivityManager();
        activityManager.setDisplay( container );

        // handle current history
        component.placeHistoryHandler().handleCurrentHistory();
    }
}
