package org.ctoolkit.turnonline.widget.shared.ui;

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

    static {
        currencyList = new ArrayList<>( currencies.currencyMap().keySet() );
        Collections.sort( currencyList );
    }

    public CurrencyComboBox()
    {
        setItems( currencyList );
    }
}
