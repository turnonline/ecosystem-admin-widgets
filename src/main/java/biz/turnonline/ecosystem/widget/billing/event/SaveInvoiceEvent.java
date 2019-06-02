package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveInvoiceEvent
        extends GwtEvent<SaveInvoiceEventHandler>
{
    public static Type<SaveInvoiceEventHandler> TYPE = new Type<SaveInvoiceEventHandler>();

    private final Invoice invoice;

    public SaveInvoiceEvent( Invoice invoice )
    {
        this.invoice = invoice;
    }

    public Type<SaveInvoiceEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveInvoiceEventHandler handler )
    {
        handler.onSaveInvoice( this );
    }

    public Invoice getInvoice()
    {
        return invoice;
    }
}
