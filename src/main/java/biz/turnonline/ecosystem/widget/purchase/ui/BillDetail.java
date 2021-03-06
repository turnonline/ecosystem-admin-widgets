/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Item;
import biz.turnonline.ecosystem.widget.shared.rest.bill.VatRateRow;
import biz.turnonline.ecosystem.widget.shared.ui.BillTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PriceTextBox;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillDetail
        extends Composite
{
    private static final DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    @UiField
    MaterialTextBox billNumber;

    @UiField
    MaterialTextBox description;

    @UiField
    MaterialDoubleBox totalPrice;

    @UiField
    CurrencyComboBox currency;

    @UiField
    BillTypeComboBox billType;

    @UiField
    MaterialDatePicker dateOfIssue;

    @UiField
    MaterialDatePicker created;

    @UiField
    MaterialDatePicker modified;

    @UiField
    MaterialRow itemsPanel;

    @UiField
    BillItems items;

    @UiField
    MaterialColumn itemsLabel;

    @UiField
    MaterialLink addItem;

    @UiField
    MaterialRow standardRow;

    @UiField
    VatRateComboBox standardVatRate;

    @UiField
    MaterialDoubleBox standardExclVat;

    @UiField
    MaterialDoubleBox standardVatAmount;

    @UiField
    PriceTextBox standardSum;

    @UiField
    MaterialRow reducedRow;

    @UiField
    VatRateComboBox reducedVatRate;

    @UiField
    MaterialDoubleBox reducedExclVat;

    @UiField
    MaterialDoubleBox reducedVatAmount;

    @UiField
    PriceTextBox reducedSum;

    @UiField
    MaterialRow zeroRow;

    @UiField
    VatRateComboBox zeroVatRate;

    @UiField
    MaterialDoubleBox zeroExclVat;

    @UiField
    MaterialPanel vatRecapitulation;

    @UiField
    MaterialRow sumRow;

    @UiField
    MaterialDoubleBox sumTotalPriceExclVat;

    @UiField
    MaterialDoubleBox sumTotalVat;

    @UiField
    PriceTextBox sumTotalPrice;

    @UiField
    MaterialEmptyState emptyRecapitulation;

    public BillDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        emptyRecapitulation.setHeight( "20vh" );
        emptyRecapitulation.setIconType( IconType.CLOSE );
        emptyRecapitulation.setIconColor( Color.BLUE );

        billNumber.setReturnBlankAsNull( true );
        description.setReturnBlankAsNull( true );
        totalPrice.setReturnBlankAsNull( true );
        reducedSum.setReturnBlankAsNull( true );
        standardSum.setReturnBlankAsNull( true );
        sumTotalPrice.setReturnBlankAsNull( true );

        created.setReadOnly( true );
        modified.setReadOnly( true );

        standardExclVat.addChangeHandler( event -> {
            calcStandardPrice();
            calcTotalPriceExclVat();
        } );
        standardVatAmount.addChangeHandler( event -> {
            calcStandardPrice();
            calcTotalVatAmount();
        } );

        reducedExclVat.addChangeHandler( event -> {
            calcReducedPrice();
            calcTotalPriceExclVat();
        } );
        reducedVatAmount.addChangeHandler( event -> {
            calcReducedPrice();
            calcTotalVatAmount();
        } );

        billType.addValueChangeHandler( event -> handleBillTypeChanged() );

        zeroExclVat.addChangeHandler( event -> calcTotalPriceExclVat() );
        currency.addValueChangeHandler( event -> changeCurrency() );

        sumTotalPriceExclVat.addChangeHandler( event -> calcTotalPrice() );
        sumTotalVat.addChangeHandler( event -> calcTotalPrice() );

        zeroVatRate.setReadOnly( true );
        zeroVatRate.setSingleValueByCode( "ZERO" );

        reducedVatRate.setReadOnly( true );
        reducedVatRate.setSingleValueByCode( "REDUCED" );

        standardVatRate.setReadOnly( true );
        standardVatRate.setSingleValueByCode( "STANDARD" );

        reducedSum.setReadOnly( true );
        standardSum.setReadOnly( true );
        sumTotalPrice.setReadOnly( true );
    }

    public void bind( @Nonnull Bill bill )
    {
        bill.setDescription( description.getValue() );
        bill.setBillNumber( billNumber.getValue() );
        bill.setDateOfIssue( dateOfIssue.getValue() );
        bill.setTotalPrice( totalPrice.getValue() );
        bill.setType( billType.getSingleValueByCode() != null ? Bill.TypeEnum.valueOf( billType.getSingleValueByCode() ) : null );
        bill.setCurrency( currency.getSingleValue() );
        bill.setTotalVatBase( sumTotalPriceExclVat.getValue() );
        bill.setTotalVat( sumTotalVat.getValue() );
        bill.setItems( items.getValue() );

        // Fill VAT rate rows
        List<VatRateRow> vatRows = new ArrayList<>();
        bindFrom( vatRows, zeroExclVat, null, zeroVatRate, zeroExclVat.getValue() );
        bindFrom( vatRows, reducedExclVat, reducedVatAmount, reducedVatRate, reducedSum.getPrice() );
        bindFrom( vatRows, standardExclVat, standardVatAmount, standardVatRate, standardSum.getPrice() );

        bill.setVatRows( vatRows.isEmpty() ? null : vatRows );
    }

    private void changeCurrency()
    {
        String currencyValue = currency.getSingleValue();

        items.setCurrency( currencyValue );
        sumTotalPrice.setValue( totalPrice.getValue(), currencyValue );
        standardSum.setValue( standardSum.getPrice(), currencyValue );
        reducedSum.setValue( reducedSum.getPrice(), currencyValue );
        sumTotalPrice.setValue( sumTotalPrice.getPrice(), currencyValue );
    }

    private void calcStandardPrice()
    {
        Double pev = standardExclVat.getValue();
        Double vat = standardVatAmount.getValue();
        if ( pev != null && vat != null )
        {
            standardSum.setValue( pev + vat, currency.getSingleValue() );
        }
    }

    private void calcReducedPrice()
    {
        Double pev = reducedExclVat.getValue();
        Double vat = reducedVatAmount.getValue();
        if ( pev != null && vat != null )
        {
            reducedSum.setValue( pev + vat, currency.getSingleValue() );
        }
    }

    private void calcTotalPriceExclVat()
    {
        Double standard = standardExclVat.getValue() == null ? 0.0 : standardExclVat.getValue();
        Double reduced = reducedExclVat.getValue() == null ? 0.0 : reducedExclVat.getValue();
        Double zero = zeroExclVat.getValue() == null ? 0.0 : zeroExclVat.getValue();
        sumTotalPriceExclVat.setValue( standard + reduced + zero );

        calcTotalPrice();
    }

    private void calcTotalVatAmount()
    {
        Double standard = standardVatAmount.getValue() == null ? 0.0 : standardVatAmount.getValue();
        Double reduced = reducedVatAmount.getValue() == null ? 0.0 : reducedVatAmount.getValue();
        sumTotalVat.setValue( standard + reduced );

        calcTotalPrice();
    }

    private void calcTotalPrice()
    {
        Double pev = sumTotalPriceExclVat.getValue();
        Double vat = sumTotalVat.getValue();
        if ( pev != null && vat != null )
        {
            sumTotalPrice.setValue( pev + vat, currency.getSingleValue() );
            totalPrice.setValue( sumTotalPrice.getPrice() );
        }
    }

    private void bindFrom( @Nonnull List<VatRateRow> vatRows,
                           @Nullable MaterialDoubleBox exclVatBox,
                           @Nullable MaterialDoubleBox vatAmountBox,
                           @Nonnull VatRateComboBox vatRateBox,
                           @Nullable Double sum )
    {
        Double exclVat = exclVatBox == null ? null : exclVatBox.getValue();
        Double vatAmount = vatAmountBox == null ? null : vatAmountBox.getValue();

        if ( exclVat != null || vatAmount != null || sum != null )
        {
            VatRateRow row = new VatRateRow();
            vatRows.add( row );

            row.setPriceInclVat( sum );
            row.setVat( vatAmount == null ? 0.0 : vatAmount );
            row.setVatBase( exclVat );
            row.setVatRate( vatRateBox.getSingleValue().getValue() );
        }
    }

    public void fill( @Nonnull Bill bill )
    {
        description.setValue( bill.getDescription() );
        billNumber.setValue( bill.getBillNumber() );
        dateOfIssue.setValue( bill.getDateOfIssue() );
        totalPrice.setValue( bill.getTotalPrice() == null ? 0.0 : bill.getTotalPrice() );
        billType.setSingleValueByCode( bill.getType() != null ? bill.getType().name() : null );
        currency.setSingleValue( bill.getCurrency() );
        sumTotalPriceExclVat.setValue( bill.getTotalVatBase() );
        sumTotalVat.setValue( bill.getTotalVat() );
        sumTotalPrice.setValue( bill.getTotalPrice(), bill.getCurrency() );
        created.setValue( bill.getCreatedDate() );
        modified.setValue( bill.getModificationDate() );

        boolean approved = bill.isApproved() != null && bill.isApproved();
        List<Item> billItems = bill.getItems();

        items.setValue( billItems, bill.getCurrency() );
        boolean showItems = !approved || billItems != null && !billItems.isEmpty();

        items.setVisible( showItems );
        itemsLabel.setVisible( showItems );

        zeroExclVat.reset();

        reducedExclVat.reset();
        reducedVatAmount.reset();
        reducedSum.reset();

        standardExclVat.reset();
        standardVatAmount.reset();
        standardSum.reset();

        List<VatRateRow> vatRows = bill.getVatRows();
        if ( vatRows != null && !vatRows.isEmpty() )
        {
            Map<Double, VatRateRow> vatMap = vatRows
                    .stream()
                    .collect( Collectors.toMap( VatRateRow::getVatRate, a -> a ) );

            fillFromRow( vatMap, zeroVatRate, zeroExclVat, null, null );
            fillFromRow( vatMap, reducedVatRate, reducedExclVat, reducedVatAmount, reducedSum );
            fillFromRow( vatMap, standardVatRate, standardExclVat, standardVatAmount, standardSum );
        }

        // evaluate as a last step
        setReadOnly( approved );
        handleBillTypeChanged();
    }

    private void fillFromRow( @Nonnull Map<Double, VatRateRow> vatMap,
                              @Nonnull VatRateComboBox vatRate,
                              @Nullable MaterialDoubleBox exclVat,
                              @Nullable MaterialDoubleBox vatAmount,
                              @Nullable PriceTextBox sum )
    {
        Double vatRateValue = vatRate.getSingleValue().getValue();
        VatRateRow row = vatRateValue == null ? null : vatMap.get( vatRateValue );

        if ( row != null )
        {
            String currencyValue = currency.getSingleValue();
            if ( exclVat != null )
            {
                exclVat.setValue( row.getVatBase() );
            }
            if ( vatAmount != null )
            {
                vatAmount.setValue( row.getVat() );
            }
            if ( sum != null )
            {
                sum.setValue( row.getPriceInclVat(), currencyValue );
            }
        }
    }

    @UiHandler( "addItem" )
    public void add( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Item item = new Item();
        items.addRow( item );
    }

    private void handleBillTypeChanged()
    {
        itemsPanel.setVisible( false );
        vatRecapitulation.setVisible( false );

        if ( billType.getSingleValueByCode().equals( Bill.TypeEnum.RECEIPT.name() ) )
        {
            vatRecapitulation.setVisible( true );
        }
        if ( billType.getSingleValueByCode().equals( Bill.TypeEnum.INVOICE.name() ) )
        {
            itemsPanel.setVisible( true );
        }
    }

    public void setReadOnly( boolean approved )
    {
        addItem.setVisible( !approved );
        items.setReadOnly( approved );

        billNumber.setReadOnly( approved );
        description.setReadOnly( approved );
        if ( approved )
        {
            totalPrice.setReadOnly( true );
        }
        else
        {
            totalPrice.setEnabled( false );
        }
        currency.setReadOnly( approved );
        billType.setReadOnly( approved );
        dateOfIssue.setReadOnly( approved );
        items.setReadOnly( approved );

        standardExclVat.setReadOnly( approved );
        standardVatAmount.setReadOnly( approved );

        standardRow.setVisible( !( standardExclVat.getValue() == null
                && standardVatAmount.getValue() == null
                && standardSum.getValue() == null )
                || !approved );

        reducedExclVat.setReadOnly( approved );
        reducedVatAmount.setReadOnly( approved );

        reducedRow.setVisible( !( reducedExclVat.getValue() == null
                && reducedVatAmount.getValue() == null
                && reducedSum.getValue() == null )
                || !approved );

        zeroExclVat.setReadOnly( approved );
        zeroRow.setVisible( zeroExclVat.getValue() != null || !approved );

        sumTotalPriceExclVat.setReadOnly( approved );
        sumTotalVat.setReadOnly( approved );
        sumRow.setVisible( !( sumTotalPriceExclVat.getValue() == null
                && sumTotalVat.getValue() == null )
                || !approved );

        emptyRecapitulation.setVisible( !standardRow.isVisible()
                && !reducedRow.isVisible()
                && !zeroRow.isVisible()
                && !sumRow.isVisible() );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, BillDetail>
    {
    }
}
