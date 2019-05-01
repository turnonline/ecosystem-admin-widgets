package biz.turnonline.ecosystem.widget.invoice.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface BackEventHandler
        extends EventHandler
{
    void onBack( BackEvent event );
}
