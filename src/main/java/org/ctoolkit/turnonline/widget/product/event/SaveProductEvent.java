package org.ctoolkit.turnonline.widget.product.event;

import com.google.gwt.event.shared.GwtEvent;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveProductEvent
        extends GwtEvent<SaveProudctEventHandler>
{
    public static Type<SaveProudctEventHandler> TYPE = new Type<SaveProudctEventHandler>();

    private final Product product;

    public SaveProductEvent( Product product )
    {
        this.product = product;
    }

    public Type<SaveProudctEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveProudctEventHandler handler )
    {
        handler.onSaveContact( this );
    }

    public Product getProduct()
    {
        return product;
    }
}
