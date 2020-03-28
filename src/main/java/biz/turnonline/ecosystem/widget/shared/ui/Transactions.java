/*
 * Copyright (c) 2020 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
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
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
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
