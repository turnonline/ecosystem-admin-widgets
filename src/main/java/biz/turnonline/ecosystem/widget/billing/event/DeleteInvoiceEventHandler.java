package biz.turnonline.ecosystem.widget.billing.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Delete invoice event handler.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface DeleteInvoiceEventHandler
        extends EventHandler
{
    void onDeleteInvoice( DeleteInvoiceEvent event );
}
