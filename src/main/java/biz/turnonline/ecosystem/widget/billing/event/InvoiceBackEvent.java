package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoiceBackEvent
        extends GwtEvent<InvoiceBackEventHandler>
{
    public static Type<InvoiceBackEventHandler> TYPE = new Type<>();

    private Invoice from;

    public InvoiceBackEvent( Invoice from )
    {
        this.from = from;
    }

    public Type<InvoiceBackEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public String getScrollspy()
    {
        return from == null ? null : from.getScrollspy();
    }

    protected void dispatch( InvoiceBackEventHandler handler )
    {
        handler.onBack( this );
    }
}
