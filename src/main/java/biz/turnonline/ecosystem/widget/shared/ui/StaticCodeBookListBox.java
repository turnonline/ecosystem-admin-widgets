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

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public abstract class StaticCodeBookListBox
        extends CodeBookComboBox<StaticCodeBook>
{
    public StaticCodeBookListBox()
    {
        super( StaticCodeBook.class );
        setHideSearch( true );
        setMultiple( false );
    }

    protected abstract List<StaticCodeBook> values();

    protected void initialize( String code )
    {
        setItems( values() );
        itemsLoaded();
        setSingleValueByCode( code );
    }
}
