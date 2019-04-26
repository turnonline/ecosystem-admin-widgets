package biz.turnonline.ecosystem.widget.shared.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface RestCallEventHandler
        extends EventHandler
{
    void onRestCall( RestCallEvent event );
}
