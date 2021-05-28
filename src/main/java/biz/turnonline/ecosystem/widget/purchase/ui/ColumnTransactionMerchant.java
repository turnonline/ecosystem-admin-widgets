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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Merchant;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionMerchant
        extends WidgetColumn<Transaction, MaterialColumn>
{
    @Override
    public MaterialColumn getValue( Transaction object )
    {
        MaterialColumn content = new MaterialColumn();
        content.setPaddingLeft( 0 );

        Merchant merchant = object.getMerchant();

        if ( merchant != null )
        {
            MaterialLabel bankCode = new MaterialLabel( merchant.getName() );
            content.add( bankCode );

            if (merchant.getCity() != null) {
                bankCode.setTooltip( merchant.getCity() );
            }
        }

        return content;
    }
}
