package biz.turnonline.ecosystem.widget.order.event;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteOrderEvent
        extends GwtEvent<DeleteOrderEventHandler>
{
    public static Type<DeleteOrderEventHandler> TYPE = new Type<DeleteOrderEventHandler>();

    private final List<Order> orders;

    public DeleteOrderEvent( List<Order> orders )
    {
        this.orders = orders;
    }

    public Type<DeleteOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteOrderEventHandler handler )
    {
        handler.onDeleteOrder( this );
    }

    public List<Order> getOrders()
    {
        return orders;
    }
}
