package biz.turnonline.ecosystem.widget.order.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.ui.BillingUnitComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.ProductSelector;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Item
        extends Composite
        implements HasModel<PricingItem>
{
    private static ItemUiBinder binder = GWT.create( ItemUiBinder.class );

    private static AppMessages messages = AppMessages.INSTANCE;

    private MaterialRow row;

    interface ItemUiBinder
            extends UiBinder<MaterialRow, Item>
    {
    }

    @UiField
    MaterialCheckBox selected;

    @UiField
    MaterialTextBox itemName;

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

    @UiField
    MaterialButton btnSelect;

    @UiField( provided = true )
    ProductSelector selector;

    public Item( EventBus eventBus )
    {
        selector = new ProductSelector( eventBus )
        {
            @Override
            public void onSelect( Product product )
            {
                MaterialToast.fireToast( messages.labelSelectedProduct( Formatter.formatProductName( product ) ), "green" );
                fill( mapProductToPricingItem( product ) );
                selector.close();
            }
        };
        initWidget( row = binder.createAndBindUi( this ) );

        selected.getElement().setAttribute( "style", "margin: 0;" );
        selected.addClickHandler( event -> row.setBackgroundColor( selected.getValue() ? Color.GREY_LIGHTEN_5 : Color.WHITE ) );
    }

    @Override
    public void bind( PricingItem model )
    {
        model.setItemName( itemName.getValue() );
        model.setAmount( amount.getValue() );
        model.setPriceExclVat( priceExclusiveVat.getValue() );
        model.setCurrency( currency.getSingleValue() );
        model.setVat( vat.getSingleValueByCode() );
        model.setUnit( unit.getSingleValueByCode() );
    }

    @Override
    public void fill( PricingItem model )
    {
        itemName.setValue( model.getItemName() );
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

    @UiHandler( "btnSelect" )
    public void handleSelect( ClickEvent event )
    {
        selector.open();
    }

    // -- private helpers

    private PricingItem mapProductToPricingItem( Product product )
    {
        ProductPricing pricing = product.getPricing();

        PricingItem pricingItem = new PricingItem();
        pricingItem.setCurrency( pricing.getCurrency() );
        pricingItem.setItemName( product.getItemName() );
        pricingItem.setPriceExclVat( pricing.getPriceExclVat() );
        pricingItem.setVat( pricing.getVat() );
        pricingItem.setAmount( amount.getValue() );

        return pricingItem;
    }
}
