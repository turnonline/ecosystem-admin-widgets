package biz.turnonline.ecosystem.widget.bill.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class NewBillEvent
        extends GwtEvent<NewBillEventHandler>
{
    public static Type<NewBillEventHandler> TYPE = new Type<NewBillEventHandler>();

    private Bill bill;

    public NewBillEvent( Bill bill )
    {
        this.bill = bill;
    }

    public Type<NewBillEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( NewBillEventHandler handler )
    {
        handler.onNew( this );
    }

    public Bill getBill()
    {
        return bill;
    }
}
