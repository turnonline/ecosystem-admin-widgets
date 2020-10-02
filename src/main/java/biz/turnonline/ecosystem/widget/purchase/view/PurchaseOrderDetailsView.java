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

import biz.turnonline.ecosystem.widget.purchase.event.DeclinePurchaseOrderEvent;
import biz.turnonline.ecosystem.widget.purchase.event.IncomingInvoiceDetailsEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderInvoicesEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderListEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.PurchaseOrderDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.CreditorPanel;
import biz.turnonline.ecosystem.widget.purchase.ui.IncomingInvoiceDetails;
import biz.turnonline.ecosystem.widget.purchase.ui.PurchaseOrderDetails;
import biz.turnonline.ecosystem.widget.purchase.ui.PurchaseOrderDetailsTabs;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ACTIVE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.SUSPENDED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.TRIALING;
import static gwt.material.design.client.constants.IconType.CLOSE;
import static gwt.material.design.client.constants.IconType.DELETE;

/**
 * Purchase order detail view.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderDetailsView
        extends View<PurchaseOrder>
        implements PurchaseOrderDetailsPresenter.IView
{
    private static EditContactsViewUiBinder binder = GWT.create( EditContactsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    ConfirmationWindow confirmation;

    @UiField
    PurchaseOrderDetailsTabs tabs;

    @UiField
    PurchaseOrderDetails detail;

    @UiField( provided = true )
    PricingItemsPanel items;

    @UiField
    CreditorPanel creditor;

    @UiField
    IncomingInvoiceDetails lastInvoice;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton viewLastInvoice;

    @UiField
    MaterialAnchorButton decline;

    @Inject
    public PurchaseOrderDetailsView( @Named( "ViewPurchaseOrderBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        items = new PricingItemsPanel( AppEventBus.get(), PricingItemsPanel.Context.VIEW_ONLY );
        setActive( Route.CONTACTS );

        add( binder.createAndBindUi( this ) );

        confirmation.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeclinePurchaseOrderEvent( getRawModel() ) ) );
    }

    @Override
    protected void afterSetModel( PurchaseOrder order )
    {
        detail.fill( order );
        creditor.fill( order.getCreditor() );

        Order.Status status = order.getStatus() == null ? SUSPENDED : Order.Status.valueOf( order.getStatus() );
        decline.setEnabled( status == TRIALING || status == ACTIVE || status == SUSPENDED );

        items.reset();
        items.fill( order.getItems() );

        List<IncomingInvoice> invoices = order.getInvoices();
        if ( invoices != null && !invoices.isEmpty() )
        {
            lastInvoice.fill( invoices.get( 0 ) );
            viewLastInvoice.setEnabled( true );
        }
        else
        {
            viewLastInvoice.setEnabled( false );
        }

        if ( isOutsideEcosystem() )
        {
            decline.setIconType( DELETE );
            decline.setTooltip( messages.tooltipPurchaseOrderDelete() );
        }
        else
        {
            decline.setIconType( CLOSE );
            decline.setTooltip( messages.tooltipPurchaseOrderDecline() );
        }
    }

    /**
     * If there is no account (returns true), it represents a purchase outside of the Ecosystem.
     *
     * @return true if purchase outside Ecosystem
     */
    private boolean isOutsideEcosystem()
    {
        PurchaseOrder order = getRawModel();
        return order.getCreditor() == null || order.getCreditor().getAccount() == null;
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new PurchaseOrderListEvent( getRawModel() ) );
    }

    @UiHandler( "decline" )
    public void deletePurchaseOrder( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        PurchaseOrder order = getRawModel();
        if ( isOutsideEcosystem() )
        {
            confirmation.open( AppMessages.INSTANCE.questionPurchaseOrderDelete( order.formattedName() ) );
        }
        else
        {
            confirmation.open( AppMessages.INSTANCE.questionPurchaseOrderDecline( order.formattedName() ) );
        }
    }

    @UiHandler( "orderInvoices" )
    public void orderInvoices( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        PurchaseOrder order = getRawModel();
        bus().fireEvent( new PurchaseOrderInvoicesEvent( order.getId() ) );
    }

    @UiHandler( "viewLastInvoice" )
    public void viewLastInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        IncomingInvoice invoice = lastInvoice.getInvoice();
        if ( invoice != null && invoice.getOrderId() != null && invoice.getId() != null )
        {
            bus().fireEvent( new IncomingInvoiceDetailsEvent( invoice.getOrderId(), invoice.getId() ) );
        }
    }

    @Override
    public void selectTab( String tab )
    {
        tabs.selectTab( tab );
    }

    interface EditContactsViewUiBinder
            extends UiBinder<HTMLPanel, PurchaseOrderDetailsView>
    {
    }
}