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

import biz.turnonline.ecosystem.widget.shared.rest.account.Domain;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Domain creation event with {@link Domain} payload.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class CreateDomainEvent
        extends GwtEvent<CreateDomainEventHandler>
{
    public static Type<CreateDomainEventHandler> TYPE = new Type<>();

    private final Domain domain;

    public CreateDomainEvent( @Nonnull Domain domain )
    {
        this.domain = checkNotNull( domain );
    }

    public Type<CreateDomainEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns a domain that needs to be created.
     *
     * @return the domain to be created
     */
    public Domain getDomain()
    {
        return domain;
    }

    protected void dispatch( CreateDomainEventHandler handler )
    {
        handler.onCreateDomain( this );
    }
}
