package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;
import com.google.gwt.text.client.DoubleRenderer;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnInvoicePrice
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