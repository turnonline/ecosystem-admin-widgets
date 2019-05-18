package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoiceTypeListBox
        extends StaticCodeBookListBox
{
    private static List<StaticCodeBook> types = new ArrayList<>();

    private static final AppMessages messages = AppMessages.INSTANCE;

    static
    {
        types.add( new StaticCodeBook( InvoiceType.PROFORMA.name(), messages.labelProforma() ) );
        types.add( new StaticCodeBook( InvoiceType.TAX_DOCUMENT.name(), messages.labelTaxDocument() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return types;
    }
}
