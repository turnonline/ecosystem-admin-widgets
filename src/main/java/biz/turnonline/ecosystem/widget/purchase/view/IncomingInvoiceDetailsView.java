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

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.DeleteIncomingInvoiceEvent;
import biz.turnonline.ecosystem.widget.purchase.event.ExpenseListEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderDetailEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.IncomingInvoiceDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.CreditorPanel;
import biz.turnonline.ecosystem.widget.purchase.ui.IncomingInvoiceDetails;
import biz.turnonline.ecosystem.widget.purchase.ui.IncomingInvoiceDetailsTabs;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.BillPayment;
import biz.turnonline.ecosystem.widget.shared.rest.billing.BillPricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Creditor;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.Transactions;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Incoming invoice detail view.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceDetailsView
        extends View<IncomingInvoice>
        implements IncomingInvoiceDetailsPresenter.IView
{
    private static EditInvoiceViewUiBinder binder = GWT.create( EditInvoiceViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    ConfirmationWindow confirmation;

    @UiField
    IncomingInvoiceDetailsTabs tabs;

    @UiField
    IncomingInvoiceDetails detail;

    @UiField( provided = true )
    PricingItemsPanel items;

    @UiField
    CreditorPanel creditor;

    @UiField
    Transactions transactions;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton viewOrder;

    @UiField
    MaterialAnchorButton downloadInvoice;

    @UiField
    MaterialAnchorButton deleteInvoice;

    @Inject
    public IncomingInvoiceDetailsView( @Named( "ViewIncomingInvoiceBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        items = new PricingItemsPanel( AppEventBus.get(), PricingItemsPanel.Context.VIEW_ONLY );
        setActive( Route.PURCHASES );

        add( binder.createAndBindUi( this ) );

        confirmation.getBtnOk().addClickHandler( event -> {
            IncomingInvoice ii = getRawModel();
            bus().fireEvent( new DeleteIncomingInvoiceEvent( ii.getOrderId(), ii.getId(), ii.getInvoiceNumber() ) );
        } );
    }

    @UiFactory
    Transactions createTransactions()
    {
        return new Transactions( bus() );
    }

    @Override
    protected void afterSetModel( IncomingInvoice invoice )
    {
        detail.fill( invoice );
        creditor.fill( invoice.getCreditor() );
        transactions.initFor( invoice.getOrderId(), invoice.getId() );

        BillPricing pricing = invoice.getPricing();
        items.reset();
        items.fill( pricing == null ? null : pricing.getItems() );

        downloadInvoice.setEnabled( invoice.getPin() != null );

        // If there is no account, it represents a purchase outside of the Ecosystem
        boolean outsideEcosystem = invoice.getCreditor() == null || invoice.getCreditor().getAccount() == null;
        BillPayment payment = invoice.getPayment();
        deleteInvoice.setVisible( ( payment == null || !( payment.getTotalAmount() <= 0.0 ) ) && outsideEcosystem );

    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new ExpenseListEvent( getRawModel() ) );
    }

    @UiHandler( "viewOrder" )
    public void viewOrderClick( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        IncomingInvoice invoice = getRawModel();
        bus().fireEvent( new PurchaseOrderDetailEvent( invoice.getOrderId() ) );
    }

    @UiHandler( "downloadInvoice" )
    public void downloadInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        IncomingInvoice ii = getRawModel();
        Creditor creditor = ii.getCreditor();
        boolean isAccount = creditor != null && creditor.getAccount() != null;

        bus().fireEvent( new DownloadInvoiceEvent( ii.getOrderId(), ii.getId(), ii.getPin(), !isAccount ) );
    }

    @UiHandler( "deleteInvoice" )
    public void deleteInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmation.open( AppMessages.INSTANCE.questionDeleteRecord() );
    }

    @Override
    public void selectTab( String tab )
    {
        tabs.selectTab( tab );
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
    public void fill( @Nullable List<Transaction> transactions )
    {
        this.transactions.fill( transactions );
    }

    interface EditInvoiceViewUiBinder
            extends UiBinder<HTMLPanel, IncomingInvoiceDetailsView>
    {
    }
}