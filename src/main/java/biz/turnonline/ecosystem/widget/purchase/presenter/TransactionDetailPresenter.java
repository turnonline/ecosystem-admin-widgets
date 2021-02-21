/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.BackTransactionEvent;
import biz.turnonline.ecosystem.widget.purchase.place.TransactionDetail;
import biz.turnonline.ecosystem.widget.purchase.place.Transactions;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class TransactionDetailPresenter
        extends Presenter<TransactionDetailPresenter.IView>
{
    @Inject
    public TransactionDetailPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( TransactionDetail.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackTransactionEvent.TYPE, event -> controller().goTo( new Transactions() ) );
    }

    @Override
    public void onBackingObject()
    {
        TransactionDetail where = ( TransactionDetail ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().paymentProcessor().findTransactionById( where.getId(), response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Transaction>
    {
    }
}