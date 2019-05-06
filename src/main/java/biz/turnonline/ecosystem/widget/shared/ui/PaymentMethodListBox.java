package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class PaymentMethodListBox
        extends StaticCodeBookListBox
{
    private static List<StaticCodeBook> types = new ArrayList<>();

    private static final AppMessages messages = AppMessages.INSTANCE;

    static
    {
        types.add( new StaticCodeBook( PaymentMethod.BANK_TRANSFER.name(), messages.labelBankTransfer() ) );
        types.add( new StaticCodeBook( PaymentMethod.CASH.name(), messages.labelCash() ) );
        types.add( new StaticCodeBook( PaymentMethod.CREDIT_CARD.name(), messages.labelCreditCard() ) );
        types.add( new StaticCodeBook( PaymentMethod.DEBIT_CARD.name(), messages.labelDebitCard() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return types;
    }
}
