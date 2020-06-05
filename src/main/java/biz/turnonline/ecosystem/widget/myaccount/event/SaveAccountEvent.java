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

import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Account save event with {@link Account} payload.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class SaveAccountEvent
        extends GwtEvent<SaveAccountEventHandler>
{
    public static Type<SaveAccountEventHandler> TYPE = new Type<>();

    private final Account account;

    private final boolean reloadPage;

    public SaveAccountEvent( @Nonnull Account account, boolean reloadPage )
    {
        this.account = checkNotNull( account, "Account cannot be null" );
        this.reloadPage = reloadPage;
    }

    public Account getAccount()
    {
        return account;
    }

    public boolean isReloadPage()
    {
        return reloadPage;
    }

    public String getLoginId()
    {
        Long id = account.getId();
        return String.valueOf( checkNotNull( id, "Account ID cannot be null" ) );
    }

    public Type<SaveAccountEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveAccountEventHandler handler )
    {
        handler.onSaveAccount( this );
    }
}
