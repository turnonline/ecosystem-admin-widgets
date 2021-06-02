package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ApproveAllBillsEvent
        extends GwtEvent<ApproveAllBillsEventHandler>
{
    public static Type<ApproveAllBillsEventHandler> TYPE = new Type<ApproveAllBillsEventHandler>();

    private List<Bill> bills;

    public ApproveAllBillsEvent( List<Bill> bills )
    {
        this.bills = bills;
    }

    public List<Bill> getBills()
    {
        return bills;
    }

    public Type<ApproveAllBillsEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( ApproveAllBillsEventHandler handler )
    {
        handler.onApprove( this );
    }
}
