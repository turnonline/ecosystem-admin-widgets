package org.ctoolkit.turnonline.widget.shared.ui;

import com.google.gwt.core.client.GWT;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.html.Option;
import org.ctoolkit.gwt.client.facade.Items;
import org.ctoolkit.turnonline.widget.shared.rest.FacadeCallback;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.AccountStewardFacade;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.Country;
import org.fusesource.restygwt.client.Method;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CountryComboBox
        extends MaterialComboBox<Country>
{
    private AccountStewardFacade accountStewardFacade = GWT.create( AccountStewardFacade.class );

    private boolean itemsLoaded = false;

    public CountryComboBox()
    {
        setKeyFactory( Country::getCode );
    }

    @Override
    protected Option buildOption( String text, Country value )
    {
        Option option = super.buildOption( text, value );
        option.setText( value.getLabel() );
        return option;
    }

    public void setSingleValueByCode( String code )
    {
        setSelectedIndex( getIndexByString( defaultCountry() ) );

        if ( itemsLoaded )
        {
            if ( code == null )
            {
                setSelectedIndex( getIndexByString( defaultCountry() ) );
            }
            else
            {
                setSelectedIndex( getIndexByString( code ) );
            }
        }
        else
        {
            initialize( code );
        }
    }

    public String getSingleValueByCode()
    {
        return getSingleValue() != null ? getSingleValue().getCode() : null;
    }

    protected void initialize( String code )
    {
        accountStewardFacade.getCountries( null, new FacadeCallback<Items<Country>>()
        {
            @Override
            public void onSuccess( Method method, Items<Country> response )
            {
                super.onSuccess( method, response );
                setItems( response.getItems() );

                if ( !response.getItems().isEmpty() )
                {
                    itemsLoaded = true;
                    setSingleValueByCode( code );
                }
            }
        } );
    }

    // TODO: implement default country
    protected String defaultCountry()
    {
        return "SK";
    }
}
