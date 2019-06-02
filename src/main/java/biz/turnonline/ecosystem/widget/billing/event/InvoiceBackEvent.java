package biz.turnonline.ecosystem.widget.billing.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoiceBackEvent
        extends GwtEvent<InvoiceBackEventHandler>
{
    public static Type<InvoiceBackEventHandler> TYPE = new Type<InvoiceBackEventHandler>();

    public Type<InvoiceBackEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( InvoiceBackEventHandler handler )
    {
        handler.onBack( this );
    }
}
