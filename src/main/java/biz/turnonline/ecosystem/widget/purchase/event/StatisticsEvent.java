package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class StatisticsEvent
        extends GwtEvent<StatisticsEventHandler>
{
    public static Type<StatisticsEventHandler> TYPE = new Type<StatisticsEventHandler>();

    public Type<StatisticsEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( StatisticsEventHandler handler )
    {
        handler.onStatistics( this );
    }
}
