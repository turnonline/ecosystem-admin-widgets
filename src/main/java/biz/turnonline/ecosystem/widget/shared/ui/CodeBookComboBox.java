package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.CodeBook;
import biz.turnonline.ecosystem.widget.shared.rest.CodeBookRestFacade;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.html.Option;
import org.ctoolkit.gwt.client.facade.Items;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CodeBookComboBox<T extends CodeBook>
        extends MaterialComboBox<T>
{
    private boolean itemsLoaded = false;

    private final Class<T> clazz;

    public CodeBookComboBox(Class<T> clazz)
    {
        setKeyFactory( CodeBook::getCode );
        this.clazz = clazz;
    }

    @Override
    protected Option buildOption( String text, T value )
    {
        Option option = super.buildOption( text, value );
        option.setText( value.getLabel() );
        return option;
    }

    public void setSingleValueByCode( String code )
    {
        setSelectedIndex( getIndexByString( defaultValue() ) );

        if ( itemsLoaded )
        {
            if ( code == null )
            {
                setSelectedIndex( getIndexByString( defaultValue() ) );
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
        CodeBookRestFacade.getCodeBook( clazz, ( FacadeCallback<Items<T>> ) response -> {
            setItems( response.getItems() );

            if ( !response.getItems().isEmpty() )
            {
                itemsLoaded = true;
                setSingleValueByCode( code );
            }
        } );
    }

    protected String defaultValue()
    {
        return null;
    }
}
