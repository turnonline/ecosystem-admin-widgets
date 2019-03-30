package org.ctoolkit.turnonline.widget.product.ui;

import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.ctoolkit.turnonline.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnName
        extends NotSafeHtmlColumn<Product>
{
    @Override
    public String getValue( Product object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getItemName() != null )
        {
            sb.append( object.getItemName() );
        }

        return sb.toString();
    }
}
