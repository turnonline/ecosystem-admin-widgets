package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.bill.BillItem;
import biz.turnonline.ecosystem.widget.shared.ui.Repeater;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillItems
        extends Repeater<BillItem>
{
    private static AppMessages messages = AppMessages.INSTANCE;

    public BillItems()
    {
        header( messages.labelItemName(), "25%" );
        header( messages.labelAmount(), "10%" );
        header( messages.labelCurrency(), "10%" );
        header( messages.labelUnit(), "10%" );
        header( messages.labelVat(), "10%" );
        header( messages.labelPriceExcludingVat(), "15%" );
        header( messages.labelPriceIncludingVat(), "15%" );
        header( "", "5%" );
    }

    @Override
    protected Widget newItem()
    {
        return new BillItemRow();
    }

    private void header(String header, String width) {
        Label label = new Label( header );
        label.getElement().getStyle().setOverflow( Style.Overflow.AUTO );

        addHeader( label, width ).setPaddingLeft( 0 );
    }
}
