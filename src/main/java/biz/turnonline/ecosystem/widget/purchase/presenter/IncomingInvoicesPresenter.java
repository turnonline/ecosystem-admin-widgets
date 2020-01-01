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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.ClearIncomingInvoicesFilterEvent;
import biz.turnonline.ecosystem.widget.purchase.event.IncomingInvoiceDetailsEvent;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoiceDetails;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoices;
import biz.turnonline.ecosystem.widget.shared.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
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
public class IncomingInvoicesPresenter
        extends Presenter<IncomingInvoicesPresenter.IView>
{
    private InvoiceDataSource dataSource;

    @Inject
    public IncomingInvoicesPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( IncomingInvoices.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( IncomingInvoiceDetailsEvent.TYPE, event ->
                controller().goTo( new IncomingInvoiceDetails( event.getOrderId(), event.getInvoiceId(), "tabDetail" ) )
        );

        bus().addHandler( DownloadInvoiceEvent.TYPE, e -> view().downloadInvoice( e.downloadInvoiceUrl() ) );
        bus().addHandler( ClearIncomingInvoicesFilterEvent.TYPE, this::clearFilter );

        view().setDataSource( dataSource = new InvoiceDataSource() );
    }

    private void clearFilter( ClearIncomingInvoicesFilterEvent event )
    {
        dataSource.filterBy( null );
        view().clear();
        controller().goTo( new IncomingInvoices() );
    }

    @Override
    protected void onBeforeBackingObject()
    {
        IncomingInvoices where = ( IncomingInvoices ) controller().getWhere();
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

        IncomingInvoices where = ( IncomingInvoices ) controller().getWhere();
        if ( where.getScrollspy() != null )
        {
            view().scrollTo( where.getScrollspy() );
        }

        view().setClearFilterEnabled( dataSource.isFilter() );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<IncomingInvoice>
    {
        /**
         * Downloads invoice PDF from the specified URL.
         *
         * @param url the full path to the invoice PDF
         */
        void downloadInvoice( @Nonnull String url );

        void scrollTo( @Nullable String scrollspy );

        void clear();

        void setDataSource( InfiniteScroll.Callback<IncomingInvoice> callback );

        void setClearFilterEnabled( boolean enabled );
    }

    private class InvoiceDataSource
            implements InfiniteScroll.Callback<IncomingInvoice>
    {
        private Long orderId;

        @Override
        public void load( int offset, int limit, SuccessCallback<Items<IncomingInvoice>> callback )
        {
            if ( orderId == null )
            {
                bus().billing().searchIncomingInvoices( offset, limit, true, callback );
            }
            else
            {
                bus().billing().listOrderIncomingInvoices( orderId, offset, limit, true, callback );
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