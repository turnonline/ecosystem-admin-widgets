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

package biz.turnonline.ecosystem.widget.shared.event;

import com.google.gwt.event.shared.GwtEvent;
import org.fusesource.restygwt.client.ServiceRoots;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_STORAGE;
import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to download PDF of the specified (incoming) invoice.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DownloadInvoiceEvent
        extends GwtEvent<DownloadInvoiceEventHandler>
{
    public static Type<DownloadInvoiceEventHandler> TYPE = new Type<>();

    private final Long orderId;

    private final Long invoiceId;

    private final String pin;

    private final boolean incoming;

    public DownloadInvoiceEvent( @Nonnull Long orderId, @Nonnull Long invoiceId, @Nonnull String pin )
    {
        this( orderId, invoiceId, pin, false );
    }

    public DownloadInvoiceEvent( @Nonnull Long orderId, @Nonnull Long invoiceId, @Nonnull String pin, boolean incoming )
    {
        this.orderId = checkNotNull( orderId, "Order ID can't be null" );
        this.invoiceId = checkNotNull( invoiceId, "Invoice ID can't be null" );
        this.pin = checkNotNull( pin, "Invoice PIN can't be null" );
        this.incoming = incoming;
    }

    public Type<DownloadInvoiceEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DownloadInvoiceEventHandler handler )
    {
        handler.onDownloadInvoice( this );
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
     * Returns the invoice PIN.
     *
     * @return the invoice PIN
     */
    public String getPin()
    {
        return pin;
    }

    public String downloadInvoiceUrl()
    {
        return ServiceRoots.get( PRODUCT_BILLING_STORAGE )
                + "pdf/orders/"
                + getOrderId()
                + "/invoices/"
                + getInvoiceId()
                + "/"
                + getPin()
                + ( incoming ? "?incoming=true" : "" );
    }
}
