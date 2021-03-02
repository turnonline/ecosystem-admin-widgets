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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Preconditions;
import biz.turnonline.ecosystem.widget.shared.event.ItemChangedCalculateEvent;
import biz.turnonline.ecosystem.widget.shared.event.ProductAutoCompleteEvent;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingProduct;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.VatRate;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchProduct;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableRow;

import javax.annotation.Nonnull;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * The pricing item rendered as a single table row.
 * Fires {@link ProductAutoCompleteEvent} once user has selected a product in autocomplete.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
class RowItem
        extends Composite
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static final ItemUiBinder binder = GWT.create( ItemUiBinder.class );

    private final EventBus bus;

    private final TreeItemWithModel treeItem;

    private final HasValue<String> itemName;

    @UiField
    MaterialCheckBox checkedIn;

    @UiField( provided = true )
    ProductAutoComplete itemNameSearch;

    @UiField
    MaterialTextBox itemNameStandard;

    @UiField
    MaterialDoubleBox amount;

    @UiField
    MaterialDoubleBox priceExclVat;

    @UiField
    VatRateComboBox vat;

    @UiField
    BillingUnitComboBox unit;

    @UiField
    MaterialButton delete;

    @UiField
    TableData deleteColumn;

    private boolean originCheckedInEnabled;

    private boolean originItemNameSearchReadOnly;

    private boolean originItemNameStandardReadOnly;

    private boolean originAmountReadOnly;

    private boolean originPriceExclVatReadOnly;

    private boolean originVatReadOnly;

    private boolean originUnitReadOnly;

    private boolean originDeleteEnabled;

    private boolean isReadOnly;

    RowItem( @Nonnull EventBus eventBus, @Nonnull TreeItemWithModel treeItem )
    {
        this.bus = checkNotNull( eventBus );
        this.treeItem = checkNotNull( treeItem );

        itemNameSearch = new ProductAutoComplete( eventBus );
        itemNameSearch.addSelectionHandler( this::fillFrom );

        initWidget( binder.createAndBindUi( this ) );

        amount.setReturnBlankAsNull( true );
        priceExclVat.setReturnBlankAsNull( true );

        delete.getElement().getStyle().setMargin( 0, Style.Unit.PX );
        delete.getElement().getStyle().setOpacity( 1 );

        checkedIn.addValueChangeHandler( event -> bus.fireEvent( new ItemChangedCalculateEvent() ) );

        ( ( TableData ) checkedIn.getParent() ).setDataAttribute( "data-title", messages.labelCheckedIn() );
        ( ( TableData ) itemNameSearch.getParent() ).setDataAttribute( "data-title", messages.labelItemName() );
        ( ( TableData ) amount.getParent() ).setDataAttribute( "data-title", messages.labelAmount() );
        ( ( TableData ) unit.getParent() ).setDataAttribute( "data-title", messages.labelUnit() );
        ( ( TableData ) priceExclVat.getParent() ).setDataAttribute( "data-title", messages.labelPriceExcludingVat() );
        ( ( TableData ) vat.getParent() ).setDataAttribute( "data-title", messages.labelVat() );
        ( ( TableData ) delete.getParent() ).setDataAttribute( "data-title", messages.labelDelete() );

        if ( !treeItem.isProductContext() && treeItem.isRoot() )
        {
            // product search is available only for root item but not for items initialized from template
            itemName = itemNameSearch.getItemBox();

            // by default in UI binder itemNameSearch is not visible
            itemNameStandard.setVisible( false );
            itemNameSearch.setVisible( true );
        }
        else
        {
            itemName = itemNameStandard;
        }

        if ( treeItem.isPurchaseOrderContext() )
        {
            deleteColumn.removeFromParent();
            deleteColumn.setVisible( false );
        }
    }

    private void bindFromUI( PricingItem model )
    {
        String itemNameValue = itemName.getValue();

        model.setCheckedIn( checkedIn.getValue() );
        // setReturnBlankAsNull is not available
        model.setItemName( Preconditions.isNullOrEmpty( itemNameValue ) ? null : itemNameValue );
        model.setAmount( amount.getValue() );
        model.setPriceExclVat( priceExclVat.getValue() );
        model.setUnit( unit.getSingleValueByCode() );

        // value might be set while widget is unattached, so model value is being preferred
        model.setVat( getTreeItem().getModel().getVat() );
    }

    void addVatChangeHandler( ValueChangeHandler<List<VatRate>> handler )
    {
        vat.addValueChangeHandler( handler );
    }

    void setVatRateValue( VatRate rate )
    {
        vat.setSingleValue( rate );
    }

    /**
     * If {@code true} sets all editable fields read only.
     * If {@code false} sets all fields previously editable back to be editable.
     */
    void setReadOnly( boolean readOnly )
    {
        if ( isReadOnly == readOnly )
        {
            //nothing to do, call must be idempotent
            return;
        }

        isReadOnly = readOnly;

        if ( readOnly )
        {
            originCheckedInEnabled = checkedIn.isEnabled();
            originItemNameSearchReadOnly = itemNameSearch.isReadOnly();
            originItemNameStandardReadOnly = itemNameStandard.isReadOnly();
            originAmountReadOnly = amount.isReadOnly();
            originPriceExclVatReadOnly = priceExclVat.isReadOnly();
            originVatReadOnly = vat.isReadOnly();
            originUnitReadOnly = unit.isReadOnly();
            originDeleteEnabled = delete.isEnabled();

            checkedIn.setEnabled( false );
            itemNameSearch.setReadOnly( true );
            itemNameStandard.setReadOnly( true );
            amount.setReadOnly( true );
            priceExclVat.setReadOnly( true );
            vat.setReadOnly( true );
            unit.setReadOnly( true );
            delete.setEnabled( false );
        }
        else
        {
            checkedIn.setEnabled( originCheckedInEnabled );
            itemNameSearch.setReadOnly( originItemNameSearchReadOnly );
            itemNameStandard.setReadOnly( originItemNameStandardReadOnly );
            amount.setReadOnly( originAmountReadOnly );
            priceExclVat.setReadOnly( originPriceExclVatReadOnly );
            vat.setReadOnly( originVatReadOnly );
            unit.setReadOnly( originUnitReadOnly );
            delete.setEnabled( originDeleteEnabled );
        }
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

    public void fill( @Nonnull PricingItem model )
    {
        // by default checked in marked to be included in to calculation
        checkedIn.setValue( model.getCheckedIn() == null || model.getCheckedIn() );
        itemName.setValue( model.getItemName() );
        amount.setValue( model.getAmount() != null ? model.getAmount() : 1D );
        priceExclVat.setValue( model.getPriceExclVat() );
        vat.setSingleValueByCode( model.getVat() );
        unit.setSingleValueByCode( model.getUnit() );

        List<PricingItem> items = model.getItems();
        amount.setReadOnly( items != null && !items.isEmpty() );

        PricingProduct product = model.getProduct();
        boolean hasProduct = product != null && product.getId() != null;
        // if items are initialized from template, VAT is managed via TreeItemWithModel#changeVatInTree(VatRate)
        vat.setReadOnly( treeItem.isProductContext() || !treeItem.isRoot() || hasProduct );

        // once initialized from product template root the item represents a product itself,
        // so do not allow to be deleted
        delete.setEnabled( !( treeItem.isProductContext() && treeItem.isRoot() ) );

        originAmountReadOnly = amount.isReadOnly();
        originVatReadOnly = vat.isReadOnly();
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
    private void remove()
    {
        treeItem.removeFromParent();
        treeItem.remove( this );
        removeFromParent();
    }

    private void fillFrom( SelectionEvent<SuggestOracle.Suggestion> event )
    {
        SearchProduct product = ( ( ProductAutoComplete.ProductSuggest ) event.getSelectedItem() ).getProduct();

        ( ( AppEventBus ) bus ).billing().findProductById( Long.valueOf( product.getId() ), false,
                ( SuccessCallback<Product> ) p -> {

                    PricingItem pricingItem = treeItem.getModel();
                    pricingItem.setAmount( amount.getValue() );

                    bus.fireEvent( new ProductAutoCompleteEvent( p, treeItem ) );
                } );
    }

    @UiHandler( "delete" )
    public void delete( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        remove();
    }

    interface ItemUiBinder
            extends UiBinder<TableRow, RowItem>
    {
    }
}
