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
import java.util.Locale;

/**
 * {@link Locale} combo box as a UI language selector. Only limited list of the supported locale.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class LanguageComboBox
        extends CodeBookComboBox<Language>
{

    public LanguageComboBox()
    {
        super( Language.class );
    }

    @Override
    protected void initialize( String code )
    {
        List<Language> items = new ArrayList<>();
        items.add( new Language( "en", AppMessages.INSTANCE.labelLanguageEn() ) );
        items.add( new Language( "sk", AppMessages.INSTANCE.labelLanguageSk() ) );
        items.add( new Language( "cs", AppMessages.INSTANCE.labelLanguageCs() ) );

        setItems( items );
        itemsLoaded();
        setSingleValueByCode( code );
    }

    @Override
    protected String defaultValue()
    {
        return "en";
    }
}
