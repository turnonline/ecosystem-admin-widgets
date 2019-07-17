/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.myaccount.presenter;

import biz.turnonline.ecosystem.widget.myaccount.event.SaveAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.place.MyAccount;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * My account presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class MyAccountPresenter
        extends Presenter<MyAccountPresenter.IView, AppEventBus>
{
    @Inject
    public MyAccountPresenter( AppEventBus eventBus,
                               IView view,
                               PlaceController controller )
    {
        super( eventBus, view, controller );
        setPlace( MyAccount.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( SaveAccountEvent.TYPE,
                event -> bus()
                        .account()
                        .update( event.getLoginId(),
                                event.getAccount(),
                                ( response, failure ) -> success( messages.msgRecordUpdated(), failure ) ) );
    }

    @Override
    public void onBackingObject()
    {
        bus().account().getAccount( bus().config().getLoginId(), this::updateView );

        onAfterBackingObject();
    }

    private void updateView( Account account, FacadeCallback.Failure failure )
    {
        if ( failure.isFailure() )
        {
            if ( failure.isNotFound() )
            {
                error( messages.msgErrorRecordDoesNotExists() );
            }
            else
            {
                error( messages.msgErrorRemoteServiceCall() );
            }
        }
        else
        {
            view().setModel( account );
        }
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Account>
    {
    }
}