/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.billing.view;

import biz.turnonline.ecosystem.widget.billing.event.DownloadInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.billing.presenter.InvoicesPresenter;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePayment;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollLoader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.NEW;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.valueOf;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType.TAX_DOCUMENT;
import static biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel.formatPrice;
import static gwt.material.design.client.constants.Color.BLUE;
import static gwt.material.design.client.constants.Color.GREEN;
import static gwt.material.design.client.constants.Color.GREY;
import static gwt.material.design.client.constants.Color.ORANGE;
import static gwt.material.design.client.constants.Color.RED;
import static gwt.material.design.client.constants.Color.WHITE;
import static gwt.material.design.client.constants.IconType.CLOUD_DOWNLOAD;
import static gwt.material.design.client.constants.IconType.EDIT;
import static gwt.material.design.client.constants.IconType.VISIBILITY;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoicesView
        extends View
        implements InvoicesPresenter.IView
{
    private static InvoicesViewUiBinder binder = GWT.create( InvoicesViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    InfiniteScroll<Invoice> scroll;

    private int headerHeight;

    @Inject
    public InvoicesView( EventBus eventBus, @Named( "InvoicesBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        setActive( Route.INVOICES );

        add( binder.createAndBindUi( this ) );

        scroll.setRenderer( this::createWidget );
        scroll.setInfiniteScrollLoader( new InfiniteScrollLoader( messages.labelInvoiceLoading() ) );

        Window.addResizeHandler( event -> scroll.setHeight( ( event.getHeight() - headerHeight ) + "px" ) );
        Scheduler.get().scheduleDeferred( () -> {
            headerHeight = scaffoldHeader.getElement().getClientHeight() + breadcrumb.getElement().getClientHeight();
            scroll.setHeight( ( Window.getClientHeight() - headerHeight ) + "px" );
        } );
    }

    @Override
    public void scrollTo( @Nullable String scrollspy )
    {
        scroll.scrollTo( scrollspy );
    }

    @Override
    public void setDataSource( InfiniteScroll.Callback<Invoice> callback )
    {
        scroll.unload();
        scroll.setDataSource( callback );
    }

    private MaterialWidget createWidget( Invoice invoice )
    {
        MaterialCard card = new MaterialCard();
        card.setScrollspy( invoice.getScrollspy() );
        card.setMinHeight( "266px" );
        card.setDetectOrientation( true );
        card.setMarginTop( 10 );
        card.setPaddingBottom( 0 );

        boolean hasImageUrl = invoice.getServingUrl() != null;
        if ( hasImageUrl )
        {
            MaterialCardImage cardImage = new MaterialCardImage();
            card.add( cardImage );

            MaterialImage image = new MaterialImage( invoice.getServingUrl() );

            cardImage.setWaves( WavesType.LIGHT );
            cardImage.add( image );
        }

        MaterialCardContent content = new MaterialCardContent();
        content.setMaxHeight( "200px" );
        content.setPaddingBottom( 0 );
        card.add( content );

        // customer as a title
        Customer customer = invoice.getCustomer();
        String name = "";
        if ( customer != null )
        {
            if ( customer.getCompany() )
            {
                name = customer.getBusinessName();
            }
            else
            {
                name = customer.getFirstName() + " " + customer.getLastName();
            }
        }

        MaterialRow titleRow = new MaterialRow();
        MaterialColumn titleColumn = new MaterialColumn( 12, 12, 12 );
        titleColumn.setPaddingLeft( 0 );
        titleRow.add( titleColumn );
        content.add( titleColumn );

        MaterialLabel title = new MaterialLabel( name );
        title.setFontSize( "1.5em" );
        title.setFontWeight( Style.FontWeight.BOLD );
        titleColumn.add( title );

        // first column
        MaterialColumn firstColumn = new MaterialColumn( 12, 6, 6 );
        firstColumn.setPaddingTop( 8 );
        firstColumn.setPaddingLeft( 0 );
        content.add( firstColumn );

        MaterialLabel invoiceNumber = new MaterialLabel( invoice.getInvoiceNumber() );
        invoiceNumber.setFontWeight( Style.FontWeight.BOLD );
        firstColumn.add( invoiceNumber );

        MaterialRow firstColumnRow1 = new MaterialRow();
        firstColumnRow1.setPaddingTop( 10 );
        MaterialRow firstColumnRow2 = new MaterialRow();

        firstColumn.add( firstColumnRow1 );
        firstColumn.add( firstColumnRow2 );

        // invoice type
        MaterialChip typeChip = new MaterialChip();
        typeChip.setBackgroundColor( typeColor( invoice.getType() ) );
        typeChip.setText( typeText( invoice.getType() ) );
        typeChip.setTextColor( WHITE );
        firstColumnRow1.add( typeChip );

        // invoice status
        Invoice.Status status = invoice.getStatus() == null ? NEW : valueOf( invoice.getStatus() );
        MaterialChip statusChip = new MaterialChip();
        statusChip.setBackgroundColor( statusColor( status ) );
        statusChip.setText( statusText( status ) );
        statusChip.setTextColor( WHITE );
        firstColumnRow2.add( statusChip );

        // second column
        MaterialColumn secondColumn = new MaterialColumn( 12, 6, 6 );
        secondColumn.setPaddingTop( 8 );
        secondColumn.setPaddingLeft( 0 );
        content.add( secondColumn );

        MaterialRow secondColumnRow1 = new MaterialRow();
        MaterialRow secondColumnRow2 = new MaterialRow();
        secondColumn.add( secondColumnRow1 );
        secondColumn.add( secondColumnRow2 );

        // pricing
        secondColumnRow1.add( new MaterialLabel( messages.labelAmountToPay() ) );

        String toPay;
        InvoicePayment payment = invoice.getPayment();

        if ( payment != null )
        {
            String currency = invoice.getCurrency();
            Double totalAmount = payment.getTotalAmount();

            if ( currency != null )
            {
                if ( totalAmount != null )
                {
                    toPay = formatPrice( currency, totalAmount );
                }
                else
                {
                    toPay = formatPrice( currency, 0.0 );
                }
            }
            else
            {
                toPay = totalAmount == null ? "0" : totalAmount.toString();
            }
        }
        else
        {
            toPay = "0";
        }

        MaterialLabel toPayAsText = new MaterialLabel( toPay );
        toPayAsText.setFontSize( "1.2em" );
        toPayAsText.setFontWeight( Style.FontWeight.BOLD );
        secondColumnRow2.add( toPayAsText );

        // card's action buttons
        MaterialLink edit = new MaterialLink();
        edit.setIconColor( BLUE );
        edit.setPaddingTop( 10 );
        edit.addClickHandler( event -> {
            // add record in to history (to manage scrolling to selected card once going back), but do not fire event
            History.newItem( Invoices.PREFIX + ":" + invoice.getScrollspy(), false );
            bus().fireEvent( new EditInvoiceEvent( invoice ) );
        } );

        if ( NEW == status )
        {
            edit.setIconType( EDIT );
        }
        else
        {
            edit.setIconType( VISIBILITY );
        }

        MaterialLink download = null;
        if ( hasImageUrl )
        {
            download = new MaterialLink( CLOUD_DOWNLOAD );
            download.setIconColor( ORANGE );
            download.setPaddingTop( 10 );
            download.addClickHandler( event ->
            {
                DownloadInvoiceEvent de;
                de = new DownloadInvoiceEvent( invoice.getOrderId(), invoice.getId(), invoice.getPin() );
                bus().fireEvent( de );
            } );
        }

        MaterialCardAction actions = new MaterialCardAction();
        actions.setMarginTop( 0 );
        actions.setPadding( 5 );
        MaterialRow actionsRow = new MaterialRow();
        actionsRow.setMarginBottom( 0 );
        MaterialColumn actionsColumn = new MaterialColumn( 12, 12, 12 );
        actionsColumn.add( actions );
        actionsRow.add( actionsColumn );
        card.add( actionsRow );

        actions.add( edit );
        if ( download != null )
        {
            actions.add( download );
        }

        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
        column.add( card );
        return column;
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
                return RED;
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

    interface InvoicesViewUiBinder
            extends UiBinder<HTMLPanel, InvoicesView>
    {
    }
}