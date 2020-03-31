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

package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to view details of the purchase order identified by ID.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderDetailEvent
        extends GwtEvent<PurchaseOrderDetailEventHandler>
{
    public static Type<PurchaseOrderDetailEventHandler> TYPE = new Type<>();

    private final Long orderId;

    public PurchaseOrderDetailEvent( @Nonnull Long orderId )
    {
        this.orderId = checkNotNull( orderId, "Purchase order ID can't be null" );
    }

    public Type<PurchaseOrderDetailEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( PurchaseOrderDetailEventHandler handler )
    {
        handler.onPurchaseOrderDetail( this );
    }

    /**
     * Returns the purchase order ID.
     *
     * @return the purchase order ID
     */
    public Long getId()
    {
        return orderId;
    }
}
