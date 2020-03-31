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
