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

package biz.turnonline.ecosystem.widget.invoice.presenter;

import biz.turnonline.ecosystem.widget.invoice.event.DeleteInvoiceEvent;
import biz.turnonline.ecosystem.widget.invoice.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.invoice.place.EditInvoice;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Invoice;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class InvoicesPresenter
        extends Presenter<InvoicesPresenter.IView, AppEventBus>
{
    public interface IView
            extends biz.turnonline.ecosystem.widget.shared.view.IView
    {
        void refresh();
    }

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
                controller().goTo( new EditInvoice(
                        event.getInvoice() != null ? event.getInvoice().getOrderId() : null,
                        event.getInvoice() != null ? event.getInvoice().getId() : null,
                        "tabDetail" ) )
        );

        bus().addHandler( DeleteInvoiceEvent.TYPE, event -> {
            for ( Invoice invoice : event.getInvoices() )
            {
                bus().productBilling().deleteInvoice( invoice.getOrderId(), invoice.getId(), ( response ) -> {
                    success( messages.msgRecordDeleted( Formatter.formatInvoiceName( invoice ) ) );
                    Scheduler.get().scheduleDeferred( () -> view().refresh() );
                } );
            }
        } );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();
    }
}