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
import biz.turnonline.ecosystem.widget.billing.presenter.InvoicesPresenter;
import biz.turnonline.ecosystem.widget.billing.ui.InvoicesDataSource;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.ImageType;
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
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollPanel;
import gwt.material.design.incubator.client.infinitescroll.data.LoadConfig;
import gwt.material.design.incubator.client.infinitescroll.recycle.RecycleManager;
import gwt.material.design.incubator.client.infinitescroll.recycle.RecycleType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.NEW;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.valueOf;
import static biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel.formatPrice;
import static gwt.material.design.client.constants.Color.BLUE;
import static gwt.material.design.client.constants.Color.GREEN;
import static gwt.material.design.client.constants.Color.GREY;
import static gwt.material.design.client.constants.Color.ORANGE;
import static gwt.material.design.client.constants.Color.RED;
import static gwt.material.design.client.constants.Color.WHITE;
import static gwt.material.design.client.constants.IconType.EDIT;
import static gwt.material.design.client.constants.IconType.FILE_DOWNLOAD;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoicesView
        extends View
        implements InvoicesPresenter.IView
{
    private static InvoicesViewUiBinder binder = GWT.create( InvoicesViewUiBinder.class );

    private static Map<Invoice.Status, Color> colorMap = new HashMap<>();

    static
    {
        colorMap.put( Invoice.Status.NEW, BLUE );
        colorMap.put( Invoice.Status.SENT, ORANGE );
        colorMap.put( Invoice.Status.CANCELED, RED );
        colorMap.put( Invoice.Status.PAID, GREEN );
    }

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    InfiniteScrollPanel<Invoice> scroll;

    private int headerHeight;

    @Inject
    public InvoicesView( EventBus eventBus, @Named( "InvoicesBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        setActive( Route.INVOICES );

        add( binder.createAndBindUi( this ) );

        scroll.setLoadConfig( new LoadConfig<>( 0, 20 ) );
        scroll.setDataSource( new InvoicesDataSource( ( AppEventBus ) eventBus ) );
        scroll.setRenderer( this::createWidget );
        scroll.setInfiniteScrollLoader( new InfiniteScrollLoader( "Please wait while we are getting your data" ) );
        scroll.setRecycleManager( new RecycleManager( RecycleType.DETACH ) );

        Window.addResizeHandler( event -> GWT.log( "Height: " + event.getHeight() ) );
        Window.addResizeHandler( event -> scroll.setHeight( ( event.getHeight() - headerHeight ) + "px" ) );

        Scheduler.get().scheduleDeferred( () -> {
            headerHeight = scaffoldHeader.getElement().getClientHeight() + breadcrumb.getElement().getClientHeight();
            scroll.setHeight( ( Window.getClientHeight() - headerHeight ) + "px" );
        } );
    }

    @Override
    public void refresh()
    {
        scroll.reload();
    }

    private MaterialWidget createWidget( Invoice invoice )
    {
        MaterialCard card = new MaterialCard();
        card.setMinHeight( "240px" );
        card.setDetectOrientation( true );
        card.setMarginTop( 10 );
        card.setPaddingBottom( 0 );

        if ( invoice.getServingUrl() != null )
        {
            MaterialCardImage cardImage = new MaterialCardImage();
            card.add( cardImage );

            MaterialImage image = new MaterialImage( invoice.getServingUrl(), ImageType.MATERIALBOXED );
            image.setMinHeight( "220px" );

            cardImage.setWaves( WavesType.LIGHT );
            cardImage.add( image );
        }

        MaterialLabel title = new MaterialLabel( invoice.getInvoiceNumber() );
        title.setFontWeight( Style.FontWeight.BOLD );
        title.setFontSize( "1.2em" );

        MaterialCardContent content = new MaterialCardContent();
        card.add( content );
        content.add( title );

        MaterialColumn firstColumn = new MaterialColumn();
        firstColumn.setPaddingLeft( 0 );
        content.add( firstColumn );

        Customer customer = invoice.getCustomer();
        if ( customer != null )
        {
            String name;
            if ( customer.getCompany() )
            {
                name = customer.getBusinessName();
            }
            else
            {
                name = customer.getFirstName() + " " + customer.getLastName();
            }
            MaterialLabel nameText = new MaterialLabel( name );
            nameText.setFontWeight( Style.FontWeight.BOLD );
            nameText.setPaddingTop( 8 );
            firstColumn.add( nameText );
        }

        // second column
        MaterialColumn secondColumn = new MaterialColumn();
        secondColumn.setPaddingTop( 8 );
        secondColumn.setPaddingLeft( 20 );
        content.add( secondColumn );

        MaterialRow secondColumnRow1 = new MaterialRow();
        MaterialRow secondColumnRow2 = new MaterialRow();
        secondColumn.add( secondColumnRow1 );
        secondColumn.add( secondColumnRow2 );

        // invoice type
        MaterialChip typeChip = new MaterialChip();
        typeChip.setBackgroundColor( typeColor( invoice.getType() ) );
        typeChip.setText( typeText( invoice.getType() ) );
        typeChip.setTextColor( WHITE );
        secondColumnRow1.add( typeChip );

        // invoice status
        Invoice.Status status = invoice.getStatus() == null ? NEW : valueOf( invoice.getStatus() );
        MaterialChip statusChip = new MaterialChip();
        statusChip.setBackgroundColor( statusColor( status ) );
        statusChip.setText( statusText( status ) );
        statusChip.setTextColor( WHITE );
        secondColumnRow2.add( statusChip );

        // third column
        MaterialColumn thirdColumn = new MaterialColumn();
        thirdColumn.setPaddingTop( 8 );
        thirdColumn.setPaddingLeft( 20 );
        content.add( thirdColumn );

        MaterialRow thirdColumnRow1 = new MaterialRow();
        MaterialRow thirdColumnRow2 = new MaterialRow();
        thirdColumn.add( thirdColumnRow1 );
        thirdColumn.add( thirdColumnRow2 );

        // pricing
        thirdColumnRow1.add( new MaterialLabel( messages.labelPriceExcludingVat() ) );

        String excludingVat;
        InvoicePricing pricing = invoice.getPricing();
        // TODO currency for now is hardcoded
        String currency = "EUR";

        if ( pricing != null )
        {
            Double priceExclVat = pricing.getTotalPriceExclVat();
            if ( priceExclVat != null )
            {
                excludingVat = formatPrice( currency, priceExclVat );
            }
            else
            {
                excludingVat = formatPrice( currency, 0.0 );
            }
        }
        else
        {
            excludingVat = "0";
        }

        thirdColumnRow2.add( new MaterialLabel( excludingVat ) );

        // action buttons
        MaterialLink edit = new MaterialLink( EDIT );
        edit.setIconColor( BLUE );
        edit.setPaddingTop( 10 );
        edit.addClickHandler( event ->
                bus().fireEvent( new EditInvoiceEvent( invoice ) ) );

        MaterialLink download = new MaterialLink( FILE_DOWNLOAD );
        download.setIconColor( ORANGE );
        download.setPaddingTop( 10 );
        download.addClickHandler( event ->
                bus().fireEvent( new DownloadInvoiceEvent( invoice.getOrderId(), invoice.getId(), invoice.getPin() ) ) );

        MaterialCardAction actions = new MaterialCardAction();
        card.add( actions );

        actions.add( edit );
        actions.add( download );

        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
        column.add( card );
        return column;
    }

    private Color statusColor( Invoice.Status status )
    {
        if ( colorMap.containsKey( status ) )
        {
            return colorMap.get( status );
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
        if ( type.equalsIgnoreCase( InvoiceType.TAX_DOCUMENT.name() ) )
        {
            return GREEN;
        }

        return GREY;
    }

    private String typeText( String type )
    {
        if ( type.equalsIgnoreCase( InvoiceType.TAX_DOCUMENT.name() ) )
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