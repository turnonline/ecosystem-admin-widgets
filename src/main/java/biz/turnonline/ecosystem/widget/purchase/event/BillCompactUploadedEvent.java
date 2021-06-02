package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.dom.client.DomEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillCompactUploadedEvent
        extends DomEvent<BillCompactUploadedEventHandler>
{
    public static Type<BillCompactUploadedEventHandler> TYPE = new Type<BillCompactUploadedEventHandler>( "bill-compact-uploaded", new BillCompactUploadedEvent() );

    private Bill bill;

    public BillCompactUploadedEvent()
    {
    }

    public BillCompactUploadedEvent( Bill bill )
    {
        this.bill = bill;
    }

    public Bill getBill()
    {
        return bill;
    }

    public Type<BillCompactUploadedEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( BillCompactUploadedEventHandler handler )
    {
        handler.onUploaded( this );
    }
}
