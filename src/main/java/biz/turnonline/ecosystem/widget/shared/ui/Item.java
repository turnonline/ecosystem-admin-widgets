package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchProduct;
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

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Item
        extends Composite
        implements HasModel<PricingItem>
{
    private static AppMessages messages = AppMessages.INSTANCE;

    private static ItemUiBinder binder = GWT.create( ItemUiBinder.class );

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

    private EventBus eventBus;

    public Item( EventBus eventBus )
    {
        this.eventBus = eventBus;

        itemName = new ProductAutoComplete( eventBus );
        itemName.addSelectionHandler( event -> {
            SearchProduct product = ( ( ProductAutoComplete.ProductSuggest ) event.getSelectedItem() ).getProduct();
            fillFrom( product );
        } );

        initWidget( row = binder.createAndBindUi( this ) );

        selected.getElement().setAttribute( "style", "margin: 0;" );
        selected.addClickHandler( event -> row.setBackgroundColor( selected.getValue() ? Color.GREY_LIGHTEN_5 : Color.WHITE ) );

        ( ( TableData ) itemName.getParent() ).setDataAttribute( "data-title", messages.labelItemName() );
        ( ( TableData ) amount.getParent() ).setDataAttribute( "data-title", messages.labelAmount() );
        ( ( TableData ) unit.getParent() ).setDataAttribute( "data-title", messages.labelUnit() );
        ( ( TableData ) priceExclusiveVat.getParent() ).setDataAttribute( "data-title", messages.labelPriceExclusiveVat() );
        ( ( TableData ) currency.getParent() ).setDataAttribute( "data-title", messages.labelCurrency() );
        ( ( TableData ) vat.getParent() ).setDataAttribute( "data-title", messages.labelVat() );
    }

    @Override
    public void bind( PricingItem model )
    {
        model.setItemName( itemName.getItemBox().getValue() );
        model.setAmount( amount.getValue() );
        model.setPriceExclVat( priceExclusiveVat.getValue() );
        model.setCurrency( currency.getSingleValue() );
        model.setVat( vat.getSingleValueByCode() );
        model.setUnit( unit.getSingleValueByCode() );
    }

    @Override
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

    // -- private helpers

    private void fillFrom( SearchProduct searchProduct )
    {
        ( ( AppEventBus ) eventBus ).billing().findProductById( Long.valueOf( searchProduct.getId() ),
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
            extends UiBinder<TableRow, Item>
    {
    }
}
