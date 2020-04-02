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

package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.ui.DynamicTabs;
import gwt.material.design.client.constants.IconType;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class EditInvoiceTabs
        extends DynamicTabs
{
    public EditInvoiceTabs()
    {
        super();

        add( newTabItem( messages.labelDetail(), "tabDetail", IconType.VISIBILITY ) );
        add( newTabItem( messages.labelItems(), "tabItems", IconType.REORDER ) );
        add( newTabItem( messages.labelCustomer(), "tabCustomer", IconType.PERM_IDENTITY ) );
        add( newTabItem( messages.labelTransactions(), "tabTransactions", IconType.IMPORT_EXPORT ) );
    }
}
