package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPicture;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class RemovePictureEvent
        extends GwtEvent<RemovePictureEventHandler>
{
    public static Type<RemovePictureEventHandler> TYPE = new Type<RemovePictureEventHandler>();

    private ProductPicture picture;

    public RemovePictureEvent( ProductPicture picture )
    {
        this.picture = picture;
    }

    public Type<RemovePictureEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( RemovePictureEventHandler handler )
    {
        handler.onRemovePicture( this );
    }

    public ProductPicture getPicture()
    {
        return picture;
    }
}
