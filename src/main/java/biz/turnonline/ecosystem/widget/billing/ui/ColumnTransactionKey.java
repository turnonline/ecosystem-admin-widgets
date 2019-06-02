package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionKey
        extends NotSafeHtmlColumn<Transaction>
{
    @Override
    public String getValue( Transaction object )
    {
        return object.getKey();
    }
}
