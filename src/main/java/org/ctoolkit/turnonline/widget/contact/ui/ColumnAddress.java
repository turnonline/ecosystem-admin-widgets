package org.ctoolkit.turnonline.widget.contact.ui;

import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;
import org.ctoolkit.turnonline.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnAddress
        extends NotSafeHtmlColumn<ContactCard>
{
    @Override
    public String getValue( ContactCard object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getStreet() != null )
        {
            sb.append( "<b>" );
            sb.append( object.getStreet() );
            sb.append( "</b>" );
        }

        if ( appendComma( object ) )
        {
            sb.append( "<br/>" );
        }

        if ( object.getPostcode() != null )
        {
            sb.append( formatPostcode( object.getPostcode() ) );
        }

        if ( object.getCity() != null )
        {
            sb.append( " " );
            sb.append( object.getCity() );
        }

        if ( object.getCountry() != null )
        {
            sb.append( " <br/>" );
            sb.append( "<i class='grey-text'>" );
            sb.append( object.getCountry() );
            sb.append( "</i>" );
        }

        return sb.toString();
    }

    private boolean appendComma( ContactCard object )
    {
        return object.getCity() != null || object.getPostcode() != null || object.getCountry() != null;
    }

    private String formatPostcode( String postcode )
    {
        return postcode.length() == 5 ? postcode.substring( 0, 2 ) + " " + postcode.substring( 2 ) : postcode;
    }
}
