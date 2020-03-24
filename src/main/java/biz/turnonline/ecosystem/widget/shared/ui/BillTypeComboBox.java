package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillTypeComboBox
        extends StaticCodeBookListBox
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static List<StaticCodeBook> types = new ArrayList<>();

    static
    {
        types.add( new StaticCodeBook( "RECEIPT", messages.labelCashRegisterDocument() ) );
        types.add( new StaticCodeBook( "INCOMING_INVOICE", messages.labelIncomingInvoice() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return types;
    }

    @Override
    protected String defaultValue()
    {
        return "RECEIPT";
    }
}
