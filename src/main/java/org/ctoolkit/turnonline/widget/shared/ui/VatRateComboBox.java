package org.ctoolkit.turnonline.widget.shared.ui;

import org.ctoolkit.turnonline.widget.shared.rest.productbilling.VatRate;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class VatRateComboBox
        extends CodeBookComboBox<VatRate>
{
    public VatRateComboBox()
    {
        super( VatRate.class );
    }
}
