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

package biz.turnonline.ecosystem.widget.order.view;

import biz.turnonline.ecosystem.widget.order.AppEventBus;
import biz.turnonline.ecosystem.widget.order.event.DeleteOrderEvent;
import biz.turnonline.ecosystem.widget.order.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.order.presenter.OrdersPresenter;
import biz.turnonline.ecosystem.widget.order.ui.ColumnActions;
import biz.turnonline.ecosystem.widget.order.ui.ColumnCustomer;
import biz.turnonline.ecosystem.widget.order.ui.ColumnId;
import biz.turnonline.ecosystem.widget.order.ui.ColumnPrice;
import biz.turnonline.ecosystem.widget.order.ui.ColumnStatus;
import biz.turnonline.ecosystem.widget.order.ui.OrdersDataSource;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow.Question;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTable;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class OrdersView
        extends View
        implements OrdersPresenter.IView
{
    private static OrdersViewUiBinder binder = GWT.create( OrdersViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    MaterialButton btnNew;

    @UiField
    MaterialButton btnDelete;

    @UiField
    SmartTable<Order> table;

    @UiField
    ConfirmationWindow confirmationWindow;

    @Override
    public void refresh()
    {
        table.refresh();
    }

    interface OrdersViewUiBinder
            extends UiBinder<HTMLPanel, OrdersView>
    {
    }

    @Inject
    public OrdersView( EventBus eventBus, @Named( "OrdersBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        scaffoldNavBar.setActive( Route.ORDERS );

        add( binder.createAndBindUi( this ) );
        initTable();

        confirmationWindow.getBtnOk().addClickHandler( event -> {
            List<Order> selectedRowModels = table.getSelectedRowModels( false );
            bus().fireEvent( new DeleteOrderEvent( selectedRowModels ) );
        } );
    }

    private void initTable()
    {
        ColumnId id = new ColumnId();
        id.setWidth( "20%" );

        ColumnStatus status = new ColumnStatus();
        status.setWidth( "20%" );

        ColumnCustomer customer = new ColumnCustomer();
        customer.setWidth( "25%" );

        ColumnPrice price = new ColumnPrice();
        price.setWidth( "20%" );

        ColumnActions actions = new ColumnActions( bus() );
        actions.setWidth( "5%" );

        table.addColumn( id, messages.labelId() );
        table.addColumn( status, messages.labelStatus() );
        table.addColumn( customer, messages.labelCustomer() );
        table.addColumn( price, messages.labelPrice() );
        table.addColumn( actions );

        table.configure( new OrdersDataSource( ( AppEventBus ) bus() ) );
    }

    @UiHandler( "btnNew" )
    public void handleNew( ClickEvent event )
    {
        bus().fireEvent( new EditOrderEvent() );
    }

    @UiHandler( "btnDelete" )
    public void handleDelete( ClickEvent event )
    {
        List<Order> selected = table.getSelectedRowModels( false );
        if ( !selected.isEmpty() )
        {
            confirmationWindow.open( new Question()
            {
                @Override
                public int selectedRecords()
                {
                    return selected.size();
                }

                @Override
                public String name()
                {
                    return Formatter.formatOrderName( selected.get( 0 ) );
                }
            } );
        }
    }
}