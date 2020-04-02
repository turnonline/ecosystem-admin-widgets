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
import com.google.gwt.dom.client.Style;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;

import java.util.ArrayList;
import java.util.List;

/**
 * List of payment transactions table related to single invoice.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Transactions
        extends MaterialDataTable<Transaction>
{
    public Transactions()
    {
        getScaffolding().getTopPanel().removeFromParent();
        getScaffolding().getTableBody().getElement().getStyle().setHeight( 100, Style.Unit.PCT );
        ( ( AbstractDataTable.DefaultTableScaffolding ) getScaffolding() ).getXScrollPanel().removeFromParent();

        ColumnTransactionKey key = new ColumnTransactionKey();
        key.width( "40%" );

        ColumnTransactionPaymentMethod paymentMethod = new ColumnTransactionPaymentMethod();
        paymentMethod.width( "30%" );

        ColumnTransactionAmount amount = new ColumnTransactionAmount();
        amount.width( "30%" );

        addColumn( "Key", key ); // TODO: localize
        addColumn( "Method", paymentMethod ); // TODO: localize
        addColumn( "Amount", amount ); // TODO: localize
    }

    public void fill()
    {
        List<Transaction> transactions = new ArrayList<>();
        setRowData( 0, transactions );
    }
}
