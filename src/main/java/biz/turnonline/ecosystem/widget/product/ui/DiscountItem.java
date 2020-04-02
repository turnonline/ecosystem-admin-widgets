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

import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductDiscount;
import biz.turnonline.ecosystem.widget.shared.ui.DiscountRuleComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.DiscountUnitComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.MaterialChipTextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.table.TableRow;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DiscountItem
        extends Composite
        implements TakesValue<ProductDiscount>
{
    private static DiscountItemUiBinder binder = GWT.create( DiscountItemUiBinder.class );

    @UiField
    MaterialCheckBox enabled;

    @UiField
    MaterialDoubleBox off;

    @UiField
    DiscountUnitComboBox unit;

    @UiField
    DiscountRuleComboBox rule;

    @UiField
    MaterialChipTextBox codes;

    @UiField
    MaterialButton btnDelete;

    private ProductDiscount discount;

    public DiscountItem()
    {
        initWidget( binder.createAndBindUi( this ) );
        enabled.addClickHandler( event -> handleEnabled( enabled.getValue() ) );

        btnDelete.getElement().getStyle().setOpacity( 1 );
    }

    @Override
    public ProductDiscount getValue()
    {
        discount.setOff( off.getValue() );
        discount.setUnit( unit.getSingleValueByCode() );
        discount.setRule( rule.getSingleValueByCode() );
        discount.setCodes( codes.getChippedValue() );
        discount.setEnabled( enabled.getValue() );

        return discount;
    }

    @Override
    public void setValue( ProductDiscount discount )
    {
        this.discount = discount;

        off.setValue( discount.getOff() );
        unit.setSingleValueByCode( discount.getUnit() );
        rule.setSingleValueByCode( discount.getRule() );
        codes.setChippedValue( discount.getCodes() );
        enabled.setValue( discount.getEnabled() );

        handleEnabled( discount.getEnabled() );
    }

    public MaterialButton getBtnDelete()
    {
        return btnDelete;
    }

    private void handleEnabled( boolean enabled )
    {
        off.setEnabled( enabled );
        unit.setEnabled( enabled );
        rule.setEnabled( enabled );
        codes.setEnabled( enabled );
    }

    interface DiscountItemUiBinder
            extends UiBinder<TableRow, DiscountItem>
    {
    }
}
