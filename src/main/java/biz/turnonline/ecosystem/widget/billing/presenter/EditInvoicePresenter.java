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

import biz.turnonline.ecosystem.widget.billing.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.InvoiceBackEvent;
import biz.turnonline.ecosystem.widget.billing.event.InvoiceStatusChangeEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditInvoice;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import com.google.gwt.place.shared.PlaceController;
import org.fusesource.restygwt.client.ServiceRoots;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.ArrayList;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_API_ROOT;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.SENT;

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
        bus().addHandler( RecalculatedPricingEvent.TYPE, this::recalculated );
        bus().addHandler( InvoiceStatusChangeEvent.TYPE, this::changeInvoiceStatus );
        bus().addHandler( DownloadInvoiceEvent.TYPE, this::downloadInvoice );

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

    private void recalculated( RecalculatedPricingEvent event )
    {
        view().update( event.getPricing() );
    }

    private void changeInvoiceStatus( InvoiceStatusChangeEvent event )
    {
        Invoice.Status status = event.getInvoiceStatus();
        Invoice invoice = new Invoice();
        String email = event.getEmail();
        if ( email != null )
        {
            Customer customer = new Customer();
            customer.setContactEmail( email );
            invoice.setCustomer( customer );
        }

        bus().billing().sendInvoice( event.getOrderId(), event.getInvoiceId(),
                SENT == status, invoice, ( response, failure ) -> {
                    if ( SENT.name().equalsIgnoreCase( response.getStatus() ) )
                    {
                        success( messages.msgInvoiceStatusSent(), failure );
                        view().setStatus( SENT );
                    }
                    else if ( failure.isNotFound() )
                    {
                        error( AppMessages.INSTANCE.msgErrorRecordDoesNotExists() );
                        view().setStatus( event.getOriginStatus() );
                    }
                    else if ( failure.isBadRequest() )
                    {
                        error( AppMessages.INSTANCE.msgErrorBadRequest( failure.response().getText() ) );
                        view().setStatus( event.getOriginStatus() );
                    }
                    else
                    {
                        view().setStatus( event.getOriginStatus() );
                    }
                } );
    }

    private void downloadInvoice( DownloadInvoiceEvent event )
    {
        String url = ServiceRoots.get( PRODUCT_BILLING_API_ROOT )
                + "pdf/orders/"
                + event.getOrderId()
                + "/invoices/"
                + event.getInvoiceId()
                + "/"
                + event.getPin();
        view().downloadInvoice( url );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Invoice>
    {
        /**
         * Downloads invoice PDF from the specified URL.
         *
         * @param url the full path to the invoice PDF
         */
        void downloadInvoice( @Nonnull String url );

        /**
         * Updates the order's pricing (details and items) UI by recalculated price.
         *
         * @param pricing the recalculated price
         */
        void update( @Nonnull Pricing pricing );

        /**
         * Sets the current invoice status. It have an impact on whether some action buttons will be enabled or not.
         *
         * @param status the current status to be set
         */
        void setStatus( @Nonnull Invoice.Status status );
    }
}