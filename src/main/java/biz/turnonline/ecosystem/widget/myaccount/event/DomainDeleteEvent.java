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
