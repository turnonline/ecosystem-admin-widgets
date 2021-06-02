package biz.turnonline.ecosystem.widget.purchase.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public interface HasBillCompactUploadedEventHandlers
        extends HasHandlers
{
    HandlerRegistration addUploadedHandler( BillCompactUploadedEventHandler handler );
}
