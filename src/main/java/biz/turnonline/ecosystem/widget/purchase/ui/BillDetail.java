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

import biz.turnonline.ecosystem.widget.shared.rest.account.Image;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.BillItem;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Scan;
import biz.turnonline.ecosystem.widget.shared.rest.bill.VatRateRow;
import biz.turnonline.ecosystem.widget.shared.ui.BillTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PriceLabel;
import biz.turnonline.ecosystem.widget.shared.ui.VatRateComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    BillUploader billUploader;

    @UiField
    BillItems items;

    @UiField
    MaterialLink addItem;

    @UiField
    VatRateComboBox standardVatRate;

    @UiField
    MaterialDoubleBox standardExclVat;

    @UiField
    MaterialDoubleBox standardVatAmount;

    @UiField
    MaterialDoubleBox standardSum;

    @UiField
    VatRateComboBox reducedVatRate;

    @UiField
    MaterialDoubleBox reducedExclVat;

    @UiField
    MaterialDoubleBox reducedVatAmount;

    @UiField
    MaterialDoubleBox reducedSum;

    @UiField
    VatRateComboBox zeroVatRate;

    @UiField
    MaterialDoubleBox zeroExclVat;

    @UiField
    MaterialDoubleBox sumTotalPriceExclVat;

    @UiField
    MaterialDoubleBox sumTotalVat;

    @UiField
    PriceLabel sumTotalPrice;

    public BillDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        billNumber.setReturnBlankAsNull( true );
        description.setReturnBlankAsNull( true );
        totalPrice.setReturnBlankAsNull( true );

        created.setReadOnly( true );
        modified.setReadOnly( true );

        totalPrice.addChangeHandler( e -> sumTotalPrice.setValue( totalPrice.getValue(), currency.getSingleValue() ) );
        currency.addValueChangeHandler( e -> sumTotalPrice.setValue( totalPrice.getValue(), e.getValue().get( 0 ) ) );

        zeroVatRate.setReadOnly( true );
        zeroVatRate.setSingleValueByCode( "ZERO" );

        reducedVatRate.setReadOnly( true );
        reducedVatRate.setSingleValueByCode( "REDUCED" );

        standardVatRate.setReadOnly( true );
        standardVatRate.setSingleValueByCode( "STANDARD" );
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

        Scan scan = new Scan();
        scan.setOrder( 1 );
        scan.setServingUrl( billUploader.getValue().getServingUrl() );
        scan.setStorageName( billUploader.getValue().getStorageName() );
        bill.setScans( Collections.singletonList( scan ) );

        bill.setItems( items.getValue() );

        // Fill VAT rate rows
        List<VatRateRow> vatRows = new ArrayList<>();
        bindFrom( vatRows, zeroExclVat, null, zeroExclVat, zeroVatRate );
        bindFrom( vatRows, reducedExclVat, reducedVatAmount, reducedSum, reducedVatRate );
        bindFrom( vatRows, standardExclVat, standardVatAmount, standardSum, standardVatRate );

        bill.setVatRows( vatRows.isEmpty() ? null : vatRows );
    }

    private void bindFrom( @Nonnull List<VatRateRow> vatRows,
                           @Nullable MaterialDoubleBox exclVatBox,
                           @Nullable MaterialDoubleBox vatAmountBox,
                           @Nullable MaterialDoubleBox sumBox,
                           @Nonnull VatRateComboBox vatRateBox )
    {
        Double exclVat = exclVatBox == null ? null : exclVatBox.getValue();
        Double vatAmount = vatAmountBox == null ? null : vatAmountBox.getValue();
        Double sum = sumBox == null ? null : sumBox.getValue();

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
        totalPrice.setValue( bill.getTotalPrice() );
        billType.setSingleValueByCode( bill.getType() != null ? bill.getType().name() : null );
        currency.setSingleValue( bill.getCurrency() );
        sumTotalPriceExclVat.setValue( bill.getTotalVatBase() );
        sumTotalVat.setValue( bill.getTotalVat() );
        sumTotalPrice.setValue( bill.getTotalPrice(), bill.getCurrency() );

        List<Scan> scans = Optional.ofNullable( bill.getScans() ).orElse( new ArrayList<>() );
        Scan scan = scans.isEmpty() ? new Scan() : scans.get( 0 );

        Image image = new Image();
        image.setServingUrl( scan.getServingUrl() );
        image.setStorageName( scan.getStorageName() );
        billUploader.setValue( image );

        created.setValue( bill.getCreatedDate() );
        modified.setValue( bill.getModificationDate() );

        items.setValue( bill.getItems() );

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
    }

    private void fillFromRow( @Nonnull Map<Double, VatRateRow> vatMap,
                              @Nonnull VatRateComboBox vatRate,
                              @Nullable MaterialDoubleBox exclVat,
                              @Nullable MaterialDoubleBox vatAmount,
                              @Nullable MaterialDoubleBox sum )
    {
        Double vatRateValue = vatRate.getSingleValue().getValue();
        VatRateRow row = vatRateValue == null ? null : vatMap.get( vatRateValue );

        if ( row != null )
        {
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
                sum.setValue( row.getPriceInclVat() );
            }
        }
    }

    @UiHandler( "addItem" )
    public void add( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        BillItem item = new BillItem();
        item.setAmount( 1D );
        items.addRow( item );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, BillDetail>
    {
    }
}
