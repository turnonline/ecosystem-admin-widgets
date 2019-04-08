package biz.turnonline.ecosystem.widget.order.ui;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.OrderStatus;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnStatus
        extends NotSafeHtmlColumn<Order>
{
    private static Map<String, String> colorMap = new HashMap<>();

    static
    {
        colorMap.put( OrderStatus.TRIALING.name(), "yellow" );
        colorMap.put( OrderStatus.ACTIVE.name(), "green" );
        colorMap.put( OrderStatus.SUSPENDED.name(), "orange" );
        colorMap.put( OrderStatus.ISSUE.name(), "red" );
        colorMap.put( OrderStatus.FINISHED.name(), "blue" );
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
