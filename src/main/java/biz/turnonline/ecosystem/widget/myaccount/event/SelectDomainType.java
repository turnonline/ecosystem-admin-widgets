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
