package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public interface TransactionDetailEventHandler
        extends EventHandler
{
    void onDetail( TransactionDetailEvent event );
}
