package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;
import gwt.material.design.client.constants.IconType;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnContactContacts<T extends Contact>
        extends NotSafeHtmlColumn<T>
{
    @Override
    public String getValue( T object )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "<div style='padding: 10px 0;'>" );
        if ( object.getContactEmail() != null && !"".equals( object.getContactEmail() ) )
        {
            constructContact( object.getContactEmail(), IconType.EMAIL, sb );
            sb.append( "<br/>" );
        }

        if ( object.getContactPhone() != null && !"".equals( object.getContactPhone() ) )
        {
            constructContact( object.getContactPhone(), IconType.PHONE, sb );
        }
        sb.append( "</div>" );
        return sb.toString();
    }

    private void constructContact( String contact, IconType icon, StringBuilder sb )
    {
        sb.append( "<i class='orange-text material-icons' style='position:relative;top: 2px;'>" ).append( icon.getCssName() ).append( "</i>" );
        sb.append( "<span style='position:relative;top: -6px;padding-left: 5px;'>" );
        sb.append( contact );
        sb.append( "</span>" );
    }
}
