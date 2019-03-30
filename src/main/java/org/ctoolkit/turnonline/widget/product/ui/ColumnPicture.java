package org.ctoolkit.turnonline.widget.product.ui;

import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.ProductPicture;
import org.ctoolkit.turnonline.widget.shared.ui.NotSafeHtmlColumn;

import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnPicture
        extends NotSafeHtmlColumn<Product>
{
    @Override
    public String getValue( Product object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getPublishing() != null && !object.getPublishing().getPictures().isEmpty() )
        {
            List<ProductPicture> pictures = object.getPublishing().getPictures();
            pictures.sort( Comparator.comparing( ProductPicture::getOrder ) );

            sb.append( createImage( pictures.get( 0 ).getServingUrl() ) );
        }

        return sb.toString();
    }

    private String createImage( String source )
    {
        return "<img class='responsive-img blue-text' src='" + source + "' width='40' style='margin: 10px 0;'>";
    }
}
