package biz.turnonline.ecosystem.widget.bill.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteBillEvent
        extends GwtEvent<DeleteBillEventHandler>
{
    public static Type<DeleteBillEventHandler> TYPE = new Type<DeleteBillEventHandler>();

    private final Bill bill;

    public DeleteBillEvent(Bill bill)
    {
        this.bill = bill;
    }

    public Type<DeleteBillEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteBillEventHandler handler )
    {
        handler.onDeleteBill( this );
    }

    public Bill getBill()
    {
        return bill;
    }
}
