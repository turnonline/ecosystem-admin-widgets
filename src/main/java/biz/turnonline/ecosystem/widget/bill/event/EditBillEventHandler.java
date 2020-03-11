package biz.turnonline.ecosystem.widget.bill.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface EditBillEventHandler
        extends EventHandler
{
    void onEditBill(EditBillEvent event);
}
