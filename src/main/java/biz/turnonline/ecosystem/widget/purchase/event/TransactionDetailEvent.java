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

    private final TransactionSource source;

    public TransactionDetailEvent( Long id, TransactionSource source )
    {
        this.id = id;
        this.source = source;
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

    @Override
    public TransactionSource getSource()
    {
        return source;
    }

    /**
     * Source of transaction id - for various source different approach of transaction loading is applied
     */
    public enum TransactionSource {
        /**
         * Take ID as is an send it to transaction detail page
         */
        PAYMENT,

        /**
         * Take ID and find transaction via billing service, use transactionId from response
         * as value which will be send to transaction detail page
         */
        BILLING;
    }
}
