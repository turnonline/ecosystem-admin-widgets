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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.PropertyName;
import biz.turnonline.ecosystem.widget.shared.ui.StaticCodeBook;
import biz.turnonline.ecosystem.widget.shared.ui.StaticCodeBookListBox;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link PropertyName} combobox
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class CategoryFilterPropertyNameComboBox
        extends StaticCodeBookListBox
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static List<StaticCodeBook> types = new ArrayList<>();

    static
    {
        types.add( new StaticCodeBook( PropertyName.NAME.name(), messages.labelPropertyNameName() ) );
        types.add( new StaticCodeBook( PropertyName.AMOUNT.name(), messages.labelPropertyNameAmount() ) );
        types.add( new StaticCodeBook( PropertyName.CURRENCY.name(), messages.labelPropertyNameCurrency() ) );
        types.add( new StaticCodeBook( PropertyName.CREDIT.name(), messages.labelPropertyNameCredit() ) );
        types.add( new StaticCodeBook( PropertyName.COUNTERPARTY_IBAN.name(), messages.labelPropertyNameCounterpartyIban() ) );
        types.add( new StaticCodeBook( PropertyName.REFERENCE.name(), messages.labelPropertyNameReference() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return types;
    }

    @Override
    protected String defaultValue()
    {
        return PropertyName.NAME.name();
    }
}
