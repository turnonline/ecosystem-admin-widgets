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
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Domain deletion event with {@link Domain} payload.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DomainDeleteEvent
        extends GwtEvent<DomainDeleteEventHandler>
{
    public static Type<DomainDeleteEventHandler> TYPE = new Type<>();

    private final List<String> domainNames;

    public DomainDeleteEvent( @Nonnull List<String> domainNames )
    {
        this.domainNames = checkNotNull( domainNames );
    }

    public Type<DomainDeleteEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the list of unique domain identifiers that needs to be deleted.
     *
     * @return the list of unique domain identifiers
     */
    public List<String> getDomainNames()
    {
        return domainNames;
    }

    protected void dispatch( DomainDeleteEventHandler handler )
    {
        handler.onDomainDelete( this );
    }
}
