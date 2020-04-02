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
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class SaveOrderEvent
        extends GwtEvent<SaveOrderEventHandler>
{
    public static Type<SaveOrderEventHandler> TYPE = new Type<SaveOrderEventHandler>();

    private final Order order;

    public SaveOrderEvent( Order order )
    {
        this.order = order;
    }

    public Type<SaveOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveOrderEventHandler handler )
    {
        handler.onSaveOrder( this );
    }

    public Order getOrder()
    {
        return order;
    }
}
