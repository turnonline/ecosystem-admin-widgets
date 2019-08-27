package biz.turnonline.ecosystem.widget.billing.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Order list event handler.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface OrderListEventHandler
        extends EventHandler
{
    void onBack( OrderListEvent event );
}
