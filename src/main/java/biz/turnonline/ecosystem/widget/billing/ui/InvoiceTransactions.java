package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import com.google.gwt.dom.client.Style;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;

import java.util.ArrayList;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoiceTransactions
        extends MaterialDataTable<Transaction>
        implements HasModel<Invoice>
{
    public InvoiceTransactions()
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
