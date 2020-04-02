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

package biz.turnonline.ecosystem.widget.contact.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTableLoadResult;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ContactsDataSource
        extends RefreshableDataSource<ContactCard>
{
    private AppEventBus eventBus;

    public ContactsDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<ContactCard> loadConfig, LoadCallback<ContactCard> callback )
    {
        super.load( loadConfig, callback );

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.account().getContacts( eventBus.config().getLoginId(),
                offset,
                limit,
                null,
                response -> callback.onSuccess( new SmartTableLoadResult<>( response.getItems(), loadConfig ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
