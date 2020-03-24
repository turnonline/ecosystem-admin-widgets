package biz.turnonline.ecosystem.widget.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface Resources
        extends ClientBundle
{
    Resources INSTANCE = GWT.create( Resources.class );

    @Source( {"no-image.png"} )
    ImageResource noImage();

    @Source( "logo.png" )
    ImageResource logo();
}
