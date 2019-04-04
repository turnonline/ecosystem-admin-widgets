package biz.turnonline.ecosystem.widget.contact.ui;

import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import biz.turnonline.ecosystem.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnName
        extends NotSafeHtmlColumn<ContactCard>
{
    @Override
    public String getValue( ContactCard object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getBusinessName() != null && !"".equals( object.getBusinessName() ) )
        {
            sb.append( "<b>" );
            sb.append( object.getBusinessName() );
            sb.append( "</b>" );

            sb.append( " " );
            sb.append( "<span class='white-text badge green' style='position:relative;right:0;top:-1px;font-size:0.9em;border-radius:3px;'>" );
            sb.append( object.getCompanyId() );
            sb.append( "</span>" );

            sb.append( "<br/>" );
        }

        if ( object.getFirstName() != null || object.getLastName() != null )
        {
            if ( object.getPrefix() != null )
            {
                sb.append( object.getPrefix() );
                sb.append( " " );
            }
            sb.append( object.getFirstName() );
            sb.append( " " );
            sb.append( object.getLastName() );

            if ( object.getSuffix() != null )
            {
                sb.append( " " );
                sb.append( object.getSuffix() );
            }
        }

        return sb.toString();
    }
}
