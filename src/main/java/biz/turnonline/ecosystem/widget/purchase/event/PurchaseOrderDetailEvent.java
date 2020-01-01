package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to view details of the purchase order identified by ID.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderDetailEvent
        extends GwtEvent<PurchaseOrderDetailEventHandler>
{
    public static Type<PurchaseOrderDetailEventHandler> TYPE = new Type<>();

    private final Long orderId;

    public PurchaseOrderDetailEvent( @Nonnull Long orderId )
    {
        this.orderId = checkNotNull( orderId, "Purchase order ID can't be null" );
    }

    public Type<PurchaseOrderDetailEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( PurchaseOrderDetailEventHandler handler )
    {
        handler.onPurchaseOrderDetail( this );
    }

    /**
     * Returns the purchase order ID.
     *
     * @return the purchase order ID
     */
    public Long getId()
    {
        return orderId;
    }
}
