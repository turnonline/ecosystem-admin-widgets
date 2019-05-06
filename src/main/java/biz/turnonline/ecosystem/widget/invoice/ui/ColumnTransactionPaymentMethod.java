package biz.turnonline.ecosystem.widget.invoice.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.PaymentMethod;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Transaction;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionPaymentMethod
        extends WidgetColumn<Transaction, MaterialChip>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static Map<String, String> localizationMap = new HashMap<>();

    private static Map<String, IconType> iconTypeMap = new HashMap<>();

    static
    {
        localizationMap.put( PaymentMethod.BANK_TRANSFER.name(), messages.labelBankTransfer() );
        localizationMap.put( PaymentMethod.CASH.name(), messages.labelCash() );
        localizationMap.put( PaymentMethod.CREDIT_CARD.name(), messages.labelCreditCard() );
        localizationMap.put( PaymentMethod.DEBIT_CARD.name(), messages.labelDebitCard() );

        iconTypeMap.put( PaymentMethod.BANK_TRANSFER.name(), IconType.ACCOUNT_BALANCE );
        iconTypeMap.put( PaymentMethod.CASH.name(), IconType.ACCOUNT_BALANCE_WALLET );
        iconTypeMap.put( PaymentMethod.CREDIT_CARD.name(), IconType.PAYMENT );
        iconTypeMap.put( PaymentMethod.DEBIT_CARD.name(), IconType.PAYMENT );
    }

    @Override
    public MaterialChip getValue( Transaction object )
    {
        String form = object.getForm();
        return new MaterialChip(localizationMap.get( form ), iconTypeMap.get( form ));
    }
}
