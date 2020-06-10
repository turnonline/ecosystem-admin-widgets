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

package biz.turnonline.ecosystem.widget.shared.presenter;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import com.google.gwt.core.client.GWT;
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
public abstract class Presenter<V extends IView>
        extends BinderyPresenter<V>
{
    protected AppMessages messages = AppMessages.INSTANCE;

    public Presenter( V view, PlaceController placeController )
    {
        super( AppEventBus.get(), view, placeController );

        setTitle( "TurnOnline.biz Administration" );
    }

    /**
     * <p>Show info message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public static void info( String msg )
    {
        MaterialToast.fireToast( msg, "cyan" );
    }

    /**
     * <p>Show success message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public static void success( String msg )
    {
        MaterialToast.fireToast( msg, "green" );
    }

    /**
     * <p>Show warning message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public static void warn( String msg )
    {
        MaterialToast.fireToast( msg, "amber" );
    }

    /**
     * <p>Show warning message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public static void warn( String msg, FacadeCallback.Failure failure )
    {
        if ( failure.isFailure() )
        {
            if ( failure.isNotFound() )
            {
                error( AppMessages.INSTANCE.msgErrorRecordDoesNotExists() );
            }
            else if ( failure.isBadRequest() )
            {
                error( AppMessages.INSTANCE.msgErrorBadRequest( failure.response().getText() ) );
            }
            else
            {
                error( AppMessages.INSTANCE.msgErrorRemoteServiceCall() );
                GWT.log( "Exception has occurred while calling remote service: " + failure.response().getText() );
            }
        }
        else
        {
            warn( msg );
        }
    }

    /**
     * <p>Show info message.</p>
     *
     * @param msg message to show in feedback panel
     */
    public static void error( String msg )
    {
        MaterialToast.fireToast( msg, "red" );
    }

    /**
     * <p>Show conditional message.</p>
     *
     * @param success message to show in feedback panel
     */
    public static void success( String success, FacadeCallback.Failure failure )
    {
        if ( failure.isFailure() )
        {
            if ( failure.isNotFound() )
            {
                error( AppMessages.INSTANCE.msgErrorRecordDoesNotExists() );
            }
            else if ( failure.isBadRequest() )
            {
                error( AppMessages.INSTANCE.msgErrorBadRequest( failure.response().getText() ) );
            }
            else
            {
                error( AppMessages.INSTANCE.msgErrorRemoteServiceCall() );
                GWT.log( "Exception has occurred while calling remote service: " + failure.response().getText() );
            }
        }
        else
        {
            success( success );
        }
    }

    @Override
    protected final AppEventBus bus()
    {
        return ( AppEventBus ) super.bus();
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
}
