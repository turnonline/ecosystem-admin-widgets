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

package biz.turnonline.ecosystem.widget.order.presenter;

import biz.turnonline.ecosystem.widget.order.event.BackEvent;
import biz.turnonline.ecosystem.widget.order.event.SaveOrderEvent;
import biz.turnonline.ecosystem.widget.order.place.EditOrder;
import biz.turnonline.ecosystem.widget.order.place.Orders;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditOrderPresenter
        extends Presenter<EditOrderPresenter.IView, AppEventBus>
{
    public interface IView
            extends biz.turnonline.ecosystem.widget.shared.view.IView<Order>
    {
    }

    @Inject
    public EditOrderPresenter( AppEventBus eventBus,
                               IView view,
                               PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new Orders() ) );

        bus().addHandler( SaveOrderEvent.TYPE, event -> {
            Order order = event.getOrder();

            if ( order.getId() == null )
            {
                bus().billing().createOrder( order, response -> {
                    success( messages.msgRecordCreated() );
                    controller().goTo( new Orders() );
                } );
            }
            else
            {
                bus().billing().updateOrder( order.getId(), order, response -> {
                    success( messages.msgRecordUpdated() );
                    controller().goTo( new Orders() );
                } );
            }
        } );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newOrder() );

        EditOrder where = ( EditOrder ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().billing().findOrderById( where.getId(), response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    private Order newOrder()
    {
        Order order = new Order();

        return order;
    }
}