package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrders;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nullable;

/**
 * Represents a request to show list of purchase orders.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderListEvent
        extends GwtEvent<PurchaseOrderListEventHandler>
{
    public static Type<PurchaseOrderListEventHandler> TYPE = new Type<>();

    private PurchaseOrder from;

    public PurchaseOrderListEvent( @Nullable PurchaseOrder from )
    {
        this.from = from;
    }

    public Type<PurchaseOrderListEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public String getScrollspy()
    {
        return PurchaseOrders.getScrollspy( from );
    }

    protected void dispatch( PurchaseOrderListEventHandler handler )
    {
        handler.onBack( this );
    }
}
