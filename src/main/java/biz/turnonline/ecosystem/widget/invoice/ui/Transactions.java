package biz.turnonline.ecosystem.widget.invoice.ui;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import com.google.gwt.dom.client.Style;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;

import java.util.ArrayList;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Transactions
        extends MaterialDataTable<Transaction>
        implements HasModel<Invoice>
{
    public Transactions()
    {
        getScaffolding().getTopPanel().removeFromParent();
        getScaffolding().getTableBody().getElement().getStyle().setHeight( 100, Style.Unit.PCT );
        ( ( AbstractDataTable.DefaultTableScaffolding ) getScaffolding() ).getXScrollPanel().removeFromParent();

        ColumnTransactionKey key = new ColumnTransactionKey();
        key.setWidth( "40%" );

        ColumnTransactionPaymentMethod paymentMethod = new ColumnTransactionPaymentMethod();
        paymentMethod.setWidth( "30%" );

        ColumnTransactionAmount amount = new ColumnTransactionAmount();
        amount.setWidth( "30%" );

        addColumn( key, "Key" ); // TODO: localize
        addColumn( paymentMethod, "Method" ); // TODO: localize
        addColumn( amount, "Amount" ); // TODO: localize
    }

    @Override
    public void bind( Invoice invoice )
    {
    }

    @Override
    public void fill( Invoice invoice )
    {
        setRowData( 0, invoice.getPayment() != null && invoice.getPayment().getTransactions() != null ? invoice.getPayment().getTransactions() : new ArrayList<>() );
    }
}
