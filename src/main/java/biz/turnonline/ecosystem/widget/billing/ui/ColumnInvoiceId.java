package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnInvoiceId
        extends NotSafeHtmlColumn<Invoice>
{
    @Override
    public String getValue( Invoice object )
    {
        StringBuilder sb = new StringBuilder();

        if ( object.getType() != null )
        {
            sb.append( "<span class='white-text badge ")
                    .append( invoiceTypeColor( object.getType() ))
                    .append( "' style='position:relative;right:0;left:top:-1px;font-size:0.9em;border-radius:3px;'>" );
            sb.append( object.getType() );
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
