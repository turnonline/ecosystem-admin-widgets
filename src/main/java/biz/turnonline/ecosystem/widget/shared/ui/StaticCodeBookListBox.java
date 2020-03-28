package biz.turnonline.ecosystem.widget.shared.ui;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
abstract class StaticCodeBookListBox
        extends CodeBookComboBox<StaticCodeBook>
{
    StaticCodeBookListBox()
    {
        super( StaticCodeBook.class );
        setHideSearch( true );
        setMultiple( false );
    }

    protected abstract List<StaticCodeBook> values();

    protected void initialize( String code )
    {
        setItems( values() );
        itemsLoaded();
        setSingleValueByCode( code );
    }
}
