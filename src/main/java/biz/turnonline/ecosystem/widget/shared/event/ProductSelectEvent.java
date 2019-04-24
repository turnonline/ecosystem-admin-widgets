package biz.turnonline.ecosystem.widget.shared.event;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ProductSelectEvent
        extends GwtEvent<ProductSelectEventHandler>
{
    public static Type<ProductSelectEventHandler> TYPE = new Type<ProductSelectEventHandler>();

    private Product product;

    public ProductSelectEvent( Product product )
    {
        this.product = product;
    }

    public Type<ProductSelectEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( ProductSelectEventHandler handler )
    {
        handler.onProductSelect( this );
    }

    public Product getProduct()
    {
        return product;
    }
}
