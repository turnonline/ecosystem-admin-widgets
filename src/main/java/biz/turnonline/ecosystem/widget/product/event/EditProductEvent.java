package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditProductEvent
        extends GwtEvent<EditProductEventHandler>
{
    public static Type<EditProductEventHandler> TYPE = new Type<>();

    private Product product;

    public EditProductEvent()
    {
    }

    public EditProductEvent( Product product )
    {
        this.product = product;
    }

    public Type<EditProductEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditProductEventHandler handler )
    {
        handler.onEditProduct( this );
    }

    /**
     * Returns the product ID or {@code null} if the event represents a new product request.
     *
     * @return the product ID or {@code null}
     */
    public Long getId()
    {
        return product == null ? null : product.getId();
    }
}
