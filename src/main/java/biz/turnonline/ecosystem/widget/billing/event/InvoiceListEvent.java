package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a request to list all invoices.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoiceListEvent
        extends GwtEvent<InvoiceListEventHandler>
{
    public static Type<InvoiceListEventHandler> TYPE = new Type<>();

    private Invoice from;

    public InvoiceListEvent( Invoice from )
    {
        this.from = from;
    }

    public Type<InvoiceListEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public String getScrollspy()
    {
        return Invoices.getScrollspy( from );
    }

    protected void dispatch( InvoiceListEventHandler handler )
    {
        handler.onBack( this );
    }
}
