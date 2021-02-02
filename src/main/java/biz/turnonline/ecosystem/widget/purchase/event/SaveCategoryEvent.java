package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class SaveCategoryEvent
        extends GwtEvent<SaveCategoryEventHandler>
{
    public static Type<SaveCategoryEventHandler> TYPE = new Type<SaveCategoryEventHandler>();

    private final Category category;

    public SaveCategoryEvent( Category category )
    {
        this.category = category;
    }

    public Type<SaveCategoryEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveCategoryEventHandler handler )
    {
        handler.onSave( this );
    }

    public Category getCategory()
    {
        return category;
    }
}
