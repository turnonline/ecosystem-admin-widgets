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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.TransactionListEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;

import javax.annotation.Nullable;
import java.util.List;

/**
 * List of payment transactions table related to single invoice.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Transactions
        extends Composite
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private final EventBus bus;

    private final MaterialDataTable<Transaction> table;

    private final MaterialEmptyState emptyState;

    private final FlowPanel root;

    public Transactions( EventBus eventBus )
    {
        root = new FlowPanel();
        initWidget( root );

        bus = eventBus;
        table = new MaterialDataTable<>();

        table.getScaffolding().getTopPanel().removeFromParent();
        table.getScaffolding().getTableBody().getElement().getStyle().setHeight( 100, Style.Unit.PCT );
        ( ( AbstractDataTable.DefaultTableScaffolding ) table.getScaffolding() ).getXScrollPanel().removeFromParent();

        ColumnTransactionPaymentMethod paymentMethod = new ColumnTransactionPaymentMethod();
        paymentMethod.width( "15%" );

        ColumnTransactionAmount amount = new ColumnTransactionAmount();
        amount.width( "20%" );

        ColumnTransactionKey key = new ColumnTransactionKey();
        key.width( "25%" );

        ColumnTransactionReference reference = new ColumnTransactionReference();
        reference.width( "40%" );

        table.addColumn( messages.labelPaymentMethod(), paymentMethod );
        table.addColumn( messages.labelAmount(), amount );
        table.addColumn( messages.labelPaymentId(), key );
        table.addColumn( messages.labelPaymentReference(), reference );

        emptyState = new MaterialEmptyState();
        emptyState.setHeight( "40vh" );
        emptyState.setIconType( IconType.IMPORT_EXPORT );
        emptyState.setIconColor( Color.BLUE );
        emptyState.setTitle( messages.labelNoTransactions() );

        // by default empty state on init
        root.add( emptyState );
    }

    /**
     * Fires an event to request list all transactions associated with the invoice.
     *
     * @param orderId   the order identification to get associated transactions
     * @param invoiceId the invoice identification to get associated transactions
     */
    public void initFor( @Nullable Long orderId, @Nullable Long invoiceId )
    {
        if ( orderId != null && invoiceId != null )
        {
            bus.fireEvent( new TransactionListEvent( orderId, invoiceId ) );
        }
    }

    /**
     * Fills the payment transactions to be listed in the table.
     *
     * @param transactions the list of transaction
     */
    public void fill( @Nullable List<Transaction> transactions )
    {
        root.clear();

        if ( transactions == null || transactions.isEmpty() )
        {
            root.add( emptyState );
        }
        else
        {
            root.add( table );
            table.setRowData( 0, transactions );
        }
    }
}
