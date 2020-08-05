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

package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.product.event.EditProductEvent;
import biz.turnonline.ecosystem.widget.product.place.Products;
import biz.turnonline.ecosystem.widget.shared.event.UploaderAssociatedIdChangeEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Event;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductOverview;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.ui.PriceTextBox;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconSize;
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
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;

import javax.annotation.Nonnull;

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
    PriceTextBox priceExclVat;

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

    @UiField
    MaterialLink edit;

    public ProductOverviewCard( @Nonnull Product product, @Nonnull EventBus bus )
    {
        initWidget( binder.createAndBindUi( this ) );

        card.setScrollspy( Products.getScrollspy( product ) );

        title.getIcon().setIconSize( IconSize.SMALL );
        title.setText( product.getItemName() );
        snippet.setText( product.getSnippet() );

        ProductOverview overview = product.getOverview();
        if ( overview != null )
        {
            String url = overview.getThumbnailUrl();
            if ( url != null )
            {
                thumbnail.setUrl( url + "=s" + ( MIN_HEIGHT * 2 ) + "-c" );
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

        priceExclVat.setReadOnly( true );
        priceExclVat.setValue( pricing.getPriceExclVat(), pricing.getCurrency() );
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

        // action event handlers
        String scrollspyHistoryToken = Products.PREFIX + ":" + Products.getScrollspy( product );
        edit.addClickHandler( e -> {
            // don't add history record if there is already an another token not managing scrollspy
            if ( Products.isCurrentTokenScrollspy() )
            {
                // add record in to history (to manage scrolling to selected card once going back), but don't fire event
                History.newItem( scrollspyHistoryToken, false );
            }
            bus.fireEvent( new UploaderAssociatedIdChangeEvent( product.getId() ) );
            bus.fireEvent( new EditProductEvent( product ) );
        } );
    }

    interface ProductOverviewCardUiBinder
            extends UiBinder<MaterialCard, ProductOverviewCard>
    {
    }
}