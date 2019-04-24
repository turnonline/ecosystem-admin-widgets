package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.constants.CurrencyCodeMapConstants;
import gwt.material.design.addins.client.combobox.MaterialComboBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CurrencyComboBox
        extends MaterialComboBox<String>
{
    private static final CurrencyCodeMapConstants currencies = GWT.create( CurrencyCodeMapConstants.class );

    private static List<String> currencyList;

    static
    {
        currencyList = new ArrayList<>( currencies.currencyMap().keySet() );
        Collections.sort( currencyList );
    }

    public CurrencyComboBox()
    {
        setItems( currencyList );
    }


    @Override
    public void setSingleValue( String value )
    {
        if ( value == null || "".equals( value ) )
        {
            value = defaultValue();
        }

        super.setSingleValue( value );
    }

    protected String defaultValue()
    {
        return Configuration.get().getCurrency();
    }
}
