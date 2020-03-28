package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.i18n.client.NumberFormat;
import gwt.material.design.client.ui.MaterialLabel;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class PriceLabel
        extends MaterialLabel
{
    public void setValue( Double price, String currency )
    {
        setValue( format( price, currency ) );
    }

    private String format( Double price, String currency )
    {
        price = price != null ? price : 0D;
        return currency == null ? price.toString() : NumberFormat.getCurrencyFormat( currency ).format( price );
    }
}
