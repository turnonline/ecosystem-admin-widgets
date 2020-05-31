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

import biz.turnonline.ecosystem.widget.purchase.event.ClearIncomingInvoicesFilterEvent;
import biz.turnonline.ecosystem.widget.purchase.event.DeleteIncomingInvoiceEvent;
import biz.turnonline.ecosystem.widget.purchase.event.IncomingInvoiceDetailsEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Expenses;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoiceDetails;
import biz.turnonline.ecosystem.widget.shared.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Expense;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.gwt.client.facade.Items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Incoming invoice list view presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ExpensesPresenter
        extends Presenter<ExpensesPresenter.IView>
{
    private ExpenseDataSource dataSource;

    @Inject
    public ExpensesPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( Expenses.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( IncomingInvoiceDetailsEvent.TYPE, event ->
                controller().goTo( new IncomingInvoiceDetails( event.getOrderId(), event.getInvoiceId(), "tabDetails" ) )
        );

        bus().addHandler( DownloadInvoiceEvent.TYPE, e -> view().downloadDocument( e.downloadInvoiceUrl() ) );
        bus().addHandler( ClearIncomingInvoicesFilterEvent.TYPE, this::clearFilter );
        bus().addHandler( DeleteIncomingInvoiceEvent.TYPE, this::deleteIncomingInvoice );

        view().setDataSource( dataSource = new ExpenseDataSource() );
    }

    private void clearFilter( ClearIncomingInvoicesFilterEvent event )
    {
        dataSource.filterBy( null );
        view().clear();
        controller().goTo( new Expenses() );
    }

    private void deleteIncomingInvoice( DeleteIncomingInvoiceEvent event )
    {
        bus().billing().deleteIncomingInvoice( event.getOrderId(), event.getInvoiceId(), ( response, failure ) -> {
            controller().goTo( new Expenses() );
            success( messages.msgRecordDeleted( event.getInvoiceNumber() ), failure );
        } );
    }

    @Override
    protected void onBeforeBackingObject()
    {
        Expenses where = ( Expenses ) controller().getWhere();
        Long orderId = where.getOrderId();
        if ( dataSource.filterBy( orderId ) )
        {
            view().clear();
        }
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();

        Expenses where = ( Expenses ) controller().getWhere();
        if ( where.getScrollspy() != null )
        {
            view().scrollTo( where.getScrollspy() );
        }

        view().setClearFilterEnabled( dataSource.isFilter() );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Expense>
    {
        /**
         * Downloads invoice PDF from the specified URL.
         *
         * @param url the full path to the invoice PDF
         */
        void downloadDocument( @Nonnull String url );

        void scrollTo( @Nullable String scrollspy );

        void clear();

        void setDataSource( InfiniteScroll.Callback<Expense> callback );

        void setClearFilterEnabled( boolean enabled );
    }

    private class ExpenseDataSource
            implements InfiniteScroll.Callback<Expense>
    {
        private Long orderId;

        @Override
        public void load( int offset, int limit, SuccessCallback<Items<Expense>> callback )
        {
            if ( orderId == null )
            {
                bus().billing().searchExpenses( offset, limit, true, callback );
            }
            else
            {
                bus().billing().searchExpensesByOrder( offset, limit, true, orderId, callback );
            }
        }

        boolean isFilter()
        {
            return orderId != null;
        }

        /**
         * Sets the filter criteria.
         *
         * @param orderId the order identification
         * @return {@code true} if criteria has changed
         */
        boolean filterBy( @Nullable Long orderId )
        {
            boolean clear = false;

            if ( this.orderId == null && orderId != null )
            {
                clear = true;
            }

            if ( this.orderId != null && orderId == null )
            {
                clear = true;
            }
            if ( this.orderId != null && orderId != null )
            {
                clear = !this.orderId.equals( orderId );
            }

            this.orderId = orderId;
            return clear;
        }
    }
}