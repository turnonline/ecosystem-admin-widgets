package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class PaymentMethodComboBox
        extends StaticCodeBookListBox
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static List<StaticCodeBook> types = new ArrayList<>();

    static
    {
        types.add( new StaticCodeBook( PaymentMethod.TRANSFER.name(), messages.labelBankTransfer() ) );
        types.add( new StaticCodeBook( PaymentMethod.CARD_PAYMENT.name(), messages.labelCardPayment() ) );
        types.add( new StaticCodeBook( PaymentMethod.CASH.name(), messages.labelCash() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return types;
    }

    @Override
    protected String defaultValue()
    {
        return PaymentMethod.TRANSFER.name();
    }
}
