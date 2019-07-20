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

import biz.turnonline.ecosystem.widget.billing.event.DeleteOrderEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.IssueOrderInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderBackEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderInvoicesEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditOrder;
import biz.turnonline.ecosystem.widget.billing.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.billing.ui.CustomerPanel;
import biz.turnonline.ecosystem.widget.billing.ui.EditOrderTabs;
import biz.turnonline.ecosystem.widget.billing.ui.InvoiceDetail;
import biz.turnonline.ecosystem.widget.billing.ui.OrderDetail;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ACTIVE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.FINISHED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.SUSPENDED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.TRIALING;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditOrderView
        extends View<Order>
        implements EditOrderPresenter.IView
{
    private static EditOrderViewUiBinder binder = GWT.create( EditOrderViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    EditOrderTabs tabs;

    @UiField
    OrderDetail detail;

    @UiField( provided = true )
    CustomerPanel<Order> customer;

    @UiField( provided = true )
    PricingItemsPanel items;

    @UiField
    InvoiceDetail lastInvoice;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton issueInvoice;

    @UiField
    MaterialAnchorButton viewInvoice;

    @UiField
    MaterialAnchorButton orderInvoices;

    @UiField
    MaterialAnchorButton deleteOrder;

    private PlaceController controller;

    @Inject
    public EditOrderView( AppEventBus eventBus,
                          PlaceController controller,
                          @Named( "EditOrderBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                          AddressLookupListener addressLookup )
    {
        super( eventBus );

        this.controller = controller;

        this.breadcrumb = breadcrumb;
        setActive( Route.ORDERS );

        customer = new CustomerPanel<>( eventBus, addressLookup );
        items = new PricingItemsPanel( eventBus, PricingItemsPanel.Context.ORDER );

        add( binder.createAndBindUi( this ) );
    }

    @UiFactory
    OrderDetail createOrderDetail()
    {
        return new OrderDetail( bus() );
    }

    @UiFactory
    InvoiceDetail createInvoiceDetail()
    {
        return new InvoiceDetail( bus() );
    }

    @Override
    protected void beforeGetModel()
    {
        Order order = getRawModel();

        detail.bind( order );
        customer.bind( order );
        order.setItems( items.bind() );
    }

    @Override
    protected void afterSetModel()
    {
        Order order = getRawModel();

        detail.fill( order );
        customer.fill( order );

        items.reset();
        items.fill( order.getItems() );

        Order.Status status = order.getStatus() == null ? SUSPENDED : Order.Status.valueOf( order.getStatus() );
        items.setReadOnly( FINISHED == status );

        evalActionButtonsEnable( status );

        Scheduler.get().scheduleDeferred( () -> {
            EditOrder where = ( EditOrder ) controller.getWhere();
            tabs.selectTab( where.getTab() );
        } );
    }

    private void evalActionButtonsEnable( Order.Status status )
    {
        Order order = getRawModel();
        Long orderId = order.getId();

        issueInvoice.setEnabled( orderId != null && ( ACTIVE == status || TRIALING == status ) );

        List<Invoice> invoices = order.getInvoices();
        viewInvoice.setEnabled( invoices != null && !invoices.isEmpty() );

        orderInvoices.setEnabled( orderId != null );
        deleteOrder.setEnabled( orderId != null && ( invoices == null || invoices.isEmpty() ) );
    }

    @UiHandler( "btnBack" )
    public void handleBack( ClickEvent event )
    {
        bus().fireEvent( new OrderBackEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( ClickEvent event )
    {
        bus().fireEvent( new SaveOrderEvent( getModel() ) );
    }

    @UiHandler( "issueInvoice" )
    public void issueInvoiceClick( ClickEvent event )
    {
        Order order = getRawModel();
        bus().fireEvent( new IssueOrderInvoiceEvent( order.getId() ) );
    }

    @UiHandler( "viewInvoice" )
    public void viewInvoiceClick( ClickEvent event )
    {
        Invoice invoice = lastInvoice.getInvoice();
        if ( invoice != null && invoice.getOrderId() != null && invoice.getId() != null )
        {
            bus().fireEvent( new EditInvoiceEvent( invoice ) );
        }
    }

    @UiHandler( "orderInvoices" )
    public void orderInvoicesClick( ClickEvent event )
    {
        Order order = getRawModel();
        bus().fireEvent( new OrderInvoicesEvent( order.getId() ) );
    }

    @UiHandler( "deleteOrder" )
    public void deleteOrderClick( ClickEvent event )
    {
        Order order = getRawModel();
        bus().fireEvent( new DeleteOrderEvent( order ) );
    }

    @Override
    public void lastInvoice( @Nullable Invoice invoice )
    {
        if ( invoice != null )
        {
            lastInvoice.fill( invoice );
            lastInvoice.setReadOnly( true );
        }
        else
        {
            lastInvoice.clear();
            lastInvoice.setReadOnly( false );
        }
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
    public void setBeginOnReadOnly( boolean readOnly )
    {
        detail.setBeginOnReadOnly( readOnly );
    }

    @Override
    public void setNextBillingDate( @Nonnull Date next )
    {
        detail.setNextBillingDate( next );
    }

    @Override
    public void setDueDate( @Nullable Date dueDate )
    {
        detail.setDueDate( dueDate );
    }

    @Override
    public void setNumberOfDays( Integer days )
    {
        detail.setNumberOfDays( days );
    }

    @Override
    public void setStatus( @Nonnull Order.Status status )
    {
        detail.setStatus( status );
        evalActionButtonsEnable( status );
    }

    @Override
    public void addItem( @Nonnull Product product,
                         @Nonnull TreeItemWithModel parentItem )
    {
        items.fill( product, parentItem );
    }

    interface EditOrderViewUiBinder
            extends UiBinder<HTMLPanel, EditOrderView>
    {
    }
}