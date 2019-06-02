package biz.turnonline.ecosystem.widget.billing.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class OrderBackEvent
        extends GwtEvent<OrderBackEventHandler>
{
    public static Type<OrderBackEventHandler> TYPE = new Type<OrderBackEventHandler>();

    public Type<OrderBackEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( OrderBackEventHandler handler )
    {
        handler.onBack( this );
    }
}
