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
import org.fusesource.restygwt.client.ServiceRoots;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_STORAGE;
import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to download PDF of the specified receipt.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DownloadReceiptEvent
        extends GwtEvent<DownloadReceiptEventHandler>
{
    public static Type<DownloadReceiptEventHandler> TYPE = new Type<>();

    private final Long receiptId;

    private final String pin;

    public DownloadReceiptEvent( @Nonnull Long receiptId, @Nonnull String pin )
    {
        this.receiptId = checkNotNull( receiptId, "Receipt ID can't be null" );
        this.pin = checkNotNull( pin, "Receipt PIN can't be null" );
    }

    public Type<DownloadReceiptEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DownloadReceiptEventHandler handler )
    {
        handler.onDownloadReceipt( this );
    }

    /**
     * Returns the receipt identification.
     *
     * @return the receipt ID
     */
    public Long getReceiptId()
    {
        return receiptId;
    }

    /**
     * Returns the receipt PIN.
     *
     * @return the receipt PIN
     */
    public String getPin()
    {
        return pin;
    }

    /**
     * The final URL will look like:
     * '/api/billing/v1/pdf/purchases/expenses/{expense_id}/{pin}'
     */
    public String downloadReceiptUrl()
    {
        return ServiceRoots.get( PRODUCT_BILLING_STORAGE )
                + "pdf/purchases/expenses/"
                + getReceiptId()
                + "/"
                + getPin();
    }
}
