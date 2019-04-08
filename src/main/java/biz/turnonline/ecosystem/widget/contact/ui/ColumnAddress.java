package biz.turnonline.ecosystem.widget.contact.ui;

import biz.turnonline.ecosystem.widget.shared.rest.CodeBookRestFacade;
import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.Country;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

import static biz.turnonline.ecosystem.widget.shared.util.Formatter.formatPostcode;

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
        sb.append( "<div style='padding: 10px 0;'>" );
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
            Country country = CodeBookRestFacade.getCodeBookValue( Country.class, object.getCountry() );

            sb.append( " <br/>" );
            sb.append( "<i class='grey-text'>" );
            sb.append( country != null ? country.getLabel() : object.getCountry() );
            sb.append( "</i>" );
        }

        sb.append( "</div>" );
        return sb.toString();
    }

    private boolean appendComma( ContactCard object )
    {
        return object.getCity() != null || object.getPostcode() != null || object.getCountry() != null;
    }
}
