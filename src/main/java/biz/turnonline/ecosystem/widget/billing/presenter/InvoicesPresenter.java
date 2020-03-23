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

package biz.turnonline.ecosystem.widget.billing.presenter;

import biz.turnonline.ecosystem.widget.billing.event.ClearInvoicesFilterEvent;
import biz.turnonline.ecosystem.widget.billing.event.DeleteInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditInvoice;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.gwt.client.facade.Items;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoicesPresenter
        extends Presenter<InvoicesPresenter.IView>
{
    private InvoiceDataSource dataSource;

    @Inject
    public InvoicesPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( Invoices.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditInvoiceEvent.TYPE, event ->
                controller().goTo( new EditInvoice( event.getOrderId(), event.getInvoiceId(), "tabDetail" ) )
        );

        bus().addHandler( DeleteInvoiceEvent.TYPE, this::deleteInvoice );
        bus().addHandler( ClearInvoicesFilterEvent.TYPE, this::clearFilter );

        view().setDataSource( dataSource = new InvoiceDataSource() );
    }

    private void deleteInvoice( DeleteInvoiceEvent event )
    {
        bus().billing().deleteInvoice( event.getOrderId(), event.getInvoiceId(), ( response, failure ) -> {
            controller().goTo( new Invoices() );
            success( messages.msgRecordDeleted( event.getInvoiceNumber() ), failure );
        } );
    }

    private void clearFilter( ClearInvoicesFilterEvent event )
    {
        dataSource.filterBy( null );
        view().clear();
        controller().goTo( new Invoices() );
    }

    @Override
    protected void onBeforeBackingObject()
    {
        Invoices where = ( Invoices ) controller().getWhere();
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

        Invoices where = ( Invoices ) controller().getWhere();
        if ( where.getScrollspy() != null )
        {
            view().scrollTo( where.getScrollspy() );
        }

        view().setClearFilterEnabled( dataSource.isFilter() );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<List<Invoice>>
    {
        void scrollTo( @Nullable String scrollspy );

        void clear();

        void setDataSource( InfiniteScroll.Callback<Invoice> callback );

        void setClearFilterEnabled( boolean enabled );
    }

    private class InvoiceDataSource
            implements InfiniteScroll.Callback<Invoice>
    {
        private Long orderId;

        @Override
        public void load( int offset, int limit, SuccessCallback<Items<Invoice>> callback )
        {
            if ( orderId == null )
            {
                bus().billing().getInvoices( offset, limit, true, callback );
            }
            else
            {
                bus().billing().getOrderInvoices( orderId, offset, limit, true, callback );
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