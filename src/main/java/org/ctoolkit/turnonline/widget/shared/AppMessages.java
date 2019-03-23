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

package org.ctoolkit.turnonline.widget.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public interface AppMessages
        extends Messages
{
    public static final AppMessages INSTANCE = GWT.create( AppMessages.class );

    // labels

    @Key( value = "label.contacts")
    String labelContacts();

    @Key( value = "label.products")
    String labelProducts();

    @Key( value = "label.new")
    String labelNew();

    @Key( value = "label.edit")
    String labelEdit();

    @Key( value = "label.delete")
    String labelDelete();

    // tooltips

    @Key( value = "tooltip.new.contact")
    String tooltipNewContact();

    @Key( value = "tooltip.edit.contact")
    String tooltipEditContact();

    @Key( value = "tooltip.delete.contact")
    String tooltipDeleteContact();
}
