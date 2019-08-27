package biz.turnonline.ecosystem.widget.billing.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Invoice list event handler.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface InvoiceListEventHandler
        extends EventHandler
{
    void onBack( InvoiceListEvent event );
}
