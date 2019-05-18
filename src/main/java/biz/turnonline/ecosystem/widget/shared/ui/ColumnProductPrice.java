package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import com.google.gwt.text.client.DoubleRenderer;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnProductPrice
        extends NotSafeHtmlColumn<Product>
{
    @Override
    public String getValue( Product object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getPricing() != null && object.getPricing().getPriceExclVat() != null )
        {
            sb.append( DoubleRenderer.instance().render( object.getPricing().getPriceExclVat() ) );

            if ( object.getPricing().getCurrency() != null )
            {
                sb.append( " " ).append( object.getPricing().getCurrency() );
            }
        }

        return sb.toString();
    }
}
