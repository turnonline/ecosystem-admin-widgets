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

package biz.turnonline.ecosystem.widget.shared.event;

import com.google.gwt.event.shared.GwtEvent;
import org.fusesource.restygwt.client.ServiceRoots;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_STORAGE;
import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to download PDF of the specified invoice.
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

    public DownloadInvoiceEvent( @Nonnull Long orderId, @Nonnull Long invoiceId, @Nonnull String pin )
    {
        this.orderId = checkNotNull( orderId, "Order ID can't be null" );
        this.invoiceId = checkNotNull( invoiceId, "Invoice ID can't be null" );
        this.pin = checkNotNull( pin, "Invoice PIN can't be null" );
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
                + getPin();
    }
}
