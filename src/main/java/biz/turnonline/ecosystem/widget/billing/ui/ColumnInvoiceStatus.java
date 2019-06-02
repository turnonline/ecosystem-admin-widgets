package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceStatus;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnInvoiceStatus
        extends NotSafeHtmlColumn<Invoice>
{
    private static Map<String, String> colorMap = new HashMap<>();

    static
    {
        colorMap.put( InvoiceStatus.NEW.name(), "blue" );
        colorMap.put( InvoiceStatus.SENT.name(), "orange" );
        colorMap.put( InvoiceStatus.CANCELED.name(), "red" );
        colorMap.put( InvoiceStatus.PAID.name(), "green" );
    }

    @Override
    public String getValue( Invoice object )
    {
        StringBuilder sb = new StringBuilder();

        if ( object.getStatus() != null )
        {
            sb.append( "<span class='white-text badge " )
                    .append( statusColor( object.getStatus() ) )
                    .append( "' style='position:relative;right:0;left:5px;top:-1px;font-size:0.9em;border-radius:3px;'>" );
            sb.append( object.getStatus() );
            sb.append( "</span>" );
        }

        return sb.toString();
    }

    private String statusColor( String status )
    {
        if ( colorMap.containsKey( status ) )
        {
            return colorMap.get( status );
        }

        return "grey";
    }
}
