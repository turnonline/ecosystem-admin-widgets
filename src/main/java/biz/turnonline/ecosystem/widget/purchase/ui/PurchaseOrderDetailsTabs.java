package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.ui.DynamicTabs;
import gwt.material.design.client.constants.IconType;

/**
 * Tabs of the purchase order details.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderDetailsTabs
        extends DynamicTabs
{
    public PurchaseOrderDetailsTabs()
    {
        super();

        add( newTabItem( messages.labelDetail(), "tabDetails", IconType.VISIBILITY ) );
        add( newTabItem( messages.labelItems(), "tabItems", IconType.REORDER ) );
        add( newTabItem( messages.labelSupplier(), "tabSupplier", IconType.BUSINESS ) );
        add( newTabItem( messages.labelLastInvoice(), "tabInvoice", IconType.LAST_PAGE ) );
    }
}
