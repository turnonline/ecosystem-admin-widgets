package biz.turnonline.ecosystem.widget.invoice.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface EditInvoiceEventHandler
        extends EventHandler
{
    void onEditInvoice( EditInvoiceEvent event );
}
