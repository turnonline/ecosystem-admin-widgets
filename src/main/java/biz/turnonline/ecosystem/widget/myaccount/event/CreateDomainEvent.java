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
