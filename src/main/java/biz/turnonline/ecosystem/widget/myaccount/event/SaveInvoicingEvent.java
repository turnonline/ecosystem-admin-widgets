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

package biz.turnonline.ecosystem.widget.myaccount.event;

import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfig;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Invoicing save event with {@link InvoicingConfig} payload.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class SaveInvoicingEvent
        extends GwtEvent<SaveInvoicingEventHandler>
{
    public static Type<SaveInvoicingEventHandler> TYPE = new Type<>();

    private final InvoicingConfig invoicing;

    public SaveInvoicingEvent( @Nonnull InvoicingConfig invoicing )
    {
        this.invoicing = checkNotNull( invoicing, "Invoicing cannot be null" );
    }

    public InvoicingConfig getInvoicing()
    {
        return invoicing;
    }

    public Type<SaveInvoicingEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveInvoicingEventHandler handler )
    {
        handler.onSaveInvoicing( this );
    }
}
