package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to delete specified order.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteOrderEvent
        extends GwtEvent<DeleteOrderEventHandler>
{
    public static Type<DeleteOrderEventHandler> TYPE = new Type<DeleteOrderEventHandler>();

    private final Order order;

    public DeleteOrderEvent( @Nonnull Order order )
    {
        this.order = checkNotNull( order, "Order cannot be null" );
    }

    public Type<DeleteOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteOrderEventHandler handler )
    {
        handler.onDeleteOrder( this );
    }

    public Order getOrder()
    {
        return order;
    }
}
