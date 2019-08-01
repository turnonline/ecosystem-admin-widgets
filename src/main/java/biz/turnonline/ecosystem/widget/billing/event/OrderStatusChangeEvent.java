/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a notification that value for order status {@link Order#getStatus()} has changed.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrderStatusChangeEvent
        extends GwtEvent<OrderStatusChangeEventHandler>
{
    public static Type<OrderStatusChangeEventHandler> TYPE = new Type<>();

    private final Order.Status orderStatus;

    private final Long orderId;

    public OrderStatusChangeEvent( @Nonnull Order.Status orderStatus, @Nonnull Long orderId )
    {
        this.orderStatus = checkNotNull( orderStatus, "Order status can't be null" );
        this.orderId = checkNotNull( orderId, "Order ID can't be null" );
    }

    public Type<OrderStatusChangeEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the identification of the order that has changed its status.
     *
     * @return the order ID
     */
    public Long getOrderId()
    {
        return orderId;
    }

    /**
     * Returns the current order status.
     *
     * @return the current order status
     */
    public Order.Status getOrderStatus()
    {
        return orderStatus;
    }

    protected void dispatch( OrderStatusChangeEventHandler handler )
    {
        handler.onOrderStatusChange( this );
    }
}