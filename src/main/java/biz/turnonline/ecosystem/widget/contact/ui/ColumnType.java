package biz.turnonline.ecosystem.widget.contact.ui;

import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnType
        extends WidgetColumn<ContactCard, MaterialIcon>
{
    @Override
    public MaterialIcon getValue( ContactCard value )
    {
        MaterialIcon icon = new MaterialIcon();
        icon.setIconType( value.getCompany() ? IconType.BUSINESS : IconType.PERSON );
        icon.setTextColor( value.getCompany() ? Color.GREEN : Color.AMBER );
        return icon;
    }
}
