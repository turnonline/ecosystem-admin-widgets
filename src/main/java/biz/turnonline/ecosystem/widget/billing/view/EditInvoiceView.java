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

package biz.turnonline.ecosystem.widget.billing.view;

import biz.turnonline.ecosystem.widget.billing.event.DeleteInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.billing.event.InvoiceListEvent;
import biz.turnonline.ecosystem.widget.billing.event.InvoiceStatusChangeEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditInvoice;
import biz.turnonline.ecosystem.widget.billing.presenter.EditInvoicePresenter;
import biz.turnonline.ecosystem.widget.billing.ui.CustomerPanel;
import biz.turnonline.ecosystem.widget.billing.ui.EditInvoiceTabs;
import biz.turnonline.ecosystem.widget.billing.ui.InvoiceDetail;
import biz.turnonline.ecosystem.widget.billing.ui.InvoiceTransactions;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.NEW;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.SENT;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.valueOf;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditInvoiceView
        extends View<Invoice>
        implements EditInvoicePresenter.IView
{
    private static EditInvoiceViewUiBinder binder = GWT.create( EditInvoiceViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    ConfirmationWindow confirmation;

    @UiField
    EditInvoiceTabs tabs;

    @UiField
    InvoiceDetail detail;

    @UiField( provided = true )
    CustomerPanel customer;

    @UiField( provided = true )
    PricingItemsPanel items;

    @UiField( provided = true )
    InvoiceTransactions transactions;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton sendInvoice;

    @UiField
    MaterialAnchorButton emailInvoice;

    @UiField
    MaterialAnchorButton viewOrder;

    @UiField
    MaterialAnchorButton downloadInvoice;

    @UiField
    MaterialAnchorButton deleteInvoice;

    @UiField
    MaterialTextBox targetEmail;

    @UiField
    MaterialDialog emailDialog;

    private PlaceController controller;

    @Inject
    public EditInvoiceView( PlaceController controller,
                            @Named( "EditInvoiceBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                            AddressLookupListener addressLookup )
    {
        super();

        this.controller = controller;

        this.breadcrumb = breadcrumb;
        setActive( Route.INVOICES );

        customer = new CustomerPanel( bus(), addressLookup );
        items = new PricingItemsPanel( AppEventBus.get(), PricingItemsPanel.Context.INVOICE );
        transactions = new InvoiceTransactions();

        add( binder.createAndBindUi( this ) );

        targetEmail.setReturnBlankAsNull( true );

        confirmation.getBtnOk().addClickHandler( event -> {
            Invoice inv = getRawModel();
            bus().fireEvent( new DeleteInvoiceEvent( inv.getOrderId(), inv.getId(), inv.getInvoiceNumber() ) );
        } );
    }

    @UiFactory
    InvoiceDetail createInvoiceDetail()
    {
        return new InvoiceDetail( bus() );
    }

    @Override
    protected void beforeGetModel()
    {
        Invoice invoice = getRawModel();

        detail.bind( invoice );
        invoice.setCustomerIf( customer.bind( invoice.getCustomer() ) );

        InvoicePricing pricing = invoice.getPricing();
        if ( pricing == null )
        {
            pricing = new InvoicePricing();
            invoice.setPricing( pricing );
        }

        pricing.setItems( items.bind() );
    }

    @Override
    protected void afterSetModel()
    {
        Invoice invoice = getRawModel();

        detail.fill( invoice );
        customer.fill( invoice.getCustomer() );
        transactions.fill( invoice );

        InvoicePricing pricing = invoice.getPricing();
        items.fill( pricing == null ? null : pricing.getItems() );

        setStatus( status( invoice ) );
        downloadInvoice.setEnabled( invoice.getPin() != null );

        Scheduler.get().scheduleDeferred( () -> {
            EditInvoice where = ( EditInvoice ) controller.getWhere();
            tabs.selectTab( where.getTab() );
        } );
    }

    private Invoice.Status status( Invoice invoice )
    {
        return invoice.getStatus() == null ? NEW : valueOf( invoice.getStatus() );
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new InvoiceListEvent( getRawModel() ) );
    }

    @UiHandler( "btnSave" )
    public void handleSave( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new SaveInvoiceEvent( getModel() ) );
    }

    @UiHandler( "sendInvoice" )
    public void sendInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Invoice inv = getRawModel();
        bus().fireEvent( new InvoiceStatusChangeEvent( status( inv ), SENT, inv.getOrderId(), inv.getId() ) );
    }

    @UiHandler( "emailInvoice" )
    public void emailInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        emailDialog.open();
    }

    @UiHandler( "viewOrder" )
    public void viewOrderClick( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Invoice invoice = getRawModel();
        bus().fireEvent( new EditOrderEvent( invoice.getOrderId() ) );
    }

    @UiHandler( "downloadInvoice" )
    public void downloadInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Invoice inv = getRawModel();
        bus().fireEvent( new DownloadInvoiceEvent( inv.getOrderId(), inv.getId(), inv.getPin() ) );
    }

    @UiHandler( "deleteInvoice" )
    public void deleteInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmation.open( AppMessages.INSTANCE.questionDeleteRecord() );

    }

    @UiHandler( "btnSendInvoice" )
    public void btnSendInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        String email = targetEmail.getValue();
        if ( email != null )
        {
            Invoice inv = getRawModel();
            InvoiceStatusChangeEvent emailingEvent;
            emailingEvent = new InvoiceStatusChangeEvent( status( inv ), SENT, inv.getOrderId(), inv.getId() );
            emailingEvent.setEmail( email );

            bus().fireEvent( emailingEvent );
        }
        emailDialog.close();
    }

    @Override
    public void downloadInvoice( @Nonnull String url )
    {
        JavaScriptObject newWindow = newWindow( "", "_blank", "" );
        setWindowTarget( newWindow, checkNotNull( url, "Invoice PDF URL can't be null" ) );
    }

    @Override
    public void update( @Nonnull Pricing pricing )
    {
        items.update( pricing );
        detail.updatePricing( pricing.getTotalPriceExclVat(),
                pricing.getTotalVatBase(),
                pricing.getTotalPrice(),
                pricing.getItems() );
    }

    @Override
    public void setStatus( @Nonnull Invoice.Status status )
    {
        detail.setStatus( status );
        items.setReadOnly( NEW != status );

        boolean persisted = getRawModel() != null && getRawModel().getId() != null;
        viewOrder.setEnabled( persisted );
        sendInvoice.setEnabled( NEW == status && persisted );
        deleteInvoice.setEnabled( NEW == status && persisted );
        emailInvoice.setEnabled( NEW != status && persisted );
    }

    interface EditInvoiceViewUiBinder
            extends UiBinder<HTMLPanel, EditInvoiceView>
    {
    }
}