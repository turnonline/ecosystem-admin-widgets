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

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingStructureTemplate;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialSwitch;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Pricing
        extends Composite
{
    private static PricingUiBinder binder = GWT.create( PricingUiBinder.class );

    @UiField
    MaterialDoubleBox priceExclVat;

    @UiField
    CurrencyComboBox currency;

    @UiField
    VatRateComboBox vat;

    @UiField
    VatRateComboBox vatEU;

    @UiField
    VatRateComboBox vatNonEU;

    @UiField
    MaterialSwitch domesticDelivery;

    @UiField( provided = true )
    PricingItemsPanel itemsPanel;

    @UiField
    Discounts discounts;

    @Inject
    public Pricing( AppEventBus eventBus )
    {
        itemsPanel = new PricingItemsPanel( eventBus, PricingItemsPanel.Context.PRODUCT );

        initWidget( binder.createAndBindUi( this ) );

        vat.addValueChangeHandler( e -> itemsPanel.changeVatInTree( e.getValue().get( 0 ) ) );
        currency.addValueChangeHandler( e -> itemsPanel.setCurrency( e.getValue().get( 0 ) ) );

        priceExclVat.setReturnBlankAsNull( true );
    }

    /**
     * Updates the product pricing items UI by recalculated pricing.
     *
     * @param result the recalculated pricing
     */
    public void update( biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing result )
    {
        itemsPanel.fill( result.getItems() );
        priceExclVat.setValue( result.getTotalPriceExclVat() );

        evalReadOnlyPriceExclVat( result.getItems() );
    }

    /**
     * Updates the product's price (excl. VAT).
     *
     * @param price the price to be shown to user
     */
    public void updatePriceExclVat( @Nonnull Double price )
    {
        priceExclVat.setValue( price );
    }

    public ProductPricing bind( @Nullable ProductPricing pricing )
    {
        if ( pricing == null )
        {
            pricing = new ProductPricing();
        }

        pricing.setPriceExclVat( priceExclVat.getValue() );
        pricing.setCurrency( currency.getSingleValue() );
        pricing.setVat( vat.getSingleValueByCode() );
        pricing.setVatEU( vatEU.getSingleValueByCode() );
        pricing.setVatNonEU( vatNonEU.getSingleValueByCode() );
        pricing.setDiscounts( discounts.getValue() );

        List<PricingItem> items = itemsPanel.bind();
        List<PricingStructureTemplate> templates = new ArrayList<>();

        if ( items != null && !items.isEmpty() && items.get( 0 ).getItems() != null )
        {
            // get rid of the root pricing item as it represents product itself
            for ( PricingItem next : items.get( 0 ).getItems() )
            {
                templates.add( fromPricingItem( next ) );
            }
        }

        pricing.setTemplate( templates );
        return pricing;
    }

    public void fill( @Nonnull Product product )
    {
        ProductPricing pricing = getProductPricing( checkNotNull( product, "Product cannot be null" ) );
        String productVat = pricing.getVat();

        priceExclVat.setValue( pricing.getPriceExclVat() );
        currency.setSingleValue( pricing.getCurrency() );
        vat.setSingleValueByCode( productVat );
        vatEU.setSingleValueByCode( pricing.getVatEU() );
        vatNonEU.setSingleValueByCode( pricing.getVatNonEU() );

        discounts.setValue( pricing.getDiscounts() );

        List<PricingItem> rootAsList = itemsPanel.fill( product, null );
        evalReadOnlyPriceExclVat( rootAsList );
    }

    @SuppressWarnings( "Duplicates" )
    private PricingStructureTemplate fromPricingItem( @Nonnull PricingItem item )
    {
        Long id = item.getId();

        PricingStructureTemplate template = new PricingStructureTemplate();
        template.setId( id == null ? null : id.intValue() );
        template.setAmount( item.getAmount() );
        template.setCheckedIn( item.getCheckedIn() );
        template.setItemName( item.getItemName() );
        template.setItemType( item.getItemType() );
        template.setOrder( item.getOrder() );
        template.setPriceExclVat( item.getPriceExclVat() );
        template.setSubsidiary( item.getSubsidiary() );
        template.setUnit( item.getUnit() );

        List<PricingItem> items = item.getItems();
        List<PricingStructureTemplate> templates = new ArrayList<>();

        if ( items != null )
        {
            for ( PricingItem next : items )
            {
                templates.add( fromPricingItem( next ) );
            }
        }

        if ( !templates.isEmpty() )
        {
            template.setItems( templates );
        }

        return template;
    }

    private void evalReadOnlyPriceExclVat( List<PricingItem> items )
    {
        if ( items != null && !items.isEmpty() )
        {
            PricingItem root = items.get( 0 );
            List<PricingItem> priceSpec = root.getItems();
            priceExclVat.setReadOnly( priceSpec != null && !priceSpec.isEmpty() );
        }
    }

    private ProductPricing getProductPricing( Product product )
    {
        ProductPricing pricing = product.getPricing();
        if ( pricing == null )
        {
            pricing = new ProductPricing();
            product.setPricing( pricing );
        }

        return pricing;
    }

    interface PricingUiBinder
            extends UiBinder<HTMLPanel, Pricing>
    {
    }
}
