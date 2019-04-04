package biz.turnonline.ecosystem.widget.product.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface EditProductEventHandler
        extends EventHandler
{
    void onEditContact( EditProductEvent event );
}
