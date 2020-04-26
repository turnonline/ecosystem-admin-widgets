/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.PaymentMethod;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionPaymentMethod
        extends WidgetColumn<Transaction, MaterialIcon>
{
    private static final Map<String, IconType> iconTypeMap = new HashMap<>();

    static
    {
        iconTypeMap.put( PaymentMethod.TRANSFER.name(), IconType.ACCOUNT_BALANCE );
        iconTypeMap.put( PaymentMethod.CASH.name(), IconType.ACCOUNT_BALANCE_WALLET );
        iconTypeMap.put( PaymentMethod.CARD_PAYMENT.name(), IconType.PAYMENT );
        iconTypeMap.put( PaymentMethod.REFUND.name(), IconType.PAYMENT );
    }

    @Override
    public MaterialIcon getValue( Transaction transaction )
    {
        IconType iconType = iconTypeMap.get( transaction.getType() );
        return new MaterialIcon( iconType == null ? IconType.ACCOUNT_BALANCE : iconType );
    }
}
