package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import biz.turnonline.ecosystem.widget.shared.rest.payment.TransactionCategory;
import biz.turnonline.ecosystem.widget.shared.ui.Badge;
import gwt.material.design.client.constants.Color;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CategoryBadge
        extends Badge
{
    public CategoryBadge( String text, String bgColor)
    {
        setText( text );
        setTextColor( Color.WHITE );
        getElement().getStyle().setBackgroundColor( bgColor );
    }

    public CategoryBadge( TransactionCategory category )
    {
        this(category.getName(), category.getColor());
    }

    public CategoryBadge( Category category )
    {
        this(category.getName(), category.getColor());
    }
}
