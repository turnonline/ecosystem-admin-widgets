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

package biz.turnonline.ecosystem.widget.contact.event;

import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class SaveContactEvent
        extends GwtEvent<SaveContactEventHandler>
{
    public static Type<SaveContactEventHandler> TYPE = new Type<SaveContactEventHandler>();

    private final ContactCard contactCard;

    public SaveContactEvent( ContactCard contactCard )
    {
        this.contactCard = contactCard;
    }

    public Type<SaveContactEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveContactEventHandler handler )
    {
        handler.onSaveContact( this );
    }

    public ContactCard getContactCard()
    {
        return contactCard;
    }
}
