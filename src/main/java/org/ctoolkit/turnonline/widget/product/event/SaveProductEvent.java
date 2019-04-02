package org.ctoolkit.turnonline.widget.product.event;

import com.google.gwt.event.shared.GwtEvent;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;

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
