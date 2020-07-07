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

import com.google.gwt.i18n.client.NumberFormat;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nullable;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class PriceTextBox
        extends MaterialTextBox
{
    private Double price;

    public Double getPrice()
    {
        return price;
    }

    @Override
    public void reset()
    {
        super.reset();
        this.price = null;
    }

    @Override
    public void clear()
    {
        super.clear();
        this.price = null;
    }

    public void setValue( @Nullable Double price, @Nullable String currency )
    {
        setValue( format( price, currency ) );
    }

    private String format( Double price, String currency )
    {
        this.price = price;

        price = price != null ? price : 0D;
        return currency == null ? price.toString() : NumberFormat.getCurrencyFormat( currency ).format( price );
    }
}
