package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;
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
