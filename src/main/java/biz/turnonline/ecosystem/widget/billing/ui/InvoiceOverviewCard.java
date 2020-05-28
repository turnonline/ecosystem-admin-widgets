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

package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.BillPayment;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.ui.PriceLabel;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
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

import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.NEW;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.valueOf;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType.TAX_DOCUMENT;
import static gwt.material.design.client.constants.Color.BLUE;
import static gwt.material.design.client.constants.Color.GREEN;
import static gwt.material.design.client.constants.Color.GREY;
import static gwt.material.design.client.constants.Color.ORANGE;
import static gwt.material.design.client.constants.Color.RED_DARKEN_2;
import static gwt.material.design.client.constants.IconType.EDIT;
import static gwt.material.design.client.constants.IconType.VISIBILITY;

/**
 * Invoice overview card component.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoiceOverviewCard
        extends Composite
{
    private static InvoiceCardUiBinder binder = GWT.create( InvoiceCardUiBinder.class );

    @UiField
    MaterialImage invoiceImage;

    @UiField
    MaterialLabel title;

    @UiField
    MaterialImage debtorLogo;

    @UiField
    MaterialLabel invoiceNumber;

    @UiField
    MaterialChip type;

    @UiField
    MaterialChip status;

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

    public InvoiceOverviewCard( Invoice invoice, EventBus bus )
    {
        initWidget( binder.createAndBindUi( this ) );

        card.setScrollspy( Invoices.getScrollspy( invoice ) );

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

        // customer as a title
        Customer customer = invoice.getCustomer();
        String name = "";
        if ( customer != null )
        {
            if ( customer.getCompany() )
            {
                name = customer.getBusinessName();
                String logo = customer.getLogoServingUrl();
                if ( !Strings.isNullOrEmpty( logo ) )
                {
                    debtorLogo.setUrl( logo );
                }
            }
            else
            {
                name = customer.getFirstName() + " " + customer.getLastName();
            }
        }
        title.setText( name );

        invoiceNumber.setText( invoice.getInvoiceNumber() );
        type.setBackgroundColor( typeColor( invoice.getType() ) );
        type.setText( typeText( invoice.getType() ) );

        Invoice.Status statusEnum = invoice.getStatus() == null ? NEW : valueOf( invoice.getStatus() );
        status.setBackgroundColor( statusColor( statusEnum ) );
        status.setText( statusText( statusEnum ) );

        // pricing
        BillPayment payment = invoice.getPayment();

        if ( payment == null )
        {
            amountToPay.setText( "0" );
        }
        else
        {
            amountToPay.setValue( payment.getTotalAmount(), invoice.getCurrency() );
        }

        // actions
        if ( NEW == statusEnum )
        {
            editLink.setIconType( EDIT );
        }
        else
        {
            editLink.setIconType( VISIBILITY );
        }

        // action event handlers
        String scrollspyHistoryToken = Invoices.PREFIX + ":" + Invoices.getScrollspy( invoice );

        editLink.addClickHandler( event -> {
            // don't add history record if there is already an another token not managing scrollspy
            if ( Invoices.isCurrentTokenScrollspy() )
            {
                // add record in to history (to manage scrolling to selected card once going back), but don't fire event
                History.newItem( scrollspyHistoryToken, false );
            }
            bus.fireEvent( new EditInvoiceEvent( invoice ) );
        } );

        viewOrder.addClickHandler( event -> {
            // don't add history record if there is already an another token not managing scrollspy
            if ( Invoices.isCurrentTokenScrollspy() )
            {
                // add record in to history (to manage scrolling to selected card once going back), but don't fire event
                History.newItem( scrollspyHistoryToken, false );
            }
            bus.fireEvent( new EditOrderEvent( invoice.getOrderId() ) );
        } );

        if ( hasImageUrl )
        {
            downloadLink.addClickHandler( event ->
            {
                DownloadInvoiceEvent de;
                de = new DownloadInvoiceEvent( invoice.getOrderId(), invoice.getId(), invoice.getPin() );
                bus.fireEvent( de );
            } );
        }
        downloadLink.setVisible( hasImageUrl );
    }

    private Color statusColor( Invoice.Status status )
    {
        switch ( status )
        {
            case NEW:
            {
                return BLUE;
            }
            case SENT:
            {
                return ORANGE;
            }
            case PAID:
            {
                return GREEN;
            }
            case CANCELED:
            {
                return RED_DARKEN_2;
            }
        }

        return GREY;
    }

    private String statusText( Invoice.Status status )
    {
        switch ( status )
        {
            case NEW:
            {
                return messages.descriptionInvoiceStatusNew();
            }
            case SENT:
            {
                return messages.descriptionInvoiceStatusSent();
            }
            case PAID:
            {
                return messages.descriptionInvoiceStatusPaid();
            }
            case CANCELED:
            {
                return messages.descriptionInvoiceStatusCanceled();
            }
        }
        String error = "Unknown invoice status: " + status;
        GWT.log( error );
        throw new IllegalArgumentException( error );
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
            extends UiBinder<MaterialCard, InvoiceOverviewCard>
    {
    }
}