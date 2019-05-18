package biz.turnonline.ecosystem.widget.order.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;
import com.google.gwt.text.client.DoubleRenderer;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnPrice
        extends NotSafeHtmlColumn<Order>
{
    @Override
    public String getValue( Order object )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "<span class='white-text badge " )
                .append( periodicityColor( object.getPeriodicity() ) )
                .append( "' style='position:relative;right:0px;left:top:-1px;font-size:0.9em;border-radius:3px;'>" );
        sb.append( object.getPeriodicity() );
        sb.append( "</span>" );

        sb.append( "<br/>" );

        sb.append( DoubleRenderer.instance().render( object.getTotalPrice() ) );
        sb.append( "<br/>" );
        sb.append( DoubleRenderer.instance().render( object.getTotalPriceExclVat() ) );
        sb.append( "<br/>" );
        sb.append( DoubleRenderer.instance().render( object.getTotalVatBase() ) ).append( " %" );

        return sb.toString();
    }

    private String periodicityColor( String periodicity )
    {
        if ( periodicity.equals( OrderPeriodicity.MANUALLY.name() ) )
        {
            return "grey";
        }

        return "cyan";
    }
}
