package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InputSearchIcon
        extends MaterialIcon
{
    public InputSearchIcon()
    {
        setTextColor( Color.GREY );
        setIconType( IconType.SEARCH );

        Style style = getElement().getStyle();
        style.setPosition( Style.Position.ABSOLUTE );
        style.setMarginTop( 15, Style.Unit.PX );
        style.setRight( 0, Style.Unit.PX );
    }
}
