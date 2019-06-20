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

package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.RowItemAtOrderSelectionEvent;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchProduct;
import biz.turnonline.ecosystem.widget.shared.ui.BillingUnitComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.ProductAutoComplete;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableRow;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * The pricing item rendered as a single table row.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
class RowItem
        extends Composite
{
    private static AppMessages messages = AppMessages.INSTANCE;

    private static ItemUiBinder binder = GWT.create( ItemUiBinder.class );

    private final EventBus bus;

    private final TreeItemWithModel treeItem;

    @UiField
    MaterialCheckBox selected;

    @UiField( provided = true )
    ProductAutoComplete itemName;

    @UiField
    MaterialDoubleBox amount;

    @UiField
    MaterialDoubleBox priceExclusiveVat;

    @UiField
    CurrencyComboBox currency;

    @UiField
    VatRateComboBox vat;

    @UiField
    BillingUnitComboBox unit;

    private TableRow row;

    RowItem( @Nonnull EventBus eventBus, @Nonnull TreeItemWithModel treeItem )
    {
        this.bus = checkNotNull( eventBus );
        this.treeItem = checkNotNull( treeItem );

        itemName = new ProductAutoComplete( eventBus );
        itemName.addSelectionHandler( event -> {
            SearchProduct product = ( ( ProductAutoComplete.ProductSuggest ) event.getSelectedItem() ).getProduct();
            fillFrom( product );
        } );

        initWidget( row = binder.createAndBindUi( this ) );

        selected.getElement().setAttribute( "style", "margin: 0;" );
        selected.addClickHandler( event -> row.setBackgroundColor( selected.getValue() ? Color.GREY_LIGHTEN_5 : Color.WHITE ) );
        selected.addValueChangeHandler( event -> bus.fireEvent( new RowItemAtOrderSelectionEvent( event.getValue() ) ) );

        ( ( TableData ) itemName.getParent() ).setDataAttribute( "data-title", messages.labelItemName() );
        ( ( TableData ) amount.getParent() ).setDataAttribute( "data-title", messages.labelAmount() );
        ( ( TableData ) unit.getParent() ).setDataAttribute( "data-title", messages.labelUnit() );
        ( ( TableData ) priceExclusiveVat.getParent() ).setDataAttribute( "data-title", messages.labelPriceExclusiveVat() );
        ( ( TableData ) currency.getParent() ).setDataAttribute( "data-title", messages.labelCurrency() );
        ( ( TableData ) vat.getParent() ).setDataAttribute( "data-title", messages.labelVat() );
    }

    private void bindFromUI( PricingItem model )
    {
        model.setItemName( itemName.getItemBox().getValue() );
        model.setAmount( amount.getValue() );
        model.setPriceExclVat( priceExclusiveVat.getValue() );
        model.setCurrency( currency.getSingleValue() );
        model.setVat( vat.getSingleValueByCode() );
        model.setUnit( unit.getSingleValueByCode() );
    }

    /**
     * Updates the associated pricing item with the values from UI and returns its instance.
     *
     * @return the updated pricing item
     */
    public PricingItem bind()
    {
        PricingItem model = treeItem.getModel();
        bindFromUI( ( model ) );
        return model;
    }

    public void fill( PricingItem model )
    {
        itemName.getItemBox().setValue( model.getItemName() );
        amount.setValue( model.getAmount() != null ? model.getAmount() : 1D );
        priceExclusiveVat.setValue( model.getPriceExclVat() );
        currency.setSingleValue( model.getCurrency() );
        vat.setSingleValueByCode( model.getVat() );
        unit.setSingleValueByCode( model.getUnit() );
    }

    public MaterialCheckBox getSelected()
    {
        return selected;
    }

    public ProductAutoComplete getItemName()
    {
        return itemName;
    }

    /**
     * Returns tree item that's being associated with this row item
     *
     * @return the associated tree item
     */
    TreeItemWithModel getTreeItem()
    {
        return treeItem;
    }

    /**
     * Removes this row item widget from the pricing tree component and table of rows too.
     */
    void remove()
    {
        treeItem.removeFromParent();
        treeItem.remove( this );
        removeFromParent();
    }

    private void fillFrom( SearchProduct searchProduct )
    {
        ( ( AppEventBus ) bus ).billing().findProductById( Long.valueOf( searchProduct.getId() ),
                ( SuccessCallback<Product> ) product -> {
                    ProductPricing pricing = product.getPricing();

                    PricingItem pricingItem = new PricingItem();
                    pricingItem.setCurrency( pricing.getCurrency() );
                    pricingItem.setItemName( product.getItemName() );
                    pricingItem.setPriceExclVat( pricing.getPriceExclVat() );
                    pricingItem.setVat( pricing.getVat() );
                    pricingItem.setAmount( amount.getValue() );

                    fill( pricingItem );
                } );
    }

    interface ItemUiBinder
            extends UiBinder<TableRow, RowItem>
    {
    }
}
