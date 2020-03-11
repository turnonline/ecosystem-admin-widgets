package biz.turnonline.ecosystem.widget.bill.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveBillEvent
        extends GwtEvent<SaveBillEventHandler>
{
    public static Type<SaveBillEventHandler> TYPE = new Type<SaveBillEventHandler>();

    private final Bill bill;

    public SaveBillEvent(Bill bill)
    {
        this.bill = bill;
    }

    public Type<SaveBillEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveBillEventHandler handler )
    {
        handler.onSaveBill( this );
    }

    public Bill getBill()
    {
        return bill;
    }
}
