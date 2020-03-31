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

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a notification that value for invoice status {@link Invoice#getStatus()} has changed.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoiceStatusChangeEvent
        extends GwtEvent<InvoiceStatusChangeEventHandler>
{
    public static Type<InvoiceStatusChangeEventHandler> TYPE = new Type<>();

    private final Invoice.Status originStatus;

    private final Invoice.Status status;

    private final Long orderId;

    private final Long invoiceId;

    private String email;

    public InvoiceStatusChangeEvent( @Nonnull Invoice.Status originStatus,
                                     @Nonnull Invoice.Status status,
                                     @Nonnull Long orderId,
                                     @Nonnull Long invoiceId )
    {
        this.originStatus = checkNotNull( status, "Invoice origin status can't be null" );
        this.status = checkNotNull( status, "Invoice status can't be null" );
        this.orderId = checkNotNull( orderId, "Order ID can't be null" );
        this.invoiceId = checkNotNull( invoiceId, "Invoice ID can't be null" );
    }

    public Type<InvoiceStatusChangeEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the status of the invoice right before has been changed.
     *
     * @return the origin invoice status
     */
    public Invoice.Status getOriginStatus()
    {
        return originStatus;
    }

    /**
     * Returns the current invoice status.
     *
     * @return the current invoice status
     */
    public Invoice.Status getInvoiceStatus()
    {
        return status;
    }

    /**
     * Returns the identification of the order as a parent of the invoice that has changed its status.
     *
     * @return the order ID
     */
    public Long getOrderId()
    {
        return orderId;
    }

    /**
     * Returns the identification of the invoice that has changed its status.
     *
     * @return the invoice ID
     */
    public Long getInvoiceId()
    {
        return invoiceId;
    }

    /**
     * Returns the target email address where the invoice to be sent.
     *
     * @return the target email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the target email address
     *
     * @param email target email address to be set
     */
    public void setEmail( String email )
    {
        this.email = email;
    }

    protected void dispatch( InvoiceStatusChangeEventHandler handler )
    {
        handler.onInvoiceStatusChange( this );
    }
}
