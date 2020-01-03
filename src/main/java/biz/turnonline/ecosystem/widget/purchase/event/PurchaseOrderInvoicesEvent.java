/*
 * Copyright (c) 2020 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to list all invoices for specific purchase order identified by its ID.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderInvoicesEvent
        extends GwtEvent<PurchaseOrderInvoicesEventHandler>
{
    public static Type<PurchaseOrderInvoicesEventHandler> TYPE = new Type<>();

    private final Long orderId;

    public PurchaseOrderInvoicesEvent( @Nonnull Long orderId )
    {
        this.orderId = checkNotNull( orderId, "Purchase order can't be null" );
    }

    public Type<PurchaseOrderInvoicesEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the purchase order identification.
     *
     * @return the purchase order ID
     */
    public Long getOrderId()
    {
        return orderId;
    }

    protected void dispatch( PurchaseOrderInvoicesEventHandler handler )
    {
        handler.onPurchaseOrderInvoices( this );
    }
}
