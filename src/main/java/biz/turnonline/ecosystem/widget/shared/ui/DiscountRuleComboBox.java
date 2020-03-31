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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DiscountRuleComboBox
        extends StaticCodeBookListBox
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static List<StaticCodeBook> rules = new ArrayList<>();

    static
    {
        rules.add( new StaticCodeBook( "DiscountCode", messages.labelDiscountCode() ) );
        rules.add( new StaticCodeBook( "AllChildrenCheckedIn", messages.labelAllChildrenCheckedIn() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return rules;
    }

    @Override
    protected String defaultValue()
    {
        return "DiscountCode";
    }
}
