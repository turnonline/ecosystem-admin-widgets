/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.CategoryFilter;
import biz.turnonline.ecosystem.widget.shared.ui.Repeater;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CategoryFilters
        extends Repeater<CategoryFilter>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    public CategoryFilters()
    {
        header( messages.labelName(), "25%" );
        header( messages.labelOperation(), "10%" );
        header( messages.labelPropertyValue(), "60%" );
        header( "", "5%" );
    }

    @Override
    protected Widget newItem()
    {
        return new CategoryFilterRow();
    }

    private void header( String header, String width )
    {
        Label label = new Label( header );
        label.getElement().getStyle().setOverflow( Style.Overflow.AUTO );

        addHeader( label, width ).setPaddingLeft( 0 );
    }

    public void setValue( @Nullable List<CategoryFilter> value, @Nullable String currency )
    {
        super.setValue( value );
    }
}
