package biz.turnonline.ecosystem.widget.bill.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditBillEvent
        extends GwtEvent<EditBillEventHandler>
{
    public static Type<EditBillEventHandler> TYPE = new Type<>();

    private Bill bill;

    public EditBillEvent()
    {
    }

    public EditBillEvent(Bill bill)
    {
        this.bill = bill;
    }

    public Type<EditBillEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditBillEventHandler handler )
    {
        handler.onEditBill( this );
    }

    /**
     * Returns the bill ID or {@code null} if the event represents a new bill request.
     *
     * @return the bill ID or {@code null}
     */
    public Long getId()
    {
        return bill == null ? null : bill.getId();
    }
}
