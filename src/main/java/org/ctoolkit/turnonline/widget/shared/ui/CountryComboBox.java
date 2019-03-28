package org.ctoolkit.turnonline.widget.shared.ui;

import org.ctoolkit.turnonline.widget.shared.Configuration;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.Country;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CountryComboBox
        extends CodeBookComboBox<Country>
{
    public CountryComboBox()
    {
        super( Country.class );
    }

    protected String defaultValue()
    {
        return Configuration.get().getDomicile();
    }
}
