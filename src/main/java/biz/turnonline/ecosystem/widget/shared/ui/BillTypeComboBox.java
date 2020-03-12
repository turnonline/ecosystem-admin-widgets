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
    private static List<StaticCodeBook> types = new ArrayList<>();

    private static final AppMessages messages = AppMessages.INSTANCE;

    static
    {
        types.add( new StaticCodeBook( "CASH_REGISTER_DOCUMENT", messages.labelCashRegisterDocument() ) );
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
        return "CASH_REGISTER_DOCUMENT";
    }
}
