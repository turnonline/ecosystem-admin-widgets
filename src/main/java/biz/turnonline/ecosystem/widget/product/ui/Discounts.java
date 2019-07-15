package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductDiscount;
import biz.turnonline.ecosystem.widget.shared.ui.DiscountRuleComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.DiscountUnitComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.MaterialChipTextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Discounts
        extends Composite
        implements TakesValue<List<ProductDiscount>>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private FlowPanel root = new FlowPanel();

    private FlowPanel actions = new FlowPanel();

    private FlowPanel rows = new FlowPanel();

    private List<ProductDiscount> values = new ArrayList<>();

    public Discounts()
    {
        initWidget( root );

        root.add( actions );
        root.add( rows );

        actions.getElement().setAttribute( "style", "margin: 10px 10px;padding-bottom: 20px;" );

        MaterialButton btnAdd = new MaterialButton( messages.labelAdd(), IconType.ADD_CIRCLE, ButtonType.OUTLINED );
        btnAdd.setIconColor( Color.GREEN );
        btnAdd.setTextColor( Color.GREEN );
        btnAdd.setWaves( WavesType.GREEN );
        btnAdd.setMarginRight( 10 );
        btnAdd.addClickHandler( event -> newRow() );
        actions.add( btnAdd );

        MaterialButton btnRemove = new MaterialButton( messages.labelDelete(), IconType.DELETE, ButtonType.OUTLINED );
        btnRemove.setIconColor( Color.RED );
        btnRemove.setTextColor( Color.RED );
        btnRemove.setWaves( WavesType.RED );
        btnRemove.addClickHandler( event -> deleteSelectedRows() );
        actions.add( btnRemove );
    }

    @Override
    public void setValue( List<ProductDiscount> value )
    {
        rows.clear();
        values.clear();

        if ( value != null && !value.isEmpty() )
        {
            value.forEach( this::createRow );
        }
    }

    @Override
    public List<ProductDiscount> getValue()
    {
        for ( int i = 0; i < values.size(); i++ )
        {
            GWT.log( i + "" );
            ProductDiscount discount = values.get( i );
            MaterialRow row = ( MaterialRow ) rows.getWidget( i );

            discount.setOff( ( ( MaterialDoubleBox ) row.getWidget( 1 ) ).getValue() );
            discount.setUnit( ( ( DiscountUnitComboBox ) row.getWidget( 2 ) ).getSingleValueByCode() );
            discount.setRule( ( ( DiscountRuleComboBox ) row.getWidget( 3 ) ).getSingleValueByCode() );
            discount.setCodes( ( ( MaterialChipTextBox ) row.getWidget( 4 ) ).getChippedValue() );
            discount.setEnabled( ( ( MaterialSwitch ) row.getWidget( 5 ) ).getValue() );
        }

        return values;
    }

    protected void newRow()
    {
        ProductDiscount discount = new ProductDiscount();
        discount.setEnabled( true );
        createRow( discount );
    }

    protected void deleteSelectedRows()
    {
        List<MaterialRow> rowsToDelete = new ArrayList<>();
        List<ProductDiscount> discountsToDelete = new ArrayList<>();

        for ( int i = 0; i < rows.getWidgetCount(); i++ )
        {
            MaterialRow row = ( MaterialRow ) rows.getWidget( i );
            MaterialCheckBox selected = ( MaterialCheckBox ) row.getWidget( 0 );
            if ( selected.getValue() )
            {
                rowsToDelete.add( row );
                discountsToDelete.add( values.get( i ) );
            }
        }

        rowsToDelete.forEach( Widget::removeFromParent );
        values.removeAll( discountsToDelete );
    }

    private void createRow( ProductDiscount discount )
    {
        MaterialRow row = new MaterialRow();
        row.setMarginBottom( 0 );
        rows.add( row );

        MaterialCheckBox selected = new MaterialCheckBox();
        selected.setGrid( "s12 m1" );
        selected.getElement().setAttribute( "style", "margin: 30px 0 0 0;" );
        selected.setWidth( "4%" );
        selected.addClickHandler( event -> row.setBackgroundColor( selected.getValue() ? Color.GREY_LIGHTEN_5 : Color.WHITE ) );
        row.add( selected );

        MaterialDoubleBox off = new MaterialDoubleBox();
        off.setLabel( messages.labelValue() );
        off.setValue( discount.getOff() );
        off.setGrid( "s12 m2" );
        row.add( off );

        DiscountUnitComboBox unit = new DiscountUnitComboBox();
        unit.setLabel( messages.labelDiscountType() );
        unit.setSingleValueByCode( discount.getUnit() );
        unit.setGrid( "s12 m2" );
        row.add( unit );

        DiscountRuleComboBox rule = new DiscountRuleComboBox();
        rule.setLabel( messages.labelDiscountRule() );
        rule.setSingleValueByCode( discount.getRule() );
        rule.setGrid( "s12 m2" );
        row.add( rule );

        MaterialChipTextBox codes = new MaterialChipTextBox();
        codes.setLetter( "C" );
        codes.setLabel( messages.labelCodes() );
        codes.setChippedValue( discount.getCodes() );
        codes.setGrid( "s12 m5" );
        row.add( codes );

        MaterialSwitch enabled = new MaterialSwitch( discount.getEnabled() );
        enabled.setGrid( "s12 m1" );
        enabled.setWidth( "5%" );
        enabled.setDisplay( Display.INITIAL );
        enabled.setMarginTop( 25 );
        enabled.setMarginLeft( -40 );
        enabled.addValueChangeHandler( event -> handleEnabled( row ) );
        row.add( enabled );

        handleEnabled( row );

        values.add( discount );
    }

    private void handleEnabled( MaterialRow row )
    {
        MaterialSwitch enabled = ( MaterialSwitch ) row.getWidget( 5 );
        for ( int i = 0; i < row.getWidgetCount(); i++ )
        {
            if ( i == 0 || i == 5 )
            {
                continue;
            }

            HasEnabled hasEnabled = ( HasEnabled ) row.getWidget( i );
            hasEnabled.setEnabled( enabled.getValue() );
        }
    }
}
