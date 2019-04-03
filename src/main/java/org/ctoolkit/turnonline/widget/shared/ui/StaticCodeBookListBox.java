package org.ctoolkit.turnonline.widget.shared.ui;

import gwt.material.design.client.ui.MaterialListValueBox;
import org.ctoolkit.turnonline.widget.shared.rest.CodeBook;

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
