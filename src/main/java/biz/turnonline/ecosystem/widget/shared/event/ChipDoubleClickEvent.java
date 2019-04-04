package biz.turnonline.ecosystem.widget.shared.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ChipDoubleClickEvent
        extends GwtEvent<ChipDoubleClickEventHandler>
{
    public static Type<ChipDoubleClickEventHandler> TYPE = new Type<ChipDoubleClickEventHandler>();

    private String text;

    public ChipDoubleClickEvent( String text )
    {
        this.text = text;
    }

    public Type<ChipDoubleClickEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( ChipDoubleClickEventHandler handler )
    {
        handler.onChipDoubleClick( this );
    }

    public String getText()
    {
        return text;
    }
}
