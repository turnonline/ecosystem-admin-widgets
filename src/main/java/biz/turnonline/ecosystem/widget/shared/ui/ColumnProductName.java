package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnProductName
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

        if ( object.getPublishing() != null
                && object.getPublishing().getComingSoon() != null
                && object.getPublishing().getComingSoon() )
        {
            sb.append( "<span class='white-text badge green' style='position:relative;right:0;left:5px;top:-1px;font-size:0.9em;border-radius:3px;'>" );
            sb.append( "Coming soon" );
            sb.append( "</span>" );
        }

        if ( object.getSnippet() != null )
        {
            sb.append( "<br/>" );
            sb.append( "<i class='grey-text'>" );
            sb.append( object.getSnippet() );
            sb.append( "</i>" );
        }

        return sb.toString();
    }
}
