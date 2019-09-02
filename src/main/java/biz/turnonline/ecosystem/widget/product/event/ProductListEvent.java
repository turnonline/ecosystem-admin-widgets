package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.product.place.Products;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a request to list all products.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ProductListEvent
        extends GwtEvent<ProductListEventHandler>
{
    public static Type<ProductListEventHandler> TYPE = new Type<>();

    private Product from;

    public ProductListEvent( Product from )
    {
        this.from = from;
    }

    public Type<ProductListEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public String getScrollspy()
    {
        return Products.getScrollspy( from );
    }

    protected void dispatch( ProductListEventHandler handler )
    {
        handler.onBack( this );
    }
}
