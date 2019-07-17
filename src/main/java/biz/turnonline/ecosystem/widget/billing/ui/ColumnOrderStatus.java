package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnOrderStatus
        extends NotSafeHtmlColumn<Order>
{
    private static Map<String, String> colorMap = new HashMap<>();

    static
    {
        colorMap.put( Order.Status.TRIALING.name(), "yellow" );
        colorMap.put( Order.Status.ACTIVE.name(), "green" );
        colorMap.put( Order.Status.SUSPENDED.name(), "orange" );
        colorMap.put( Order.Status.ISSUE.name(), "red" );
        colorMap.put( Order.Status.FINISHED.name(), "blue" );
    }

    @Override
    public String getValue( Order object )
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
