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

import biz.turnonline.ecosystem.widget.shared.rest.CodeBook;
import biz.turnonline.ecosystem.widget.shared.rest.CodeBookRestFacade;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.html.Option;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
class CodeBookComboBox<T extends CodeBook>
        extends MaterialComboBox<T>
{
    private final Class<T> clazz;

    private boolean itemsLoaded = false;

    CodeBookComboBox( Class<T> clazz )
    {
        setKeyFactory( CodeBook::getCode );
        this.clazz = clazz;
    }

    @Override
    protected Option buildOption( String text, T value )
    {
        Option option = super.buildOption( text, value );
        option.setText( value.getLabel() );
        return option;
    }

    public String getSingleValueByCode()
    {
        return getSingleValue() != null ? getSingleValue().getCode() : null;
    }

    public void setSingleValueByCode( String code )
    {
        if ( itemsLoaded )
        {
            if ( code == null )
            {
                int index = getIndexByString( defaultValue() );
                if ( index > 0 )
                {
                    setSelectedIndex( index );
                }
            }
            else
            {
                int index = getIndexByString( code );
                if ( index > 0 )
                {
                    setSelectedIndex( index );
                }
            }
        }
        else
        {
            initialize( code );
        }
    }

    protected void initialize( String code )
    {
        CodeBookRestFacade.getCodeBook( clazz, response -> {
            setItems( response.getItems() );

            if ( !response.getItems().isEmpty() )
            {
                itemsLoaded();
                setSingleValueByCode( code );
            }
        } );
    }

    void itemsLoaded()
    {
        this.itemsLoaded = true;
    }

    protected String defaultValue()
    {
        return null;
    }
}
