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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.ui.html.Span;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnBadge
        extends Span
{
    public ColumnBadge( String text, Color textColor, Color bgColor )
    {
        super( Document.get().createSpanElement(), CssName.BADGE );

        setText( text );
        setTextColor( textColor );
        setBackgroundColor( bgColor );

        setLayoutPosition( Style.Position.RELATIVE );
        setRight( 0 );
        setTop( -1 );
        setFontSize( "0.9em" );
        setBorderRadius( "3px" );
    }
}
