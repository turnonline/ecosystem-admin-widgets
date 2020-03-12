package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.shared.ui.DynamicTabs;
import gwt.material.design.client.constants.IconType;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditBillTabs
        extends DynamicTabs
{
    public EditBillTabs()
    {
        super();

        add( newTabItem( messages.labelDetail(), "tabDetail", IconType.VISIBILITY ) );
        add( newTabItem( messages.labelItems(), "tabItems", IconType.REORDER ) );
        add( newTabItem( messages.labelSupplier(), "tabSupplier", IconType.PERM_IDENTITY ) );
    }
}
