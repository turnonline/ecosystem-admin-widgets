package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.ui.DynamicTabs;
import gwt.material.design.client.constants.IconType;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditProductTabs
        extends DynamicTabs
{
    public EditProductTabs()
    {
        super();

        add( newTabItem( messages.labelDetail(), "tabDetail", IconType.VISIBILITY ) );
        add( newTabItem( messages.labelContent(), "tabContent", IconType.CODE ) );
        add( newTabItem( messages.labelPublishing(), "tabPublishing", IconType.PUBLIC ) );
        add( newTabItem( messages.labelPricing(), "tabPricing", IconType.ATTACH_MONEY ) );
        add( newTabItem( messages.labelInvoicing(), "tabInvoicing", IconType.ASSIGNMENT ) );
        add( newTabItem( messages.labelEvent(), "tabEvent", IconType.EVENT ) );
    }
}
