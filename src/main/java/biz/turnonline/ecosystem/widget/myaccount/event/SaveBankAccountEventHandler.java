package biz.turnonline.ecosystem.widget.myaccount.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface SaveBankAccountEventHandler
        extends EventHandler
{
    void onSave( SaveBankAccountEvent event );
}
