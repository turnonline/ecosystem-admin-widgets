package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * List of incoming invoices event handler.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface IncomingInvoiceListEventHandler
        extends EventHandler
{
    void onIncomingInvoiceList( IncomingInvoiceListEvent event );
}
