package biz.turnonline.ecosystem.widget.order.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BackEvent
        extends GwtEvent<BackEventHandler>
{
    public static Type<BackEventHandler> TYPE = new Type<BackEventHandler>();

    public Type<BackEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( BackEventHandler handler )
    {
        handler.onBack( this );
    }
}
