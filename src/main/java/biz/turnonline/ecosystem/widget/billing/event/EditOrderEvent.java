package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditOrderEvent
        extends GwtEvent<EditOrderEventHandler>
{
    public static Type<EditOrderEventHandler> TYPE = new Type<>();

    private Order order;

    public EditOrderEvent()
    {
    }

    public EditOrderEvent( Order order )
    {
        this.order = order;
    }

    public Type<EditOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditOrderEventHandler handler )
    {
        handler.onEditOrder( this );
    }

    /**
     * Returns the order ID or {@code null} if the event represents a new order request.
     *
     * @return the order ID or {@code null}
     */
    public Long getId()
    {
        return order == null ? null : order.getId();
    }
}
