package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveOrderEvent
        extends GwtEvent<SaveOrderEventHandler>
{
    public static Type<SaveOrderEventHandler> TYPE = new Type<SaveOrderEventHandler>();

    private final Order order;

    public SaveOrderEvent( Order order )
    {
        this.order = order;
    }

    public Type<SaveOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveOrderEventHandler handler )
    {
        handler.onSaveOrder( this );
    }

    public Order getOrder()
    {
        return order;
    }
}
