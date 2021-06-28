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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.PaymentMethod;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.InlineHTML;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionAmount
        extends WidgetColumn<Transaction, MaterialColumn>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static final Map<String, IconType> iconTypeMap = new HashMap<>();
    private static final Map<String, String> iconTypeTextMap = new HashMap<>();

    static
    {
        iconTypeMap.put( PaymentMethod.TRANSFER.name(), IconType.ACCOUNT_BALANCE );
        iconTypeMap.put( PaymentMethod.CASH.name(), IconType.ACCOUNT_BALANCE_WALLET );
        iconTypeMap.put( PaymentMethod.CARD_PAYMENT.name(), IconType.PAYMENT );
        iconTypeMap.put( PaymentMethod.REFUND.name(), IconType.PAYMENT );

        iconTypeTextMap.put( PaymentMethod.TRANSFER.name(), messages.labelPaymentMethodTransfer() );
        iconTypeTextMap.put( PaymentMethod.CASH.name(), messages.labelPaymentMethodCash() );
        iconTypeTextMap.put( PaymentMethod.CARD_PAYMENT.name(), messages.labelPaymentMethodCardPayment() );
        iconTypeTextMap.put( PaymentMethod.REFUND.name(), messages.labelPaymentMethodRefund() );
    }

    @Override
    public MaterialColumn getValue( Transaction object )
    {
        MaterialColumn content = new MaterialColumn();
        renderAmount( content, object );

        return content;
    }

    public static void renderAmount( MaterialWidget parent, Transaction transaction )
    {
        parent.clear();
        parent.setPaddingLeft( 0 );

        MaterialColumn amountParent = new MaterialColumn();
        amountParent.setGrid( "s12 m12" );
        amountParent.setMarginBottom( 10 );
        amountParent.setPaddingLeft( 0 );
        parent.add( amountParent );

        MaterialColumn completedAtParent = new MaterialColumn();
        completedAtParent.setGrid( "s12 m12" );
        completedAtParent.setFontSize( 80, Style.Unit.PCT );
        completedAtParent.setPaddingLeft( 0 );
        parent.add( completedAtParent );

        boolean credit = Optional.ofNullable( transaction.isCredit() ).orElse( false );
        Double amount = transaction.getAmount();
        String currency = transaction.getCurrency();

        if ( currency != null && amount != null )
        {
            // type icon
            IconType iconType = iconTypeMap.get( transaction.getType() );
            String tooltip = iconTypeTextMap.get( transaction.getType() );

            MaterialIcon icon = new MaterialIcon( iconType == null ? IconType.ACCOUNT_BALANCE : iconType );
            icon.setTooltip( tooltip == null ? transaction.getType() : tooltip );
            icon.getElement().getStyle().setPosition( Style.Position.RELATIVE );
            icon.getElement().getStyle().setTop( 7, Style.Unit.PX );
            icon.getElement().getStyle().setMarginRight( 5, Style.Unit.PX );
            amountParent.add( icon );

            // formatted amount
            String formatted = NumberFormat.getCurrencyFormat( currency ).format( amount );

            InlineHTML amountHtml = new InlineHTML();
            amountHtml.setHTML( ( credit ? "+" : "-" ) + formatted );
            amountParent.add( amountHtml );

            amountParent.removeStyleName( "green-text" );
            amountParent.removeStyleName( "red-text" );
            amountParent.addStyleName( credit ? "green-text" : "red-text" );
        }

        Date completedAt = transaction.getCompletedAt();
        if ( completedAt != null )
        {
            MaterialIcon icon = new MaterialIcon( IconType.DATE_RANGE );
            icon.setIconColor( Color.GREY );
            icon.setFontSize( 100, Style.Unit.PCT );
            icon.setLayoutPosition( Style.Position.RELATIVE );
            icon.setTop( -5 );
            completedAtParent.add( icon );

            DateTimeFormat format = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM );
            MaterialLabel label = new MaterialLabel( format.format( completedAt ) );
            label.getElement().getStyle().setDisplay( Style.Display.INLINE_BLOCK );
            label.getElement().getStyle().setPosition( Style.Position.RELATIVE );
            label.getElement().getStyle().setTop( -6, Style.Unit.PX );
            label.setTextColor( Color.GREY );
            completedAtParent.add( label );
        }
    }
}
