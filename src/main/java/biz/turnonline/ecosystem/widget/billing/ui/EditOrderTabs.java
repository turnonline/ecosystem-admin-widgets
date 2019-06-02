package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.ui.DynamicTabs;
import gwt.material.design.client.constants.IconType;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditOrderTabs
        extends DynamicTabs
{
    public EditOrderTabs()
    {
        super();

        add( newTabItem( messages.labelDetail(), "tabDetail", IconType.VISIBILITY ) );
        add( newTabItem( messages.labelCustomer(), "tabCustomer", IconType.PERM_IDENTITY ) );
        add( newTabItem( messages.labelItems(), "tabItems", IconType.REORDER ) );
    }
}
