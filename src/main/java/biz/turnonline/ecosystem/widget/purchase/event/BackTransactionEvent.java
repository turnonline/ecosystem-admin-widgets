package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BackTransactionEvent
        extends GwtEvent<BackTransactionEventHandler>
{
    public static Type<BackTransactionEventHandler> TYPE = new Type<BackTransactionEventHandler>();

    public Type<BackTransactionEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( BackTransactionEventHandler handler )
    {
        handler.onBack( this );
    }
}
