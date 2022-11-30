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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.StatisticsEvent;
import biz.turnonline.ecosystem.widget.purchase.event.TransactionDetailEvent;
import biz.turnonline.ecosystem.widget.purchase.event.TransactionDetailEvent.TransactionSource;
import biz.turnonline.ecosystem.widget.purchase.place.Dashboard;
import biz.turnonline.ecosystem.widget.purchase.place.TransactionDetail;
import biz.turnonline.ecosystem.widget.purchase.place.Transactions;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class TransactionsPresenter
        extends Presenter<TransactionsPresenter.IView>
{
    private final Map<TransactionSource, TransactionIdResolver> transactionIdResolverMap = new HashMap<>();

    @Inject
    public TransactionsPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( Transactions.class );

        transactionIdResolverMap.put( TransactionSource.PAYMENT, this::goToDetail );
        transactionIdResolverMap.put( TransactionSource.BILLING, id -> bus().billing().getTransactionById( id, response -> goToDetail( response.getTransactionId() ) ) );
    }

    @Override
    public void bind()
    {
        bus().addHandler( TransactionDetailEvent.TYPE, event -> {
            Long id = event.getId();
            TransactionSource source = event.getSource();

            transactionIdResolverMap.get( source ).resolve( id );
        } );

        bus().addHandler( StatisticsEvent.TYPE, event -> controller().goTo( new Dashboard() ) );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<List<Transaction>>
    {
        void refresh();
    }

    @FunctionalInterface
    private interface TransactionIdResolver
    {
        void resolve( Long id );
    }

    private void goToDetail( Long id )
    {
        controller().goTo( new TransactionDetail( id ) );
    }
}