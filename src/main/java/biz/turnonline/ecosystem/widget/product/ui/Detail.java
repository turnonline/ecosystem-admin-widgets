package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductMetaFields;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.MaterialChipTextBox;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Detail
        extends Composite
        implements HasModel<Product>
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    @UiField
    MaterialTextBox itemName;

    @UiField
    MaterialTextArea snippet;

    @UiField
    MaterialChipTextBox availableFields;

    @UiField
    MaterialChipTextBox mandatoryFields;

    @UiField
    MaterialTextBox priceExclVat;

    @UiField
    VatRateComboBox vat;

    @UiField
    MaterialTextBox priceInclVat;

    @UiField
    MaterialDatePicker created;

    @UiField
    MaterialDatePicker modified;

    @Inject
    public Detail()
    {
        initWidget( binder.createAndBindUi( this ) );

        availableFields.addChipCloseHandler( event -> ensureMandatoryFields() );
        availableFields.addChipDoubleClickHandler( event -> mandatoryFields.addChip( event.getText() ) );
        mandatoryFields.addKeyUpHandler( event -> ensureMandatoryFields() );

        priceExclVat.setReadOnly( true );
        vat.setReadOnly( true );
        priceInclVat.setReadOnly( true );
        created.setReadOnly( true );
        modified.setReadOnly( true );
    }

    /**
     * Updates the product pricing items UI by recalculated pricing.
     *
     * @param result the recalculated pricing
     */
    public void update( biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing result )
    {
        PricingItem pricing = new PricingItem();
        pricing.setFinalPriceExclVat( result.getTotalPriceExclVat() );
        pricing.setFinalPrice( result.getTotalPrice() );

        List<PricingItem> items = result.getItems();
        if ( items != null && !items.isEmpty() )
        {
            PricingItem root = items.get( 0 );
            pricing.setVat( root.getVat() );
            pricing.setCurrency( root.getCurrency() );
        }

        fillPricing( pricing );
    }

    @Override
    public void bind( Product product )
    {
        product.setItemName( itemName.getValue() );
        product.setSnippet( snippet.getValue() );

        product.getMetaFields().setAvailable( availableFields.getChippedValue() );
        product.getMetaFields().setMandatory( mandatoryFields.getChippedValue() );
    }

    @Override
    public void fill( Product product )
    {
        itemName.setValue( product.getItemName() );
        snippet.setValue( product.getSnippet() );

        ProductMetaFields metaFields = getMetaFields( product );
        availableFields.setChippedValue( metaFields.getAvailable() );
        mandatoryFields.setChippedValue( metaFields.getMandatory() );

        created.setValue( product.getCreatedDate() );
        modified.setValue( product.getModificationDate() );

        PricingItem pricing = product.getPricing() == null ? new PricingItem() : product.getPricing().getItems();
        fillPricing( pricing );
    }

    private void fillPricing( @Nonnull PricingItem pricing )
    {
        String price;
        String finalPrice;
        String currency = pricing.getCurrency();

        if ( currency != null )
        {
            if ( pricing.getFinalPriceExclVat() != null && pricing.getFinalPrice() != null )
            {
                price = NumberFormat.getCurrencyFormat( currency ).format( pricing.getFinalPriceExclVat() );
                finalPrice = NumberFormat.getCurrencyFormat( currency ).format( pricing.getFinalPrice() );
            }
            else
            {
                price = NumberFormat.getCurrencyFormat( currency ).format( 0.0 );
                finalPrice = NumberFormat.getCurrencyFormat( currency ).format( 0.0 );
            }
        }
        else
        {
            price = "0";
            finalPrice = "0";
        }

        priceExclVat.setValue( price );
        vat.setSingleValueByCode( pricing.getVat() );
        priceInclVat.setValue( finalPrice );
    }

    private ProductMetaFields getMetaFields( Product product )
    {
        if ( product.getMetaFields() == null )
        {
            product.setMetaFields( new ProductMetaFields() );
        }

        return product.getMetaFields();
    }

    private void ensureMandatoryFields()
    {
        mandatoryFields.getChippedValue().forEach( mandatoryText -> {
            if ( !availableFields.getChippedValue().contains( mandatoryText ) )
            {
                mandatoryFields.removeChip( mandatoryText );
            }
        } );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, Detail>
    {
    }
}
