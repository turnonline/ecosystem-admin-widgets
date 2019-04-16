package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnContactType<T extends Contact>
        extends WidgetColumn<T, MaterialIcon>
{
    @Override
    public MaterialIcon getValue( T value )
    {
        MaterialIcon icon = new MaterialIcon();
        icon.setIconType( value.getCompany() ? IconType.BUSINESS : IconType.PERSON );
        icon.setTextColor( value.getCompany() ? Color.GREEN : Color.AMBER );
        return icon;
    }
}
