package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingStructureTemplate;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductInvoicing;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.STANDARD;

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

        vat.addValueChangeHandler( e -> itemsPanel.changeVatInTree( e.getValue().get( 0 ) ) );
        currency.addValueChangeHandler( e -> itemsPanel.setCurrency( e.getValue().get( 0 ) ) );
    }

    /**
     * Updates the product pricing items UI by recalculated pricing.
     *
     * @param result the recalculated pricing
     */
    public void update( biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing result )
    {
        itemsPanel.fillFromTemplate( result.getItems() );
        priceExclVat.setValue( result.getTotalPriceExclVat() );

        evalReadOnlyPriceExclVat( result.getItems() );
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
    }

    @Override
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

        // populate pricing item template if any
        List<PricingStructureTemplate> templates = pricing.getTemplate();
        List<PricingItem> items = new ArrayList<>();

        if ( templates != null )
        {
            for ( PricingStructureTemplate next : templates )
            {
                items.add( fromTemplate( next, productVat ) );
            }
        }

        ProductInvoicing invoicing = product.getInvoicing();

        String currency = pricing.getCurrency();
        currency = currency == null ? "EUR" : currency;

        Double priceExclVat = pricing.getPriceExclVat();
        String unit = invoicing == null ? null : invoicing.getUnit();

        PricingItem root = new PricingItem();
        root.setAmount( 1.0 );
        root.setCheckedIn( true );
        root.setItemName( product.getItemName() );
        root.setItemType( STANDARD );
        root.setSubsidiary( pricing.getSubsidiary() );
        root.setItems( items );
        root.setCurrency( currency );
        root.setVat( productVat );

        root.setPriceExclVat( priceExclVat == null ? 0.0 : priceExclVat );
        root.setUnit( unit == null ? "ITEM" : unit );

        List<PricingItem> rootAsList = new ArrayList<>();
        rootAsList.add( root );

        itemsPanel.fillFromTemplate( rootAsList );
        // sets currency recursively for all current items
        itemsPanel.setCurrency( currency );

        evalReadOnlyPriceExclVat( rootAsList );
    }

    @SuppressWarnings( "Duplicates" )
    private PricingItem fromTemplate( @Nonnull PricingStructureTemplate template, @Nullable String vat )
    {
        Integer templateId = template.getId();

        PricingItem item = new PricingItem();
        item.setId( templateId == null ? null : templateId.longValue() );
        item.setAmount( template.getAmount() );
        item.setCheckedIn( template.getCheckedIn() );
        item.setItemName( template.getItemName() );
        item.setItemType( template.getItemType() );
        item.setOrder( template.getOrder() );
        item.setPriceExclVat( template.getPriceExclVat() );
        item.setSubsidiary( template.getSubsidiary() );
        item.setUnit( template.getUnit() );
        item.setVat( vat );

        List<PricingStructureTemplate> templates = template.getItems();
        List<PricingItem> items = new ArrayList<>();

        if ( templates != null )
        {
            for ( PricingStructureTemplate next : templates )
            {
                items.add( fromTemplate( next, vat ) );
            }
        }

        if ( !items.isEmpty() )
        {
            item.setItems( items );
        }

        return item;
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
