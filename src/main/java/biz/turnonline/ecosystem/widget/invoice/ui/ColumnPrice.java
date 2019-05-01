package biz.turnonline.ecosystem.widget.invoice.ui;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.OrderPeriodicity;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;
import com.google.gwt.text.client.DoubleRenderer;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnPrice
        extends NotSafeHtmlColumn<Invoice>
{
    @Override
    public String getValue( Invoice object )
    {
        StringBuilder sb = new StringBuilder();
        InvoicePricing pricing = object.getPricing();

        if ( pricing != null )
        {
            sb.append( DoubleRenderer.instance().render( pricing.getTotalPrice() ) );
            sb.append( "<br/>" );
            sb.append( DoubleRenderer.instance().render( pricing.getTotalPriceExclVat() ) );
            sb.append( "<br/>" );
            sb.append( DoubleRenderer.instance().render( pricing.getTotalVatBase() ) ).append( " %" );
        }

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
