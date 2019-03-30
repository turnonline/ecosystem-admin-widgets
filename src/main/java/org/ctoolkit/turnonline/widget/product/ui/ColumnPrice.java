package org.ctoolkit.turnonline.widget.product.ui;

import com.google.gwt.text.client.DoubleRenderer;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.ctoolkit.turnonline.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnPrice
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
