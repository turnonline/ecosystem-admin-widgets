package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialChip;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CategoryChip
        extends MaterialChip
{
    public CategoryChip( Category category )
    {
        setIconType( IconType.LABEL );
        setIconPosition( IconPosition.LEFT );
        setText( category.getName() );
        setTextColor( Color.WHITE );
        getElement().getStyle().setBackgroundColor( category.getColor() );
    }
}
