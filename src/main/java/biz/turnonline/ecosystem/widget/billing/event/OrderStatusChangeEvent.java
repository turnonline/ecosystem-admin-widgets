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
