package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoices;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nullable;

/**
 * Represents a request to show list of incoming invoices.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceListEvent
        extends GwtEvent<IncomingInvoiceListEventHandler>
{
    public static Type<IncomingInvoiceListEventHandler> TYPE = new Type<>();

    private IncomingInvoice from;

    public IncomingInvoiceListEvent( @Nullable IncomingInvoice from )
    {
        this.from = from;
    }

    public Type<IncomingInvoiceListEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public String getScrollspy()
    {
        return IncomingInvoices.getScrollspy( from );
    }

    protected void dispatch( IncomingInvoiceListEventHandler handler )
    {
        handler.onIncomingInvoiceList( this );
    }
}
