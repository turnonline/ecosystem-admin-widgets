package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Purchase order detail event handler.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface PurchaseOrderDetailEventHandler
        extends EventHandler
{
    void onPurchaseOrderDetail( PurchaseOrderDetailEvent event );
}
