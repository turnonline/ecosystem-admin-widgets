package org.ctoolkit.turnonline.widget.product.event;

import com.google.gwt.event.shared.GwtEvent;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteProductEvent
        extends GwtEvent<DeleteProductEventHandler>
{
    public static Type<DeleteProductEventHandler> TYPE = new Type<DeleteProductEventHandler>();

    private final List<Product> products;

    public DeleteProductEvent( List<Product> products )
    {
        this.products = products;
    }

    public Type<DeleteProductEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteProductEventHandler handler )
    {
        handler.onDeleteContact( this );
    }

    public List<Product> getProducts()
    {
        return products;
    }
}
