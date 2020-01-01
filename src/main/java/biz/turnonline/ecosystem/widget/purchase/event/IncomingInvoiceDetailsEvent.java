package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents an event that navigates to view incoming invoice identified by
 * <ul>
 * <li>{@link IncomingInvoice#getOrderId()} Purchase order ID</li>
 * <li>{@link IncomingInvoice#getId()} Incoming invoice ID</li>
 * </ul>
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceDetailsEvent
        extends GwtEvent<IncomingInvoiceDetailsHandler>
{
    public static Type<IncomingInvoiceDetailsHandler> TYPE = new Type<>();

    private final Long orderId;

    private final Long invoiceId;

    public IncomingInvoiceDetailsEvent( @Nonnull IncomingInvoice invoice )
    {
        this.orderId = checkNotNull( invoice.getOrderId() );
        this.invoiceId = checkNotNull( invoice.getId() );
    }

    public Type<IncomingInvoiceDetailsHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( IncomingInvoiceDetailsHandler handler )
    {
        handler.onIncomingInvoiceDetails( this );
    }

    /**
     * Returns the purchase order ID.
     *
     * @return the purchase order ID
     */
    public Long getOrderId()
    {
        return orderId;
    }

    /**
     * Returns the incoming invoice ID.
     *
     * @return the incoming invoice ID
     */
    public Long getInvoiceId()
    {
        return invoiceId;
    }
}
