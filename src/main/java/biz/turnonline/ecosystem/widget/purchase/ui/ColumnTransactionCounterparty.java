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
import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionCounterparty
        extends WidgetColumn<Transaction, MaterialColumn>
{
    @Override
    public MaterialColumn getValue( Transaction transaction )
    {
        MaterialColumn content = new MaterialColumn();
        content.setPaddingLeft( 0 );

        Merchant merchant = transaction.getMerchant();

        if ( merchant != null )
        {
            MaterialLabel bankCode = new MaterialLabel( merchant.getName() );
            content.add( bankCode );

            if ( merchant.getCity() != null )
            {
                bankCode.setTooltip( merchant.getCity() );
            }
        }

        String ownerIban = resolveOwnIban( transaction );
        String counterpartyIban = resolveCounterpartyIban( transaction );

        if ( ownerIban != null && counterpartyIban != null )
        {
            MaterialColumn transfer = new MaterialColumn();
            transfer.setPaddingLeft( 0 );
            transfer.setPaddingRight( 0 );
            transfer.setFontSize( 80, Style.Unit.PCT );
            transfer.setTextColor( Color.GREY );
            content.add( transfer );

            MaterialLabel ownerIbanLabel = new MaterialLabel( ownerIban );
            ownerIbanLabel.getElement().getStyle().setDisplay( Style.Display.INLINE_BLOCK );
            ownerIbanLabel.getElement().getStyle().setPosition( Style.Position.RELATIVE );
            ownerIbanLabel.setMarginRight( 5 );
            ownerIbanLabel.setFontWeight( Style.FontWeight.BOLD );
            transfer.add( ownerIbanLabel );

            MaterialIcon direction = new MaterialIcon();
            direction.setIconColor( Color.GREY );
            direction.setIconFontSize( 80, Style.Unit.PCT );
            direction.setIconType( transaction.isCredit() ? IconType.ARROW_BACK : IconType.ARROW_FORWARD );
            direction.setMarginRight( 5 );
            transfer.add( direction );

            MaterialLabel counterpartyIbanLabel = new MaterialLabel( counterpartyIban );
            counterpartyIbanLabel.getElement().getStyle().setDisplay( Style.Display.INLINE_BLOCK );
            counterpartyIbanLabel.getElement().getStyle().setPosition( Style.Position.RELATIVE );
            counterpartyIbanLabel.setTooltip( transaction.getCounterparty().getName() );
            transfer.add( counterpartyIbanLabel );
        }

        return content;
    }

    private String resolveOwnIban( Transaction transaction )
    {
        return transaction.getBankAccount().getIban();
    }

    private String resolveCounterpartyIban( Transaction transaction )
    {
        return transaction.getCounterparty() != null ? transaction.getCounterparty().getIban() : null;
    }
}
