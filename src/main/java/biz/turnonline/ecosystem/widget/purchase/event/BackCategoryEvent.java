package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BackCategoryEvent
        extends GwtEvent<BackCategoryEventHandler>
{
    public static Type<BackCategoryEventHandler> TYPE = new Type<BackCategoryEventHandler>();

    public Type<BackCategoryEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( BackCategoryEventHandler handler )
    {
        handler.onBack( this );
    }
}
