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
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.table.TableRow;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DiscountItem
        extends Composite
        implements TakesValue<ProductDiscount>
{
    private TableRow row;

    private static DiscountItemUiBinder binder = GWT.create( DiscountItemUiBinder.class );

    interface DiscountItemUiBinder
            extends UiBinder<TableRow, DiscountItem>
    {
    }

    private ProductDiscount discount;

    @UiField
    MaterialCheckBox selected;

    @UiField
    MaterialDoubleBox off;

    @UiField
    DiscountUnitComboBox unit;

    @UiField
    DiscountRuleComboBox rule;

    @UiField
    MaterialChipTextBox codes;

    @UiField
    MaterialSwitch enabled;

    public DiscountItem()
    {
        initWidget( row = binder.createAndBindUi( this ) );

        selected.addClickHandler( event -> row.setBackgroundColor( selected.getValue() ? Color.GREY_LIGHTEN_5 : Color.WHITE ) );
        enabled.addClickHandler( event -> handleEnabled( !enabled.getValue() ) );
    }

    @Override
    public void setValue( ProductDiscount discount )
    {
        this.discount = discount;

        selected.setValue( false );
        off.setValue( discount.getOff() );
        unit.setSingleValueByCode( discount.getUnit() );
        rule.setSingleValueByCode( discount.getRule() );
        codes.setChippedValue( discount.getCodes() );
        enabled.setValue( discount.getEnabled() );

        handleEnabled( discount.getEnabled() );
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

    public MaterialCheckBox getSelected()
    {
        return selected;
    }

    private void handleEnabled( boolean enabled )
    {
        off.setEnabled( enabled );
        unit.setEnabled( enabled );
        rule.setEnabled( enabled );
        codes.setEnabled( enabled );
    }
}
