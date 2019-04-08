package biz.turnonline.ecosystem.widget.order.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface DeleteOrderEventHandler
        extends EventHandler
{
    void onDeleteContact( DeleteOrderEvent event );
}
