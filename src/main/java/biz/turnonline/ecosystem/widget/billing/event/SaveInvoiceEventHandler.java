package biz.turnonline.ecosystem.widget.billing.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface SaveInvoiceEventHandler
        extends EventHandler
{
    void onSaveInvoice( SaveInvoiceEvent event );
}
