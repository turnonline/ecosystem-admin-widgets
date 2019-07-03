package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialSwitch;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Pricing
        extends Composite
        implements HasModel<Product>
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
    public Pricing( EventBus eventBus )
    {
        itemsPanel = new PricingItemsPanel( eventBus );

        initWidget( binder.createAndBindUi( this ) );
    }

    @Override
    public void bind( Product product )
    {
        ProductPricing pricing = getProductPricing( product );

        pricing.setPriceExclVat( priceExclVat.getValue() );
        pricing.setCurrency( currency.getSingleValue() );
        pricing.setVat( vat.getSingleValueByCode() );
        pricing.setVatEU( vatEU.getSingleValueByCode() );
        pricing.setVatNonEU( vatNonEU.getSingleValueByCode() );

        pricing.setDiscounts( discounts.getValue() );
    }

    @Override
    public void fill( Product product )
    {
        ProductPricing pricing = getProductPricing( product );

        priceExclVat.setValue( pricing.getPriceExclVat() );
        currency.setSingleValue( pricing.getCurrency() );
        vat.setSingleValueByCode( pricing.getVat() );
        vatEU.setSingleValueByCode( pricing.getVatEU() );
        vatNonEU.setSingleValueByCode( pricing.getVatNonEU() );

        discounts.setValue( pricing.getDiscounts() );

        // populate pricing item template if any
        ProductPricing pp = product.getPricing();
        PricingItem template = pp == null ? null : pp.getItems();

        List<PricingItem> items = new ArrayList<>();
        if ( template != null )
        {
            items.add( template );
        }

        itemsPanel.fill( items );
    }

    private ProductPricing getProductPricing( Product product )
    {
        ProductPricing pricing = product.getPricing();
        if ( pricing == null )
        {
            pricing = new ProductPricing();
            product.setPricing( pricing );
        }

        if ( pricing.getDiscounts() == null )
        {
            pricing.setDiscounts( new ArrayList<>() );
        }

        return pricing;
    }

    interface PricingUiBinder
            extends UiBinder<HTMLPanel, Pricing>
    {
    }
}
