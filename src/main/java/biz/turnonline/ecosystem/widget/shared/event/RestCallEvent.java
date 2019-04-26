package biz.turnonline.ecosystem.widget.shared.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class RestCallEvent
        extends GwtEvent<RestCallEventHandler>
{
    public static Type<RestCallEventHandler> TYPE = new Type<RestCallEventHandler>();

    private final Direction direction;

    public RestCallEvent( Direction direction )
    {
        this.direction = direction;
    }

    public Type<RestCallEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( RestCallEventHandler handler )
    {
        handler.onRestCall( this );
    }

    public Direction getDirection()
    {
        return direction;
    }

    public enum Direction
    {
        IN,
        OUT
    }
}
