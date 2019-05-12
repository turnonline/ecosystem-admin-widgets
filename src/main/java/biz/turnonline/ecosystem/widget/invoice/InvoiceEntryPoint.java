/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.invoice;

import biz.turnonline.ecosystem.widget.invoice.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.DaggerComponent;
import biz.turnonline.ecosystem.widget.shared.DaggerEntryPoint;
import com.google.gwt.place.shared.Place;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class InvoiceEntryPoint
        extends DaggerEntryPoint
{
    public static Place DEFAULT_PLACE = new Invoices();

    @Override
    protected DaggerComponent component()
    {
        return DaggerInvoiceComponent.create();
    }
}
