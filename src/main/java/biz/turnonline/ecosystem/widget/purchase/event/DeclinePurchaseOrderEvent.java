package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to decline purchase order identified by ID.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DeclinePurchaseOrderEvent
        extends GwtEvent<DeclinePurchaseOrderEventHandler>
{
    public static Type<DeclinePurchaseOrderEventHandler> TYPE = new Type<>();

    private final PurchaseOrder purchaseOrder;

    public DeclinePurchaseOrderEvent( @Nonnull PurchaseOrder purchaseOrder )
    {
        this.purchaseOrder = checkNotNull( purchaseOrder, "Purchase order can't be null" );
    }

    public Type<DeclinePurchaseOrderEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeclinePurchaseOrderEventHandler handler )
    {
        handler.onDeclinePurchaseOrder( this );
    }

    /**
     * Returns the purchase order to be declined.
     *
     * @return the purchase order to be declined
     */
    public PurchaseOrder getPurchaseOrder()
    {
        return purchaseOrder;
    }

    /**
     * Returns ID of the purchase order to be declined.
     *
     * @return the purchase order ID
     */
    public Long getId()
    {
        return purchaseOrder.getId();
    }
}
