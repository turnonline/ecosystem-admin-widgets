package biz.turnonline.ecosystem.widget.bill.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ApproveBillEvent
        extends GwtEvent<ApproveBillEventHandler>
{
    public static Type<ApproveBillEventHandler> TYPE = new Type<ApproveBillEventHandler>();

    private Bill bill;

    public ApproveBillEvent( Bill bill )
    {
        this.bill = bill;
    }

    public Type<ApproveBillEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( ApproveBillEventHandler handler )
    {
        handler.onApprove( this );
    }

    public Bill getBill()
    {
        return bill;
    }
}
