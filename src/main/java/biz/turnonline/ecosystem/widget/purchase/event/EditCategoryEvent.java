package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class EditCategoryEvent
        extends GwtEvent<EditCategoryEventHandler>
{
    public static Type<EditCategoryEventHandler> TYPE = new Type<EditCategoryEventHandler>();

    private Long categoryId;

    public EditCategoryEvent()
    {
    }

    public EditCategoryEvent( Long categoryId )
    {
        this.categoryId = categoryId;
    }

    public Type<EditCategoryEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditCategoryEventHandler handler )
    {
        handler.onEdit( this );
    }

    public Long getCategoryId()
    {
        return categoryId;
    }
}
