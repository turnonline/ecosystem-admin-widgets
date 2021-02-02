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

import biz.turnonline.ecosystem.widget.shared.rest.payment.Operation;
import biz.turnonline.ecosystem.widget.shared.ui.StaticCodeBook;
import biz.turnonline.ecosystem.widget.shared.ui.StaticCodeBookListBox;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Operation} combobox
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class CategoryFilterOperationComboBox
        extends StaticCodeBookListBox
{
    private static List<StaticCodeBook> types = new ArrayList<>();

    static
    {
        types.add( new StaticCodeBook( Operation.EQ.name(), "=" ) );
        types.add( new StaticCodeBook( Operation.LT.name(), "<" ) );
        types.add( new StaticCodeBook( Operation.LTE.name(), "<=" ) );
        types.add( new StaticCodeBook( Operation.GT.name(), ">" ) );
        types.add( new StaticCodeBook( Operation.GTE.name(), ">=" ) );
        types.add( new StaticCodeBook( Operation.REGEXP.name(), "=~" ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return types;
    }

    @Override
    protected String defaultValue()
    {
        return Operation.EQ.name();
    }
}
