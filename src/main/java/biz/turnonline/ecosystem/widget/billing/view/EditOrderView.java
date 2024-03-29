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

package biz.turnonline.ecosystem.widget.billing.view;

import biz.turnonline.ecosystem.widget.billing.event.DeleteOrderEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.IssueOrderInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderInvoicesEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderListEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditOrder;
import biz.turnonline.ecosystem.widget.billing.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.billing.ui.CustomerPanel;
import biz.turnonline.ecosystem.widget.billing.ui.EditOrderTabs;
import biz.turnonline.ecosystem.widget.billing.ui.InvoiceDetail;
import biz.turnonline.ecosystem.widget.billing.ui.OrderDetail;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
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
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.COMPLETED;
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
    ConfirmationWindow confirmation;

    @UiField
    EditOrderTabs tabs;

    @UiField
    OrderDetail detail;

    @UiField( provided = true )
    CustomerPanel customer;

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
    public EditOrderView( PlaceController controller,
                          @Named( "EditOrderBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                          AddressLookupListener addressLookup )
    {
        super();

        this.controller = controller;

        this.breadcrumb = breadcrumb;
        setActive( Route.ORDERS );

        customer = new CustomerPanel( bus(), addressLookup );
        items = new PricingItemsPanel( AppEventBus.get(), PricingItemsPanel.Context.ORDER );

        add( binder.createAndBindUi( this ) );

        confirmation.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeleteOrderEvent( getRawModel() ) ) );
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
    protected void beforeGetModel( Order order )
    {
        detail.bind( order );
        order.setCustomerIf( customer.bind( order.getCustomer() ) );
        order.setItems( items.bind() );
    }

    @Override
    protected void afterSetModel( Order order )
    {
        detail.fill( order );
        customer.fill( order.getCustomer() );

        items.reset();
        items.fill( order.getItems() );

        Order.Status status = order.getStatus() == null ? SUSPENDED : Order.Status.valueOf( order.getStatus() );
        items.setReadOnly( FINISHED == status || COMPLETED == status );

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
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new OrderListEvent( getRawModel() ) );
    }

    @UiHandler( "btnSave" )
    public void handleSave( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new SaveOrderEvent( getModel() ) );
    }

    @UiHandler( "issueInvoice" )
    public void issueInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Order order = getRawModel();
        bus().fireEvent( new IssueOrderInvoiceEvent( order.getId() ) );
    }

    @UiHandler( "viewInvoice" )
    public void viewInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Invoice invoice = lastInvoice.getInvoice();
        if ( invoice != null && invoice.getOrderId() != null && invoice.getId() != null )
        {
            bus().fireEvent( new EditInvoiceEvent( invoice ) );
        }
    }

    @UiHandler( "orderInvoices" )
    public void orderInvoices( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Order order = getRawModel();
        bus().fireEvent( new OrderInvoicesEvent( order.getId() ) );
    }

    @UiHandler( "deleteOrder" )
    public void deleteOrder( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmation.open( AppMessages.INSTANCE.questionDeleteRecord() );
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