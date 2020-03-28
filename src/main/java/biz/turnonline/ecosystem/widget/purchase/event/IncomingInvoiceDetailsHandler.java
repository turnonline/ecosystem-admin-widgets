package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Incoming invoice details handler.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface IncomingInvoiceDetailsHandler
        extends EventHandler
{
    void onIncomingInvoiceDetails( IncomingInvoiceDetailsEvent event );
}
