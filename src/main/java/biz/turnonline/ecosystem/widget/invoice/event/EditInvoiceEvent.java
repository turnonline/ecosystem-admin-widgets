package biz.turnonline.ecosystem.widget.invoice.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditInvoiceEvent
        extends GwtEvent<EditInvoiceEventHandler>
{
    public static Type<EditInvoiceEventHandler> TYPE = new Type<>();

    private Invoice invoice;

    public EditInvoiceEvent()
    {
    }

    public EditInvoiceEvent( Invoice invoice )
    {
        this.invoice = invoice;
    }

    public Type<EditInvoiceEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditInvoiceEventHandler handler )
    {
        handler.onEditInvoice( this );
    }

    /**
     * Returns the order ID or {@code null} if the event represents a new invoice request.
     *
     * @return the order ID or {@code null}
     */
    public Long getOrderId()
    {
        return invoice == null ? null : invoice.getOrderId();
    }

    /**
     * Returns the invoice ID or {@code null} if the event represents a new invoice request.
     *
     * @return the invoice ID or {@code null}
     */
    public Long getInvoiceId()
    {
        return invoice == null ? null : invoice.getId();
    }
}
