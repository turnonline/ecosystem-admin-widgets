package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Decline purchase order event handler.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface DeclinePurchaseOrderEventHandler
        extends EventHandler
{
    void onDeclinePurchaseOrder( DeclinePurchaseOrderEvent event );
}
