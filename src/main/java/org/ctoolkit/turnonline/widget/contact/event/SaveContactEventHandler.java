package org.ctoolkit.turnonline.widget.contact.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface SaveContactEventHandler
        extends EventHandler
{
    void onSaveContact( SaveContactEvent event );
}
