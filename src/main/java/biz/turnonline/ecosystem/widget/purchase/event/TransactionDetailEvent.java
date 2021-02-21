package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class TransactionDetailEvent
        extends GwtEvent<TransactionDetailEventHandler>
{
    public static Type<TransactionDetailEventHandler> TYPE = new Type<TransactionDetailEventHandler>();

    private final Long id;

    public TransactionDetailEvent( Long id )
    {
        this.id = id;
    }

    public Type<TransactionDetailEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( TransactionDetailEventHandler handler )
    {
        handler.onDetail( this );
    }

    public Long getId()
    {
        return id;
    }
}
