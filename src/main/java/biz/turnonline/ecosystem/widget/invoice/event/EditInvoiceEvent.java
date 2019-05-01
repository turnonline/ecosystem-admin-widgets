package biz.turnonline.ecosystem.widget.invoice.event;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Invoice;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditInvoiceEvent
        extends GwtEvent<EditInvoiceEventHandler>
{
    public static Type<EditInvoiceEventHandler> TYPE = new Type<EditInvoiceEventHandler>();

    private Invoice invoice;

    public EditInvoiceEvent() {
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

    public Invoice getInvoice()
    {
        return invoice;
    }
}
