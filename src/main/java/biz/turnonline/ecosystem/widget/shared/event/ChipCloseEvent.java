package biz.turnonline.ecosystem.widget.shared.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ChipCloseEvent
        extends GwtEvent<ChipCloseEventHandler>
{
    public static Type<ChipCloseEventHandler> TYPE = new Type<ChipCloseEventHandler>();

    private String text;

    public ChipCloseEvent( String text )
    {
        this.text = text;
    }

    public Type<ChipCloseEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( ChipCloseEventHandler handler )
    {
        handler.onChipClose( this );
    }

    public String getText()
    {
        return text;
    }
}
