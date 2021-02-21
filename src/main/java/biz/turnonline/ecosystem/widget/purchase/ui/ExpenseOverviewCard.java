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

import biz.turnonline.ecosystem.widget.purchase.event.DownloadReceiptEvent;
import biz.turnonline.ecosystem.widget.purchase.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.purchase.event.IncomingInvoiceDetailsEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderDetailEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Expenses;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.billing.BillPayment;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Creditor;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Expense;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ExpenseThrough;
import biz.turnonline.ecosystem.widget.shared.ui.PriceLabel;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

import static gwt.material.design.client.constants.Color.BLACK;
import static gwt.material.design.client.constants.Color.BLUE_GREY_DARKEN_2;
import static gwt.material.design.client.constants.Color.BROWN_LIGHTEN_2;
import static gwt.material.design.client.constants.Color.GREEN;
import static gwt.material.design.client.constants.Color.GREY;
import static gwt.material.design.client.constants.Color.RED_DARKEN_2;
import static gwt.material.design.client.constants.Color.TEAL_LIGHTEN_2;
import static gwt.material.design.client.constants.Color.WHITE;
import static gwt.material.design.client.constants.IconType.ASSIGNMENT;
import static gwt.material.design.client.constants.IconType.RECEIPT;

/**
 * Expense overview card component. Covers the rendering of these expense types:
 * <ul>
 *     <li>Receipt</li>
 *     <li>Invoice</li>
 * </ul>
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 * @see biz.turnonline.ecosystem.widget.shared.rest.bill.Bill.TypeEnum
 */
public class ExpenseOverviewCard
        extends Composite
{
    private static final InvoiceCardUiBinder binder = GWT.create( InvoiceCardUiBinder.class );

    private static final DateTimeFormat FORMATTER = DateTimeFormat.getFormat( "dd MMMM yyyy" );

    private final AppMessages messages = AppMessages.INSTANCE;

    @UiField
    MaterialImage invoiceImage;

    @UiField
    MaterialLabel title;

    @UiField
    MaterialImage creditorLogo;

    @UiField
    MaterialLabel billNumber;

    @UiField
    MaterialChip type;

    @UiField
    MaterialChip dueDate;

    @UiField
    PriceLabel amountToPay;

    @UiField
    MaterialLabel totalPriceLabel;

    @UiField
    MaterialLink viewBill;

    @UiField
    MaterialLink viewOrder;

    @UiField
    MaterialLink downloadLink;

    @UiField
    MaterialCard card;

    @UiField
    MaterialOverlay overlay;

    @UiField
    MaterialImage overlayImage;

    @UiField
    MaterialButton btnCloseOverlay;

    @UiField
    MaterialIcon through;

    public ExpenseOverviewCard( Expense expense, EventBus bus )
    {
        initWidget( binder.createAndBindUi( this ) );

        card.setScrollspy( Expenses.getScrollspy( expense ) );

        Bill bill = expense.getBill();
        boolean isInvoiceType = isInvoiceType( bill );

        if ( ExpenseThrough.EXTERNAL.name().equals( expense.getThrough() ) )
        {
            through.setIconType( IconType.MOOD_BAD );
            through.setIconColor( GREY );
            through.setTooltip( messages.tooltipPurchaseEcosystemOutside() );
        }
        else if ( ExpenseThrough.ECOSYSTEM.name().equals( expense.getThrough() ) )
        {
            type.setIconType( IconType.EXPLICIT );
            through.setIconColor( BLUE_GREY_DARKEN_2 );
            through.setTooltip( messages.tooltipPurchaseEcosystemInside() );
        }

        // bill image
        boolean hasImageUrl = expense.getServingUrl() != null;
        if ( hasImageUrl )
        {
            invoiceImage.setUrl( expense.getServingUrl() );
            invoiceImage.addClickHandler( e -> overlay.open( invoiceImage ) );
            overlayImage.setUrl( expense.getServingUrl() + "=s1200" );
            btnCloseOverlay.addClickHandler( e -> overlay.close() );
            overlay.addClickHandler( event -> overlay.close() );
        }
        else
        {
            invoiceImage.setVisible( false );
        }

        // creditor as a title
        Creditor creditor = expense.getSupplier();
        String name = "";
        if ( creditor != null )
        {
            name = creditor.getBusinessName();
            String logo = creditor.getLogoServingUrl();
            if ( !Strings.isNullOrEmpty( logo ) )
            {
                creditorLogo.setUrl( logo );
            }
        }
        title.setText( name );

        billNumber.setText( expense.getBillNumber() );
        type.setIconType( billIcon( bill ) );
        type.setIconPosition( IconPosition.LEFT );
        type.setTextColor( billType( bill ) );
        type.setBackgroundColor( WHITE );
        type.setBorder( "1px solid" );
        type.setText( billText( bill ) );

        // pricing
        BillPayment payment = expense.getPayment();

        if ( !isInvoiceType )
        {
            amountToPay.setValue( expense.getTotalPrice(), expense.getCurrency() );
            totalPriceLabel.setValue( messages.labelTotalPrice() );

            Date dateOfIssue = expense.getDateOfIssue();
            dueDate.setText( dateOfIssue == null ? "none" : FORMATTER.format( dateOfIssue ) );
            dueDate.setBackgroundColor( WHITE );
            dueDate.setTextColor( GREEN );
            dueDate.setBorder( "1px solid" );
            dueDate.setVisible( dateOfIssue != null );
        }
        else if ( payment == null )
        {
            amountToPay.setText( "0" );
            dueDate.setVisible( false );
        }
        else
        {
            amountToPay.setValue( payment.getTotalAmount(), expense.getCurrency() );
            Date due = payment.getDueDate();

            dueDate.setText( due == null ? "none" : FORMATTER.format( due ) );
            dueDate.setBackgroundColor( dueDateColor( payment ) );
            dueDate.setVisible( due != null );

            if ( dueDate.getBackgroundColor() == WHITE )
            {
                dueDate.setTextColor( BLACK );
                dueDate.setBorder( "1px solid black" );
            }
            else
            {
                dueDate.setTextColor( WHITE );
            }
        }

        // action event handlers
        String scrollspyHistoryToken = Expenses.PREFIX + ":" + Expenses.getScrollspy( expense );

        Long orderId = bill == null ? null : bill.getOrder();
        Long invoiceId = bill == null ? null : bill.getInvoice();
        Long billId = bill == null ? null : bill.getId();

        viewBill.addClickHandler( event -> {
            // don't add history record if there is already an another token not managing scrollspy
            if ( Expenses.isCurrentTokenScrollspy() )
            {
                // add record in to history (to manage scrolling to selected card once going back), but don't fire event
                History.newItem( scrollspyHistoryToken, false );
            }
            if ( orderId != null && invoiceId != null )
            {
                bus.fireEvent( new IncomingInvoiceDetailsEvent( orderId, invoiceId ) );
            }
            else if ( billId != null )
            {
                bus.fireEvent( new EditBillEvent( billId ) );
            }
        } );

        viewOrder.addClickHandler( event -> {
            // don't add history record if there is already an another token not managing scrollspy
            if ( Expenses.isCurrentTokenScrollspy() )
            {
                // add record in to history (to manage scrolling to selected card once going back), but don't fire event
                History.newItem( scrollspyHistoryToken, false );
            }
            if ( orderId != null )
            {
                bus.fireEvent( new PurchaseOrderDetailEvent( orderId ) );
            }
        } );

        if ( hasImageUrl )
        {
            String pin = expense.getPin();
            if ( ( orderId != null && invoiceId != null && !Strings.isNullOrEmpty( pin ) ) )
            {
                boolean isAccount = creditor != null && creditor.getAccount() != null;
                DownloadInvoiceEvent downloadEvent = new DownloadInvoiceEvent( orderId, invoiceId, pin, !isAccount );

                downloadLink.addClickHandler( event -> bus.fireEvent( downloadEvent ) );
            }
            else if ( bill.getReceipt() != null && !Strings.isNullOrEmpty( pin ) )
            {
                DownloadReceiptEvent downloadEvent = new DownloadReceiptEvent( bill.getReceipt(), pin );
                downloadLink.addClickHandler( event -> bus.fireEvent( downloadEvent ) );
            }
        }
        downloadLink.setVisible( hasImageUrl );
        viewOrder.setVisible( isInvoiceType );

        if ( isInvoiceType )
        {
            viewBill.setTooltip( messages.tooltipPurchaseInvoiceView() );
        }
        else
        {
            viewBill.setTooltip( messages.tooltipPurchaseReceiptView() );
        }
    }

    private Color dueDateColor( @Nonnull BillPayment payment )
    {
        Date date = payment.getDueDate();
        Double totalAmount = payment.getTotalAmount();

        if ( totalAmount != null && totalAmount <= 0.0 )
        {
            return GREEN;
        }

        if ( date == null )
        {
            return GREY;
        }
        else if ( date.after( new Date() ) )
        {
            return WHITE;
        }
        else
        {
            return RED_DARKEN_2;
        }
    }

    private IconType billIcon( Bill bill )
    {
        if ( isInvoiceType( bill ) )
        {
            return ASSIGNMENT;
        }
        else
        {
            return RECEIPT;
        }
    }

    private Color billType( Bill bill )
    {
        if ( isInvoiceType( bill ) )
        {
            return TEAL_LIGHTEN_2;
        }
        else
        {
            // Receipt type
            return BROWN_LIGHTEN_2;
        }
    }

    private String billText( Bill bill )
    {
        if ( isInvoiceType( bill ) )
        {
            return messages.labelInvoice();
        }
        else
        {
            return messages.labelReceipt();
        }
    }

    private boolean isInvoiceType( @Nullable Bill bill )
    {
        if ( bill == null )
        {
            return false;
        }

        return bill.getOrder() != null && bill.getInvoice() != null && bill.getReceipt() == null;
    }

    interface InvoiceCardUiBinder
            extends UiBinder<MaterialCard, ExpenseOverviewCard>
    {
    }
}