package org.ctoolkit.turnonline.widget.product.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.ProductMetaFields;
import org.ctoolkit.turnonline.widget.shared.ui.HasModel;
import org.ctoolkit.turnonline.widget.shared.ui.MaterialChipTextBox;

import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ProductDetail
        extends Composite
        implements HasModel<Product>
{
    private static ProductDetailUiBinder binder = GWT.create( ProductDetailUiBinder.class );

    interface ProductDetailUiBinder
            extends UiBinder<HTMLPanel, ProductDetail>
    {
    }

    @UiField
    MaterialTextBox itemName;

    @UiField
    MaterialTextBox snippet;

    @UiField
    MaterialChipTextBox availableFields;

    @UiField
    MaterialChipTextBox mandatoryFields;

    @Inject
    public ProductDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        availableFields.addChipCloseHandler( event -> ensureMandatoryFields() );
        availableFields.addChipDoubleClickHandler( event -> mandatoryFields.addChip( event.getText() ) );
        mandatoryFields.addKeyUpHandler( event -> ensureMandatoryFields() );
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
}
