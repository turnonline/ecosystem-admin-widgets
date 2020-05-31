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

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.account.Image;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.BillItem;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Scan;
import biz.turnonline.ecosystem.widget.shared.ui.BillTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillDetail
        extends Composite
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

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

    public BillDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        billNumber.setReturnBlankAsNull( true );
        description.setReturnBlankAsNull( true );
        totalPrice.setReturnBlankAsNull( true );

        created.setReadOnly( true );
        modified.setReadOnly( true );
    }

    public void bind( @Nonnull Bill bill )
    {
        bill.setDescription( description.getValue() );
        bill.setBillNumber( billNumber.getValue() );
        bill.setDateOfIssue( dateOfIssue.getValue() );
        bill.setTotalPrice( totalPrice.getValue() );
        bill.setType( billType.getSingleValueByCode() != null ? Bill.TypeEnum.valueOf( billType.getSingleValueByCode() ) : null );
        bill.setCurrency( currency.getSingleValue() );

        Scan scan = new Scan();
        scan.setOrder( 1 );
        scan.setServingUrl( billUploader.getValue().getServingUrl() );
        scan.setStorageName( billUploader.getValue().getStorageName() );
        bill.setScans( Collections.singletonList( scan ) );

        bill.setItems( items.getValue() );
    }

    public void fill( @Nonnull Bill bill )
    {
        description.setValue( bill.getDescription() );
        billNumber.setValue( bill.getBillNumber() );
        dateOfIssue.setValue( bill.getDateOfIssue() );
        totalPrice.setValue( bill.getTotalPrice() );
        billType.setSingleValueByCode( bill.getType() != null ? bill.getType().name() : null );
        currency.setSingleValue( bill.getCurrency() );

        List<Scan> scans = Optional.ofNullable( bill.getScans() ).orElse( new ArrayList<>() );
        Scan scan = scans.isEmpty() ? new Scan() : scans.get( 0 );

        Image image = new Image();
        image.setServingUrl( scan.getServingUrl() );
        image.setStorageName( scan.getStorageName() );
        billUploader.setValue( image );

        created.setValue( bill.getCreatedDate() );
        modified.setValue( bill.getModificationDate() );

        items.setValue( bill.getItems() );
    }

    @UiHandler( "addItem" )
    public void add( ClickEvent event )
    {
        BillItem item = new BillItem();
        item.setCurrency( Configuration.get().getCurrency() );
        item.setAmount( 1D );
        items.addRow( item );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, BillDetail>
    {
    }
}