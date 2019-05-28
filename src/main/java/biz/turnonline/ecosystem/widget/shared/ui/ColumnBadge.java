package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.ui.html.Span;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnBadge extends Span
{
    public ColumnBadge( String text, Color textColor, Color bgColor) {
        super( Document.get().createSpanElement(), CssName.BADGE);

        setText(text);
        setTextColor(textColor);
        setBackgroundColor(bgColor);

        setLayoutPosition( Style.Position.RELATIVE );
        setRight( 0 );
        setTop( -1 );
        setFontSize( "0.9em" );
        setBorderRadius( "3px" );
    }
}
