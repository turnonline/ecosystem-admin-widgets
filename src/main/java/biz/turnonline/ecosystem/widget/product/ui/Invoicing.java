package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductInvoicing;
import biz.turnonline.ecosystem.widget.shared.ui.BillingUnitComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialIntegerBox;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Invoicing
        extends Composite
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

        trialPeriod.setReturnBlankAsNull( true );
    }

    public ProductInvoicing bind( @Nullable ProductInvoicing invoicing )
    {
        if ( invoicing == null )
        {
            invoicing = new ProductInvoicing();
        }

        invoicing.setTrialPeriod( trialPeriod.getValue() );
        invoicing.setUnit( unit.getSingleValueByCode() );
        return invoicing;
    }

    public void fill( @Nullable ProductInvoicing invoicing )
    {
        if ( invoicing == null )
        {
            invoicing = new ProductInvoicing();
        }

        trialPeriod.setValue( invoicing.getTrialPeriod() );
        unit.setSingleValueByCode( invoicing.getUnit() );
    }
}
