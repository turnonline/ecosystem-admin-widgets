package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditProductEvent
        extends GwtEvent<EditProductEventHandler>
{
    public static Type<EditProductEventHandler> TYPE = new Type<EditProductEventHandler>();

    private Product product;

    public EditProductEvent() {
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
        handler.onEditContact( this );
    }

    public Product getProduct()
    {
        return product;
    }
}
