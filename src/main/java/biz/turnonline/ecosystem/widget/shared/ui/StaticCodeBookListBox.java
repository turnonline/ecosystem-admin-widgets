package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.CodeBook;
import gwt.material.design.client.ui.MaterialListValueBox;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class StaticCodeBookListBox
        extends MaterialListValueBox<StaticCodeBook>
{
    public StaticCodeBookListBox()
    {
        setKeyFactory( CodeBook::getCode );
        values().forEach( r -> addItem( r, r.getLabel() ) );
    }

    protected abstract List<StaticCodeBook> values();

    public void setSingleValueByCode( String code )
    {
        if ( code != null )
        {
            setSelectedIndex( getIndexByString( code ) );
        }
        else
        {
            setSelectedIndex( getIndexByString( defaultValue() ) );
        }
    }

    public String getSingleValueByCode()
    {
        return getValue() != null ? getValue().getCode() : null;
    }

    protected String defaultValue()
    {
        return null;
    }
}
