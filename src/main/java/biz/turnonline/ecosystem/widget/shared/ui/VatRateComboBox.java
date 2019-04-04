package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.VatRate;

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
