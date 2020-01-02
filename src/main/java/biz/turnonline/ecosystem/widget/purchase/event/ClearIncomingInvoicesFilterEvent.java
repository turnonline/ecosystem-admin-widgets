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

package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a request to clear incoming invoice list filter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ClearIncomingInvoicesFilterEvent
        extends GwtEvent<ClearIncomingInvoicesFilterEventHandler>
{
    public static Type<ClearIncomingInvoicesFilterEventHandler> TYPE = new Type<>();

    public Type<ClearIncomingInvoicesFilterEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( ClearIncomingInvoicesFilterEventHandler handler )
    {
        handler.onClearIncomingInvoicesFilter( this );
    }
}