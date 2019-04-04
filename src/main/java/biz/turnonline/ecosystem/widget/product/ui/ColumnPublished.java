package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnPublished
        extends WidgetColumn<Product, MaterialIcon>
{
    @Override
    public MaterialIcon getValue( Product object )
    {
        boolean published = false;
        if ( object.getPublishing() != null && object.getPublishing().getPublished() != null )
        {
            published = object.getPublishing().getPublished();
        }

        MaterialIcon icon = new MaterialIcon();
        icon.setIconType( published ? IconType.LOCK_OPEN : IconType.LOCK );
        icon.setTextColor( published ? Color.GREEN : Color.RED );
        return icon;
    }
}
