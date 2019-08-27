package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nullable;

/**
 * Represents a request to list all orders.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrderListEvent
        extends GwtEvent<OrderListEventHandler>
{
    public static Type<OrderListEventHandler> TYPE = new Type<>();

    private Order from;

    public OrderListEvent( @Nullable Order from )
    {
        this.from = from;
    }

    public Type<OrderListEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public String getScrollspy()
    {
        return Orders.getScrollspy( from );
    }

    protected void dispatch( OrderListEventHandler handler )
    {
        handler.onBack( this );
    }
}
