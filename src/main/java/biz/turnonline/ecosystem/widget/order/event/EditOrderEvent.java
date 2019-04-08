package biz.turnonline.ecosystem.widget.order.event;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditOrderEvent
        extends GwtEvent<EditOrderEventHandler>
{
    public static Type<EditOrderEventHandler> TYPE = new Type<EditOrderEventHandler>();

    private Order order;

    public EditOrderEvent() {
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
        handler.onEditContact( this );
    }

    public Order getOrder()
    {
        return order;
    }
}
