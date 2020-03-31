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
 * Represents a request to delete specified invoice.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DeleteInvoiceEvent
        extends GwtEvent<DeleteInvoiceEventHandler>
{
    public static Type<DeleteInvoiceEventHandler> TYPE = new Type<DeleteInvoiceEventHandler>();

    private final Long orderId;

    private final Long invoiceId;

    private final String invoiceNumber;

    public DeleteInvoiceEvent( @Nonnull Long orderId, @Nonnull Long invoiceId, @Nonnull String invoiceNumber )
    {
        this.orderId = checkNotNull( orderId, "Order ID can't be null" );
        this.invoiceId = checkNotNull( invoiceId, "Invoice ID can't be null" );
        this.invoiceNumber = checkNotNull( invoiceNumber, "Invoice number can't be null" );
    }

    public Type<DeleteInvoiceEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteInvoiceEventHandler handler )
    {
        handler.onDeleteInvoice( this );
    }

    /**
     * Returns the order identification.
     *
     * @return the order ID
     */
    public Long getOrderId()
    {
        return orderId;
    }

    /**
     * Returns the invoice identification.
     *
     * @return the invoice ID
     */
    public Long getInvoiceId()
    {
        return invoiceId;
    }

    /**
     * Returns the invoice number.
     *
     * @return the invoice number
     */
    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }
}
