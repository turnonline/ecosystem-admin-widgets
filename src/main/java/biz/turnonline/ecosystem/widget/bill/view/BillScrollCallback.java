package biz.turnonline.ecosystem.widget.bill.view;

import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRange;
import org.ctoolkit.gwt.client.facade.Items;

import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class BillScrollCallback
        implements InfiniteScroll.Callback<Bill>
{
    private Date from = PredefinedRange.firstDayOfCurrentMonth();

    private Date to = PredefinedRange.lastDayOfCurrentMonth();

    @Override
    public void load( int offset, int limit, SuccessCallback<Items<Bill>> callback )
    {
        load( offset, limit, from, to, callback );
    }

    public abstract void load( int offset, int limit, Date from, Date to, SuccessCallback<Items<Bill>> callback );

    public void setFrom( Date from )
    {
        this.from = from;
    }

    public void setTo( Date to )
    {
        this.to = to;
    }
}
