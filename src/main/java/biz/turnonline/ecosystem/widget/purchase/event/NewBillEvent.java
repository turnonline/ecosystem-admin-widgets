/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class NewBillEvent
        extends GwtEvent<NewBillEventHandler>
{
    public static Type<NewBillEventHandler> TYPE = new Type<>();

    private final Bill bill;

    public NewBillEvent( Bill bill )
    {
        this.bill = bill;
    }

    public Type<NewBillEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( NewBillEventHandler handler )
    {
        handler.onNew( this );
    }

    public Bill getBill()
    {
        return bill;
    }
}
