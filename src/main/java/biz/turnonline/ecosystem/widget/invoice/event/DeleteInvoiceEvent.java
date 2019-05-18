package biz.turnonline.ecosystem.widget.invoice.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteInvoiceEvent
        extends GwtEvent<DeleteInvoiceEventHandler>
{
    public static Type<DeleteInvoiceEventHandler> TYPE = new Type<DeleteInvoiceEventHandler>();

    private final List<Invoice> invoices;

    public DeleteInvoiceEvent( List<Invoice> invoices )
    {
        this.invoices = invoices;
    }

    public Type<DeleteInvoiceEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteInvoiceEventHandler handler )
    {
        handler.onDeleteInvoice( this );
    }

    public List<Invoice> getInvoices()
    {
        return invoices;
    }
}
