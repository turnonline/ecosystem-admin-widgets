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

package biz.turnonline.ecosystem.widget.myaccount.presenter;

import biz.turnonline.ecosystem.widget.myaccount.event.SaveAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.place.MyAccount;
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
        extends Presenter<MyAccountPresenter.IView>
{
    @Inject
    public MyAccountPresenter( IView view, PlaceController controller )
    {
        super( view, controller );
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