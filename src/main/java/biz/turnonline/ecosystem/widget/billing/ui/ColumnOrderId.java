package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnOrderId
        extends NotSafeHtmlColumn<Order>
{
    @Override
    public String getValue( Order object )
    {
        StringBuilder sb = new StringBuilder();

        if ( object.getInvoiceType() != null )
        {
            sb.append( "<span class='white-text badge ")
                    .append( invoiceTypeColor( object.getInvoiceType() ))
                    .append( "' style='position:relative;right:0;left:top:-1px;font-size:0.9em;border-radius:3px;'>" );
            sb.append( object.getInvoiceType() );
            sb.append( "</span>" );
            sb.append( "<br/>" );
        }

        sb.append( object.getId() );
        return sb.toString();
    }

    private String invoiceTypeColor( String invoiceType )
    {
        if ( invoiceType.equals( InvoiceType.TAX_DOCUMENT.name() ) )
        {
            return "green";
        }

        return "grey";
    }
}
