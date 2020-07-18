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

import biz.turnonline.ecosystem.widget.purchase.event.ExpenseListEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Expenses;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoiceDetails;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
import biz.turnonline.ecosystem.widget.shared.event.TransactionListEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * Incoming invoice details presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceDetailsPresenter
        extends Presenter<IncomingInvoiceDetailsPresenter.IView>
{
    @Inject
    public IncomingInvoiceDetailsPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( IncomingInvoiceDetails.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( ExpenseListEvent.TYPE, this::goToList );
        bus().addHandler( RecalculatedPricingEvent.TYPE, this::recalculated );
        bus().addHandler( TransactionListEvent.TYPE, this::invoiceTransactions );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( new IncomingInvoice() );

        IncomingInvoiceDetails where = ( IncomingInvoiceDetails ) controller().getWhere();
        if ( where.getInvoiceId() != null )
        {
            bus().billing().getIncomingOrderInvoice( where.getOrderId(), where.getInvoiceId(), this::setModel );
        }

        onAfterBackingObject();
    }

    private void goToList( ExpenseListEvent event )
    {
        controller().goTo( new Expenses( event.getScrollspy() ) );
    }

    private void recalculated( RecalculatedPricingEvent event )
    {
        view().update( event.getPricing() );
    }

    private void invoiceTransactions( TransactionListEvent event )
    {
        bus().billing().getTransactions( event.getOrderId(), event.getInvoiceId(),
                response -> view().fill( response.getItems() ) );
    }

    private void setModel( IncomingInvoice response )
    {
        IncomingInvoiceDetails where = ( IncomingInvoiceDetails ) controller().getWhere();
        view().setModel( response );
        Scheduler.get().scheduleDeferred( () -> view().selectTab( where.getTab() ) );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<IncomingInvoice>
    {
        /**
         * Selects the specified tab to be visible to the user.
         *
         * @param tab the name of the tab to be selected
         */
        void selectTab( String tab );

        /**
         * Updates the order's pricing (details and items) UI by recalculated price.
         *
         * @param pricing the recalculated price
         */
        void update( @Nonnull Pricing pricing );

        /**
         * Fills the transactions that are associated with this invoice.
         *
         * @param transactions the list of transaction
         */
        void fill( @Nullable List<Transaction> transactions );
    }
}