package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a request to show list of purchase orders.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BackEvent
        extends GwtEvent<BackEventHandler>
{
    public static Type<BackEventHandler> TYPE = new Type<>();

    public Type<BackEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( BackEventHandler handler )
    {
        handler.onBack( this );
    }
}
