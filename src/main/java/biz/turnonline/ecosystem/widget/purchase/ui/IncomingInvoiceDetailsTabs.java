package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.ui.DynamicTabs;
import gwt.material.design.client.constants.IconType;

/**
 * Tabs of the incoming invoice details.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceDetailsTabs
        extends DynamicTabs
{
    public IncomingInvoiceDetailsTabs()
    {
        super();

        add( newTabItem( messages.labelDetail(), "tabDetails", IconType.VISIBILITY ) );
        add( newTabItem( messages.labelItems(), "tabItems", IconType.REORDER ) );
        add( newTabItem( messages.labelSupplier(), "tabSupplier", IconType.BUSINESS ) );
        add( newTabItem( messages.labelTransactions(), "tabTransactions", IconType.IMPORT_EXPORT ) );
    }
}
