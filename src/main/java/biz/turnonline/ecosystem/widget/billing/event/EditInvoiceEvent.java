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

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nullable;

/**
 * Represents an event that navigates to a view to edit the specified invoice identified by
 * <ul>
 * <li>{@link Invoice#getOrderId()} Order ID</li>
 * <li>{@link Invoice#getId()} Invoice ID</li>
 * </ul>
 * Event created by no arg constructor {@link EditInvoiceEvent} will open an edit form to create a new invoice.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditInvoiceEvent
        extends GwtEvent<EditInvoiceEventHandler>
{
    public static Type<EditInvoiceEventHandler> TYPE = new Type<>();

    private Long orderId;

    private Long invoiceId;

    public EditInvoiceEvent()
    {
    }

    public EditInvoiceEvent( @Nullable Invoice invoice )
    {
        this.orderId = invoice == null ? null : invoice.getOrderId();
        this.invoiceId = invoice == null ? null : invoice.getId();
    }

    public Type<EditInvoiceEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditInvoiceEventHandler handler )
    {
        handler.onEditInvoice( this );
    }

    /**
     * Returns the order ID or {@code null} if the event represents a new invoice request.
     *
     * @return the order ID or {@code null}
     */
    public Long getOrderId()
    {
        return orderId;
    }

    /**
     * Returns the invoice ID or {@code null} if the event represents a new invoice request.
     *
     * @return the invoice ID or {@code null}
     */
    public Long getInvoiceId()
    {
        return invoiceId;
    }
}
