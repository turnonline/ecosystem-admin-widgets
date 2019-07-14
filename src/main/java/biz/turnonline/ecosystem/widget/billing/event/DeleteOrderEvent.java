package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteOrderEvent
        extends GwtEvent<DeleteOrderEventHandler>
{
    public static Type<DeleteOrderEventHandler> TYPE = new Type<DeleteOrderEventHandler>();

    private final List<Order> orders;

    private final boolean redirectToList;

    public DeleteOrderEvent( List<Order> orders )
    {
        this.orders = orders;
        this.redirectToList = false;
    }

    public DeleteOrderEvent( @Nonnull Order order )
    {
        List<Order> orders = new ArrayList<>();
        orders.add( checkNotNull( order, "Order cannot be null" ) );

        this.orders = orders;
        this.redirectToList = true;
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

    public boolean isRedirectToList()
    {
        return redirectToList;
    }
}
