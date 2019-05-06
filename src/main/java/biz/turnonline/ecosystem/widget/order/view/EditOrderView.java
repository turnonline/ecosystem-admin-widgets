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

import biz.turnonline.ecosystem.widget.order.event.BackEvent;
import biz.turnonline.ecosystem.widget.order.event.SaveOrderEvent;
import biz.turnonline.ecosystem.widget.order.place.EditOrder;
import biz.turnonline.ecosystem.widget.order.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.order.ui.Detail;
import biz.turnonline.ecosystem.widget.order.ui.EditOrderTabs;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import biz.turnonline.ecosystem.widget.shared.ui.CustomerPanel;
import biz.turnonline.ecosystem.widget.shared.ui.Items;
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
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditOrderView
        extends View<Order>
        implements EditOrderPresenter.IView
{
    private static EditOrderViewUiBinder binder = GWT.create( EditOrderViewUiBinder.class );

    private PlaceController controller;

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    EditOrderTabs tabs;

    @UiField
    Detail detail;

    @UiField( provided = true )
    CustomerPanel<Order> customer;

    @UiField( provided = true )
    Items<Order> items;

    // -- buttons

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    interface EditOrderViewUiBinder
            extends UiBinder<HTMLPanel, EditOrderView>
    {
    }

    @Inject
    public EditOrderView( EventBus eventBus,
                          PlaceController controller,
                          @Named( "EditOrderBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.controller = controller;

        this.breadcrumb = breadcrumb;
        scaffoldNavBar.setActive( Route.ORDERS );

        customer = new CustomerPanel<>( eventBus );
        items = new Items<>( eventBus );

        add( binder.createAndBindUi( this ) );
    }

    @Override
    protected void bind()
    {
        Order order = getRawModel();

        detail.bind( order );
        customer.bind( order );
        items.bind( order );
    }

    @Override
    protected void fill()
    {
        Order order = getRawModel();

        detail.fill( order );
        customer.fill( order );
        items.fill( order );

        Scheduler.get().scheduleDeferred( () -> {
            EditOrder where = ( EditOrder ) controller.getWhere();
            tabs.selectTab( where.getTab() );
        } );
    }

    @UiHandler( "btnBack" )
    public void handleBack( ClickEvent event )
    {
        bus().fireEvent( new BackEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( ClickEvent event )
    {
        bus().fireEvent( new SaveOrderEvent( getModel() ) );
    }
}