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
