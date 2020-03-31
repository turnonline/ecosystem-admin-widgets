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

import biz.turnonline.ecosystem.widget.shared.Configuration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.constants.CurrencyCodeMapConstants;
import gwt.material.design.addins.client.combobox.MaterialComboBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CurrencyComboBox
        extends MaterialComboBox<String>
{
    private static final CurrencyCodeMapConstants currencies = GWT.create( CurrencyCodeMapConstants.class );

    private static List<String> currencyList;

    static
    {
        currencyList = new ArrayList<>( currencies.currencyMap().keySet() );
        Collections.sort( currencyList );
    }

    public CurrencyComboBox()
    {
        setItems( currencyList );
    }


    @Override
    public void setSingleValue( String value )
    {
        if ( value == null || "".equals( value ) )
        {
            value = defaultValue();
        }

        super.setSingleValue( value );
    }

    protected String defaultValue()
    {
        return Configuration.get().getCurrency();
    }
}
