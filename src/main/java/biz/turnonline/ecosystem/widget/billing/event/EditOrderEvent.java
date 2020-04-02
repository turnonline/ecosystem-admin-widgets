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

/**
 * Represents a request to view or edit {@link Order}.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class EditOrderEvent
        extends GwtEvent<EditOrderEventHandler>
{
    public static Type<EditOrderEventHandler> TYPE = new Type<>();

    private Long orderId;

    public EditOrderEvent()
    {
    }

    public EditOrderEvent( Long orderId )
    {
        this.orderId = orderId;
    }

    public EditOrderEvent( Order order )
    {
        this.orderId = order == null ? null : order.getId();
    }

    public Type<EditOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditOrderEventHandler handler )
    {
        handler.onEditOrder( this );
    }

    /**
     * Returns the order ID or {@code null} if the event represents a new order request.
     *
     * @return the order ID or {@code null}
     */
    public Long getId()
    {
        return orderId;
    }
}
