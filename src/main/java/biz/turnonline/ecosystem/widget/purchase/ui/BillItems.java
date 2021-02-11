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
import biz.turnonline.ecosystem.widget.shared.rest.bill.Item;
import biz.turnonline.ecosystem.widget.shared.ui.Repeater;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillItems
        extends Repeater<Item>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private boolean readOnly;

    private String currency;

    public BillItems()
    {
        header( messages.labelItemName(), "40%" );
        header( messages.labelVat(), "15%" );
        header( messages.labelPriceExcludingVat(), "17%" );
        header( messages.labelVatAmount(), "17%" );
        header( messages.labelSum(), "11%" );
        header( "", "5%" );
    }

    @Override
    protected Widget newItem()
    {
        return new BillItemRow( readOnly, currency );
    }

    private void header( String header, String width )
    {
        Label label = new Label( header );
        label.getElement().getStyle().setOverflow( Style.Overflow.AUTO );

        addHeader( label, width ).setPaddingLeft( 0 );
    }

    public void setValue( @Nullable List<Item> value, @Nullable String currency )
    {
        this.currency = currency;
        super.setValue( value );
    }

    public void setCurrency( @Nullable String currency )
    {
        this.currency = currency;

        getTbody().forEach( widget -> {
            BillItemRow row = ( BillItemRow ) widget;
            row.setCurrency( currency );
        } );
    }

    public void setReadOnly( boolean readOnly )
    {
        this.readOnly = readOnly;

        getTbody().forEach( widget -> {
            BillItemRow row = ( BillItemRow ) widget;
            row.setReadOnly( readOnly );
        } );
    }
}
