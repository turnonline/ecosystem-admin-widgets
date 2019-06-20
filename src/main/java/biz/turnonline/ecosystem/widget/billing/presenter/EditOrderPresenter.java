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

package biz.turnonline.ecosystem.widget.billing.presenter;

import biz.turnonline.ecosystem.widget.billing.event.CalculatePricingEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderBackEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditOrder;
import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditOrderPresenter
        extends Presenter<EditOrderPresenter.IView, AppEventBus>
{
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
        bus().addHandler( OrderBackEvent.TYPE, event -> controller().goTo( new Orders() ) );

        bus().addHandler( SaveOrderEvent.TYPE, event -> {
            Order order = event.getOrder();

            if ( order.getId() == null )
            {
                bus().billing().createOrder( order, ( SuccessCallback<Order> ) response -> {
                    success( messages.msgRecordCreated() );
                    controller().goTo( new EditOrder( response.getId(), "tabDetail" ) );
                } );
            }
            else
            {
                bus().billing().updateOrder( order.getId(), order,
                        ( SuccessCallback<Order> ) response -> success( messages.msgRecordUpdated() ) );
            }
        } );

        bus().addHandler( CalculatePricingEvent.TYPE, event ->
                bus().billing().calculate( event.getPricing(), response -> view().update( response ) ) );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newOrder() );

        EditOrder where = ( EditOrder ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().billing().findOrderById( where.getId(), ( SuccessCallback<Order> ) response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    private Order newOrder()
    {
        return new Order();
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Order>
    {
        void update( Pricing pricing );
    }
}