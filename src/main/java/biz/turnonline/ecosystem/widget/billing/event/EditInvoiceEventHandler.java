package biz.turnonline.ecosystem.widget.billing.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Edit (new or existing) invoice event handler.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface EditInvoiceEventHandler
        extends EventHandler
{
    void onEditInvoice( EditInvoiceEvent event );
}
