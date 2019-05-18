package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductInvoicing;
import biz.turnonline.ecosystem.widget.shared.ui.BillingUnitComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialIntegerBox;

import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Invoicing
        extends Composite
        implements HasModel<Product>
{
    private static PricingUiBinder binder = GWT.create( PricingUiBinder.class );

    interface PricingUiBinder
            extends UiBinder<HTMLPanel, Invoicing>
    {
    }

    @UiField
    MaterialIntegerBox trialPeriod;

    @UiField
    BillingUnitComboBox unit;

    @Inject
    public Invoicing()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    @Override
    public void bind( Product product )
    {
        ProductInvoicing invoicing = product.getInvoicing();

        invoicing.setTrialPeriod( trialPeriod.getValue() );
        invoicing.setUnit( unit.getSingleValueByCode() );
    }

    @Override
    public void fill( Product product )
    {
        ProductInvoicing invoicing = getProductInvoicing( product );

        trialPeriod.setValue( invoicing.getTrialPeriod() );
        unit.setSingleValueByCode( invoicing.getUnit() );
    }

    private ProductInvoicing getProductInvoicing( Product product )
    {
        ProductInvoicing invoicing = product.getInvoicing();
        if ( invoicing == null )
        {
            invoicing = new ProductInvoicing();
            product.setInvoicing( invoicing );
        }

        return invoicing;
    }
}
