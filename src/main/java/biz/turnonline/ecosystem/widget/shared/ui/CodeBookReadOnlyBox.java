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
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CodeBookReadOnlyBox<T extends CodeBook>
        extends InlineLabel
{
    private String code;

    public CodeBookReadOnlyBox( String code, Class<T> clazz )
    {
        super( code );
        this.code = code;

        CodeBookRestFacade.getCodeBook( clazz, response -> {
            if ( !response.getItems().isEmpty() )
            {
                response.getItems()
                        .stream()
                        .filter( t -> t.getCode().equals( CodeBookReadOnlyBox.this.code ) )
                        .findFirst()
                        .ifPresent( codeBook -> setText( codeBook.getLabel() ) );
            }
        } );
    }
}
