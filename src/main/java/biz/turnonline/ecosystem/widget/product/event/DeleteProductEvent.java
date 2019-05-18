package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import com.google.gwt.event.shared.GwtEvent;

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
        handler.onDeleteProduct( this );
    }

    public List<Product> getProducts()
    {
        return products;
    }
}
