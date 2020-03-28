package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * List of purchase orders event handler.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface PurchaseOrderListEventHandler
        extends EventHandler
{
    void onBack( PurchaseOrderListEvent event );
}
