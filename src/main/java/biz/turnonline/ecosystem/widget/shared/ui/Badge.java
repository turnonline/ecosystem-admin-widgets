
package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.ui.html.Span;
public class Badge
        extends Span {

    public Badge() {
        super(Document.get().createSpanElement(), CssName.BADGE);
        getElement().getStyle().setPosition( Style.Position.STATIC );
        getElement().getStyle().setFontSize( 13, Style.Unit.PX );
        setBorderRadius( "3px" );
        setPaddingTop( 1 );
        setPaddingBottom( 1 );
    }

    /**
     * @param text text of the badge
     */
    public Badge( String text) {
        this();
        setText(text);
    }

    /**
     * @param text     text of the badge
     * @param isCircle is a circle badge
     */
    public Badge( String text, boolean isCircle) {
        this();
        setText(text);
        setCircle(isCircle);
    }

    /**
     * Badge with text and color
     *
     * @param text      text of the badge
     * @param textColor text color of the badge
     * @param bgColor   background color of the badge
     */
    public Badge( String text, Color textColor, Color bgColor) {
        this();
        setText(text);
        setTextColor(textColor);
        setBackgroundColor(bgColor);
    }
}
