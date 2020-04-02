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
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Invoicing
        extends Composite
{
    private static PricingUiBinder binder = GWT.create( PricingUiBinder.class );

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

    interface PricingUiBinder
            extends UiBinder<HTMLPanel, Invoicing>
    {
    }
}
