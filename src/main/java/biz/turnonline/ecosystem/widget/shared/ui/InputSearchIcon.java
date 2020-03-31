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

import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InputSearchIcon
        extends MaterialIcon
{
    public InputSearchIcon()
    {
        setTextColor( Color.GREY );
        setIconType( IconType.SEARCH );

        Style style = getElement().getStyle();
        style.setPosition( Style.Position.ABSOLUTE );
        style.setMarginTop( 15, Style.Unit.PX );
        style.setRight( 0, Style.Unit.PX );
    }
}
