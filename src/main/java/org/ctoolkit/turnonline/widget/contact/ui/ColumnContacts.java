package org.ctoolkit.turnonline.widget.contact.ui;

import gwt.material.design.client.constants.IconType;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;
import org.ctoolkit.turnonline.widget.shared.ui.NotSafeHtmlColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnContacts
        extends NotSafeHtmlColumn<ContactCard>
{
    @Override
    public String getValue( ContactCard object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getContactEmail() != null )
        {
            constructContact( object.getContactEmail(), IconType.EMAIL, sb );
            sb.append( "<br/>" );
        }

        if ( object.getContactPhone() != null )
        {
            constructContact( object.getContactPhone(), IconType.PHONE, sb );
        }

        return sb.toString();
    }

    private void constructContact( String contact, IconType icon, StringBuilder sb )
    {
        sb.append( "<i class='blue-text material-icons' style='position:relative;top: 2px;'>" ).append( icon.getCssName() ).append( "</i>" );
        sb.append( "<span style='position:relative;top: -6px;padding-left: 5px;'>" );
        sb.append( contact );
        sb.append( "</span>" );
    }
}
