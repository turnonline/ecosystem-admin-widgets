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
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DiscountItem
        extends Composite
        implements TakesValue<ProductDiscount>
{
    private static DiscountItemUiBinder binder = GWT.create( DiscountItemUiBinder.class );

    interface DiscountItemUiBinder
            extends UiBinder<TableRow, DiscountItem>
    {
    }

    private ProductDiscount discount;

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

    public DiscountItem()
    {
        initWidget( binder.createAndBindUi( this ) );
        enabled.addClickHandler( event -> handleEnabled( enabled.getValue() ) );

        btnDelete.getElement().getStyle().setOpacity( 1 );
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
}
