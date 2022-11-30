package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRange;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DashboardRangeChangeEvent
        extends GwtEvent<DashboardRangeChangeEventHandler>
{
    public static Type<DashboardRangeChangeEventHandler> TYPE = new Type<DashboardRangeChangeEventHandler>();

    private PredefinedRange predefinedRange;

    public DashboardRangeChangeEvent( PredefinedRange predefinedRange )
    {
        this.predefinedRange = predefinedRange;
    }

    public Type<DashboardRangeChangeEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DashboardRangeChangeEventHandler handler )
    {
        handler.onRangeChange( this );
    }

    public PredefinedRange getPredefinedRange()
    {
        return predefinedRange;
    }
}
