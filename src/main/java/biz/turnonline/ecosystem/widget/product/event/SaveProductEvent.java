package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveProductEvent
        extends GwtEvent<SaveProductEventHandler>
{
    public static Type<SaveProductEventHandler> TYPE = new Type<SaveProductEventHandler>();

    private final Product product;

    public SaveProductEvent( Product product )
    {
        this.product = product;
    }

    public Type<SaveProductEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveProductEventHandler handler )
    {
        handler.onSaveContact( this );
    }

    public Product getProduct()
    {
        return product;
    }
}
