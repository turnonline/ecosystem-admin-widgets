/*
 * Copyright (c) 2020 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PaymentMethod;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
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
        localizationMap.put( PaymentMethod.TRANSFER.name(), messages.labelBankTransfer() );
        localizationMap.put( PaymentMethod.CASH.name(), messages.labelCash() );
        localizationMap.put( PaymentMethod.CARD_PAYMENT.name(), messages.labelCardPayment() );

        iconTypeMap.put( PaymentMethod.TRANSFER.name(), IconType.ACCOUNT_BALANCE );
        iconTypeMap.put( PaymentMethod.CASH.name(), IconType.ACCOUNT_BALANCE_WALLET );
        iconTypeMap.put( PaymentMethod.CARD_PAYMENT.name(), IconType.PAYMENT );
    }

    @Override
    public MaterialChip getValue( Transaction object )
    {
        String form = object.getForm();
        return new MaterialChip( localizationMap.get( form ), iconTypeMap.get( form ) );
    }
}
