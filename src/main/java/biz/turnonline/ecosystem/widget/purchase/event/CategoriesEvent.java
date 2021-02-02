package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CategoriesEvent
        extends GwtEvent<CategoriesEventHandler>
{
    public static Type<CategoriesEventHandler> TYPE = new Type<CategoriesEventHandler>();

    public Type<CategoriesEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( CategoriesEventHandler handler )
    {
        handler.onCategories( this );
    }
}
