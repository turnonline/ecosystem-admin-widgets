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

import biz.turnonline.ecosystem.widget.billing.event.OrderBackEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditOrder;
import biz.turnonline.ecosystem.widget.billing.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.billing.ui.CustomerPanel;
import biz.turnonline.ecosystem.widget.billing.ui.EditOrderTabs;
import biz.turnonline.ecosystem.widget.billing.ui.OrderDetail;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;

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
    MaterialButton btnSave;

    // -- buttons

    @UiField
    MaterialButton btnBack;

    private PlaceController controller;

    @Inject
    public EditOrderView( EventBus eventBus,
                          PlaceController controller,
                          @Named( "EditOrderBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                          AddressLookupListener addressLookup )
    {
        super( eventBus );

        this.controller = controller;

        this.breadcrumb = breadcrumb;
        setActive( Route.ORDERS );

        customer = new CustomerPanel<>( eventBus, addressLookup );
        items = new PricingItemsPanel( eventBus );

        add( binder.createAndBindUi( this ) );
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
        items.fill( order.getItems() );

        Scheduler.get().scheduleDeferred( () -> {
            EditOrder where = ( EditOrder ) controller.getWhere();
            tabs.selectTab( where.getTab() );
        } );
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

    @Override
    public void update( Pricing pricing )
    {
        items.update( pricing );
    }

    interface EditOrderViewUiBinder
            extends UiBinder<HTMLPanel, EditOrderView>
    {
    }
}