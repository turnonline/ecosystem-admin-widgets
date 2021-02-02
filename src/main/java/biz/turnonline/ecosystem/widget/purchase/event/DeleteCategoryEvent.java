package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DeleteCategoryEvent
        extends GwtEvent<DeleteCategoryEventHandler>
{
    public static Type<DeleteCategoryEventHandler> TYPE = new Type<DeleteCategoryEventHandler>();

    private final Category category;

    public DeleteCategoryEvent( Category category )
    {
        this.category = category;
    }

    public Type<DeleteCategoryEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteCategoryEventHandler handler )
    {
        handler.onDelete( this );
    }

    public Category getCategory()
    {
        return category;
    }
}
