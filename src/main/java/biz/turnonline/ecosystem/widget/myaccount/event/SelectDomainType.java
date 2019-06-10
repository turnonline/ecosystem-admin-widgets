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

import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Domain type selection event.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class SelectDomainType
        extends GwtEvent<SelectDomainTypeHandler>
{
    public static Type<SelectDomainTypeHandler> TYPE = new Type<>();

    private final DT type;

    public SelectDomainType( @Nonnull DT type )
    {
        this.type = checkNotNull( type );
    }

    public Type<SelectDomainTypeHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns selected domain type to search.
     *
     * @return selected domain type
     */
    public DT getType()
    {
        return type;
    }

    protected void dispatch( SelectDomainTypeHandler handler )
    {
        handler.onSelectDomainType( this );
    }

    public enum DT
    {
        ROOT,
        PRODUCTS,
        ALL
    }
}
