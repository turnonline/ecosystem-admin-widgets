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

package biz.turnonline.ecosystem.widget.billing.presenter;

import biz.turnonline.ecosystem.widget.billing.event.InvoiceListEvent;
import biz.turnonline.ecosystem.widget.billing.event.InvoiceStatusChangeEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditInvoice;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
import biz.turnonline.ecosystem.widget.shared.event.TransactionListEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessOrAbsorbCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.BillPricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.gwt.client.facade.Items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.SENT;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditInvoicePresenter
        extends Presenter<EditInvoicePresenter.IView>
{
    @Inject
    public EditInvoicePresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( EditInvoice.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( InvoiceListEvent.TYPE, event -> controller().goTo( new Invoices( event.getScrollspy() ) ) );
        bus().addHandler( RecalculatedPricingEvent.TYPE, this::recalculated );
        bus().addHandler( InvoiceStatusChangeEvent.TYPE, this::changeInvoiceStatus );
        bus().addHandler( DownloadInvoiceEvent.TYPE, this::downloadInvoice );
        bus().addHandler( TransactionListEvent.TYPE, this::invoiceTransactions );

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
        invoice.setPricing( new BillPricing() );
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
        String email = event.getEmail();
        if ( email == null )
        {
            bus().billing().sendInvoice( event.getOrderId(), event.getInvoiceId(),
                    SENT == status, new Invoice(), new SendInvoiceCallback( event ) );
        }
        else
        {
            bus().billing().emailInvoice( event.getOrderId(), event.getInvoiceId(), Boolean.TRUE,
                    email, new Invoice(), new SendInvoiceCallback( event ) );
        }
    }

    private void downloadInvoice( DownloadInvoiceEvent event )
    {
        view().downloadInvoice( event.downloadInvoiceUrl() );
    }

    private void invoiceTransactions( TransactionListEvent event )
    {
        bus().paymentProcessor().getTransactions( event.getOrderId(), event.getInvoiceId(),
                ( SuccessOrAbsorbCallback<Items<Transaction>> ) response -> view().fill( response.getItems() ) );
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

        /**
         * Fills the transactions that are associated with this invoice.
         *
         * @param transactions the list of transaction
         */
        void fill( @Nullable List<Transaction> transactions );
    }

    private class SendInvoiceCallback
            implements FacadeCallback<Invoice>
    {
        private final InvoiceStatusChangeEvent event;

        private SendInvoiceCallback( InvoiceStatusChangeEvent event )
        {
            this.event = event;
        }

        @Override
        public void done( Invoice response, Failure failure )
        {
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
        }
    }
}