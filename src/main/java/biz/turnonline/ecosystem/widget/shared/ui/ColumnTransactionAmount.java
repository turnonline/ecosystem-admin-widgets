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

import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import com.google.gwt.text.client.DoubleRenderer;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionAmount
        extends NotSafeHtmlColumn<Transaction>
{
    @Override
    public String getValue( Transaction object )
    {
        Double amount = object.getAmount();
        String currency = object.getCurrency();
        boolean credit = object.getCredit() != null && object.getCredit();
        String color = credit ? "green-text" : "red-text";

        StringBuilder sb = new StringBuilder();
        sb.append( "<div class='" ).append( color ).append( "'>" );
        sb.append( credit ? "+" : "-" ).append( " " );
        sb.append( DoubleRenderer.instance().render( amount ) );
        sb.append( " " ).append( currency );
        sb.append( "</div>" );

        return sb.toString();
    }
}
