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

import biz.turnonline.ecosystem.widget.billing.event.DeleteInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditInvoice;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoicesPresenter
        extends Presenter<InvoicesPresenter.IView, AppEventBus>
{
    @Inject
    public InvoicesPresenter( AppEventBus eventBus,
                              IView view,
                              PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditInvoiceEvent.TYPE, event ->
                controller().goTo( new EditInvoice( event.getOrderId(), event.getInvoiceId(), "tabDetail" ) )
        );

        bus().addHandler( DeleteInvoiceEvent.TYPE, this::deleteInvoice );

        view().setDataSource( ( offset, limit, callback ) ->
                bus().billing().getInvoices( offset, limit, true, callback ) );
    }

    private void deleteInvoice( DeleteInvoiceEvent event )
    {
        bus().billing().deleteInvoice( event.getOrderId(), event.getInvoiceId(), ( response, failure ) -> {
            controller().goTo( new Invoices() );
            success( messages.msgRecordDeleted( event.getInvoiceNumber() ), failure );
        } );
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
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView
    {
        void scrollTo( @Nullable String scrollspy );

        void setDataSource( InfiniteScroll.Callback<Invoice> callback );
    }
}