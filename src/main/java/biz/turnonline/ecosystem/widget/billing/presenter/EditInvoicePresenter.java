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

import biz.turnonline.ecosystem.widget.billing.event.InvoiceBackEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditInvoice;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditInvoicePresenter
        extends Presenter<EditInvoicePresenter.IView, AppEventBus>
{
    @Inject
    public EditInvoicePresenter( AppEventBus eventBus,
                                 IView view,
                                 PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( InvoiceBackEvent.TYPE, event -> controller().goTo( new Invoices() ) );

        bus().addHandler( SaveInvoiceEvent.TYPE, event -> {
            Invoice invoice = event.getInvoice();

            if ( invoice.getId() == null )
            {
                bus().billing().createInvoice( invoice, ( SuccessCallback<Invoice> ) response -> {
                    success( messages.msgRecordCreated() );
                    controller().goTo( new EditInvoice( response.getOrderId(), response.getId(), "tabDetail" ) );
                } );
            }
            else
            {
                bus().billing().updateInvoice( invoice.getOrderId(), invoice.getId(), invoice,
                        ( SuccessCallback<Invoice> ) response -> success( messages.msgRecordUpdated() ) );
            }
        } );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newInvoice() );

        EditInvoice where = ( EditInvoice ) controller().getWhere();
        if ( where.getInvoiceId() != null )
        {
            bus().billing().findInvoiceById( where.getOrderId(), where.getInvoiceId(),
                    ( SuccessCallback<Invoice> ) response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    private Invoice newInvoice()
    {
        Invoice invoice = new Invoice();
        invoice.setPricing( new InvoicePricing() );
        invoice.getPricing().setItems( new ArrayList<>() );
        invoice.getPricing().getItems().add( new PricingItem() );

        return invoice;
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Invoice>
    {
    }
}