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

import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

/**
 * Represents a request to list all invoices for specific order identified by its ID.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrderInvoicesEvent
        extends GwtEvent<OrderInvoicesEventHandler>
{
    public static Type<OrderInvoicesEventHandler> TYPE = new Type<>();

    private final Long orderId;

    public OrderInvoicesEvent( @Nonnull Long orderId )
    {
        this.orderId = orderId;
    }

    public Type<OrderInvoicesEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the order identification to list its invoices.
     *
     * @return the order ID
     */
    public Long getOrderId()
    {
        return orderId;
    }

    protected void dispatch( OrderInvoicesEventHandler handler )
    {
        handler.onOrderInvoices( this );
    }
}
