package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.account.Country;

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
