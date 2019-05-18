package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.BillingUnit;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillingUnitComboBox
        extends CodeBookComboBox<BillingUnit>
{
    public BillingUnitComboBox()
    {
        super( BillingUnit.class );
    }
}
