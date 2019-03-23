package org.ctoolkit.turnonline.widget.shared.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import gwt.material.design.client.ui.table.cell.Column;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class NotSafeHtmlColumn<T>
        extends Column<T, String>
{
    public NotSafeHtmlColumn()
    {
        super( new AbstractCell<String>()
        {
            @Override
            public void render( Context context, String data, SafeHtmlBuilder sb )
            {
                if ( data != null )
                {
                    sb.appendHtmlConstant( data );
                }
            }
        } );
    }
}
