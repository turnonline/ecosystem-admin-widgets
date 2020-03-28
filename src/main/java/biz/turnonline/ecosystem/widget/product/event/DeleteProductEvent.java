package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to delete specified product.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteProductEvent
        extends GwtEvent<DeleteProductEventHandler>
{
    public static Type<DeleteProductEventHandler> TYPE = new Type<>();

    private final Product product;

    public DeleteProductEvent( @Nonnull Product product )
    {
        this.product = checkNotNull( product, "Product cannot be null" );
    }

    public Type<DeleteProductEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteProductEventHandler handler )
    {
        handler.onDeleteProduct( this );
    }

    public Product getProduct()
    {
        return product;
    }
}
