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

import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to decline purchase order identified by ID.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DeclinePurchaseOrderEvent
        extends GwtEvent<DeclinePurchaseOrderEventHandler>
{
    public static Type<DeclinePurchaseOrderEventHandler> TYPE = new Type<>();

    private final PurchaseOrder purchaseOrder;

    public DeclinePurchaseOrderEvent( @Nonnull PurchaseOrder purchaseOrder )
    {
        this.purchaseOrder = checkNotNull( purchaseOrder, "Purchase order can't be null" );
    }

    public Type<DeclinePurchaseOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeclinePurchaseOrderEventHandler handler )
    {
        handler.onDeclinePurchaseOrder( this );
    }

    /**
     * Returns the purchase order to be declined.
     *
     * @return the purchase order to be declined
     */
    public PurchaseOrder getPurchaseOrder()
    {
        return purchaseOrder;
    }

    /**
     * Returns ID of the purchase order to be declined.
     *
     * @return the purchase order ID
     */
    public Long getId()
    {
        return purchaseOrder.getId();
    }
}
