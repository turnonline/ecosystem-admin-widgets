package org.ctoolkit.turnonline.widget.shared.ui;

import org.ctoolkit.turnonline.widget.shared.rest.productbilling.BillingUnit;

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
