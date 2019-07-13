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

import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to issue a new invoice based on the order that's identified by Order ID.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IssueOrderInvoiceEvent
        extends GwtEvent<IssueOrderInvoiceEventHandler>
{
    public static Type<IssueOrderInvoiceEventHandler> TYPE = new Type<>();

    private final Long orderId;

    public IssueOrderInvoiceEvent( @Nonnull Long orderId )
    {
        this.orderId = checkNotNull( orderId, "Order ID cannot be null" );
    }

    public Type<IssueOrderInvoiceEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the order identification as a source to issue a new invoice.
     *
     * @return the order ID
     */
    public Long getOrderId()
    {
        return orderId;
    }

    protected void dispatch( IssueOrderInvoiceEventHandler handler )
    {
        handler.onIssueOrderInvoice( this );
    }
}
