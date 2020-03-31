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

package biz.turnonline.ecosystem.widget.shared;

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
        AppEventBus.set( component.getEventBus() );

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
