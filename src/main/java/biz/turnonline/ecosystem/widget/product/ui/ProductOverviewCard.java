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

package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.product.event.EditProductEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Event;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductOverview;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel.formatPrice;

/**
 * {@link Product} overview card component.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ProductOverviewCard
        extends Composite
{
    private static final int MIN_HEIGHT = 220;

    private static ProductOverviewCardUiBinder binder = GWT.create( ProductOverviewCardUiBinder.class );

    private final EventBus bus;

    private final Product product;

    @UiField
    MaterialCard card;

    @UiField
    MaterialCardTitle title;

    @UiField
    MaterialTextArea snippet;

    @UiField
    MaterialCardImage cardImage;

    @UiField
    MaterialImage thumbnail;

    @UiField
    MaterialCardContent content;

    @UiField
    MaterialTextBox priceExclVat;

    @UiField
    VatRateComboBox vat;

    @UiField
    MaterialCollection eventCard;

    @UiField
    MaterialDatePicker eventOn;

    @UiField
    MaterialLabel locationName;

    @UiField
    MaterialCardAction cardAction;

    public ProductOverviewCard( @Nonnull Product product, @Nonnull EventBus bus )
    {
        this.bus = bus;
        this.product = product;

        initWidget( binder.createAndBindUi( this ) );

        title.setText( product.getItemName() );
        snippet.setText( product.getSnippet() );

        ProductOverview overview = product.getOverview();
        if ( overview != null )
        {
            String url = overview.getThumbnailUrl();
            if ( url != null )
            {
                thumbnail.setUrl( url + "=s" + MIN_HEIGHT + "-c" );
            }

            if ( overview.getPublished() != null && overview.getPublished() )
            {
                title.setIconColor( Color.GREEN );
                title.setIconType( IconType.LOCK_OPEN );
            }
        }

        cardImage.setVisible( !Strings.isNullOrEmpty( thumbnail.getUrl() ) );

        card.setMinHeight( MIN_HEIGHT + "px" );
        thumbnail.setMinHeight( MIN_HEIGHT + "px" );
        content.setMinHeight( MIN_HEIGHT + "px" );

        // product pricing
        ProductPricing pricing = product.getPricing();
        String formattedPrice;
        String currency = pricing.getCurrency();
        Double priceExclVatValue = pricing.getPriceExclVat();

        if ( currency != null )
        {
            if ( priceExclVatValue != null )
            {
                formattedPrice = formatPrice( currency, priceExclVatValue );
            }
            else
            {
                formattedPrice = formatPrice( currency, 0.0 );
            }
        }
        else
        {
            formattedPrice = priceExclVatValue == null ? "0" : priceExclVatValue.toString();
        }

        priceExclVat.setReadOnly( true );
        priceExclVat.setText( formattedPrice );
        vat.setReadOnly( true );
        vat.setSingleValueByCode( pricing.getVat() );

        // event overview
        Event event = product.getEvent();
        boolean beginOn = event != null
                && event.getBegin() != null
                && event.getBegin().getShow() != null
                && event.getBegin().getShow()
                && event.getBegin().getOn() != null;
        if ( beginOn )
        {
            eventOn.setValue( event.getBegin().getOn() );
        }

        boolean hasLocation = event != null
                && event.getLocation() != null
                && !Strings.isNullOrEmpty( event.getLocation().getName() );

        if ( hasLocation )
        {
            String place = event.getLocation().getName();
            if ( !Strings.isNullOrEmpty( event.getLocation().getCity() ) )
            {
                place = place + ", " + event.getLocation().getCity();
            }
            locationName.setText( place );
        }

        eventCard.setVisible( beginOn );
        if ( beginOn )
        {
            cardAction.setBorderTop( "0" );
        }
    }

    @UiHandler( "edit" )
    public void edit( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus.fireEvent( new EditProductEvent( product ) );
    }

    interface ProductOverviewCardUiBinder
            extends UiBinder<MaterialCard, ProductOverviewCard>
    {
    }
}