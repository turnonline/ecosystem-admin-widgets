/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.purchase.event.IncomingInvoiceDetailsEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderDetailEvent;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoices;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Creditor;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePayment;
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
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

import javax.annotation.Nullable;
import java.util.Date;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType.TAX_DOCUMENT;
import static gwt.material.design.client.constants.Color.BLACK;
import static gwt.material.design.client.constants.Color.BLUE;
import static gwt.material.design.client.constants.Color.GREEN;
import static gwt.material.design.client.constants.Color.GREY;
import static gwt.material.design.client.constants.Color.RED_DARKEN_2;
import static gwt.material.design.client.constants.Color.WHITE;

/**
 * Incoming invoice (purchases) overview card component.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceOverviewCard
        extends Composite
{
    private static InvoiceCardUiBinder binder = GWT.create( InvoiceCardUiBinder.class );

    private static DateTimeFormat FORMATTER = DateTimeFormat.getFormat( "dd MMMM yyyy" );

    @UiField
    MaterialImage invoiceImage;

    @UiField
    MaterialLabel title;

    @UiField
    MaterialImage creditorLogo;

    @UiField
    MaterialLabel invoiceNumber;

    @UiField
    MaterialChip type;

    @UiField
    MaterialChip dueDate;

    @UiField
    PriceLabel amountToPay;

    @UiField
    MaterialLink editLink;

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

    private AppMessages messages = AppMessages.INSTANCE;

    public IncomingInvoiceOverviewCard( IncomingInvoice invoice, EventBus bus )
    {
        initWidget( binder.createAndBindUi( this ) );

        card.setScrollspy( IncomingInvoices.getScrollspy( invoice ) );

        // invoice image
        boolean hasImageUrl = invoice.getServingUrl() != null;
        if ( hasImageUrl )
        {
            invoiceImage.setUrl( invoice.getServingUrl() );
            invoiceImage.addClickHandler( e -> overlay.open( invoiceImage ) );
            overlayImage.setUrl( invoice.getServingUrl() + "=s1200" );
            btnCloseOverlay.addClickHandler( e -> overlay.close() );
            overlay.addClickHandler( event -> overlay.close() );
        }
        else
        {
            invoiceImage.setVisible( false );
        }

        // creditor as a title
        Creditor creditor = invoice.getCreditor();
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

        invoiceNumber.setText( invoice.getInvoiceNumber() );
        type.setBackgroundColor( typeColor( invoice.getType() ) );
        type.setText( typeText( invoice.getType() ) );

        // pricing
        InvoicePayment payment = invoice.getPayment();

        if ( payment == null )
        {
            amountToPay.setText( "0" );
            dueDate.setVisible( false );
        }
        else
        {
            amountToPay.setValue( payment.getTotalAmount(), invoice.getCurrency() );
            Date due = payment.getDueDate();

            dueDate.setText( due == null ? "none" : FORMATTER.format( due ) );
            dueDate.setBackgroundColor( dueDateColor( due ) );

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
        String scrollspyHistoryToken = IncomingInvoices.PREFIX + ":" + IncomingInvoices.getScrollspy( invoice );

        editLink.addClickHandler( event -> {
            // don't add history record if there is already an another token not managing scrollspy
            if ( IncomingInvoices.isCurrentTokenScrollspy() )
            {
                // add record in to history (to manage scrolling to selected card once going back), but don't fire event
                History.newItem( scrollspyHistoryToken, false );
            }
            bus.fireEvent( new IncomingInvoiceDetailsEvent( invoice ) );
        } );

        viewOrder.addClickHandler( event -> {
            // don't add history record if there is already an another token not managing scrollspy
            if ( IncomingInvoices.isCurrentTokenScrollspy() )
            {
                // add record in to history (to manage scrolling to selected card once going back), but don't fire event
                History.newItem( scrollspyHistoryToken, false );
            }
            bus.fireEvent( new PurchaseOrderDetailEvent( invoice.getOrderId() ) );
        } );

        if ( hasImageUrl )
        {
            downloadLink.addClickHandler( event ->
                    bus.fireEvent( new DownloadInvoiceEvent( invoice.getOrderId(), invoice.getId(), invoice.getPin() ) ) );
        }
        downloadLink.setVisible( hasImageUrl );
    }

    private Color dueDateColor( @Nullable Date date )
    {
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

    private Color typeColor( String type )
    {
        if ( type.equalsIgnoreCase( TAX_DOCUMENT.name() ) )
        {
            return GREEN;
        }

        return BLUE;
    }

    private String typeText( String type )
    {
        if ( type.equalsIgnoreCase( TAX_DOCUMENT.name() ) )
        {
            return messages.labelTaxDocument();
        }

        return messages.labelProforma();
    }

    interface InvoiceCardUiBinder
            extends UiBinder<MaterialCard, IncomingInvoiceOverviewCard>
    {
    }
}