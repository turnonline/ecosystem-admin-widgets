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
 * Represents a request to delete specified order.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DeleteOrderEvent
        extends GwtEvent<DeleteOrderEventHandler>
{
    public static Type<DeleteOrderEventHandler> TYPE = new Type<DeleteOrderEventHandler>();

    private final Order order;

    public DeleteOrderEvent( @Nonnull Order order )
    {
        this.order = checkNotNull( order, "Order cannot be null" );
    }

    public Type<DeleteOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteOrderEventHandler handler )
    {
        handler.onDeleteOrder( this );
    }

    public Order getOrder()
    {
        return order;
    }
}
