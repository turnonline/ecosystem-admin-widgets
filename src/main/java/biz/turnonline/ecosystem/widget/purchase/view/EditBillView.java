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

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.ApproveBillEvent;
import biz.turnonline.ecosystem.widget.purchase.event.BackBillEvent;
import biz.turnonline.ecosystem.widget.purchase.event.DeleteBillEvent;
import biz.turnonline.ecosystem.widget.purchase.event.SaveBillEvent;
import biz.turnonline.ecosystem.widget.purchase.place.EditBill;
import biz.turnonline.ecosystem.widget.purchase.presenter.EditBillPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.BillDetail;
import biz.turnonline.ecosystem.widget.purchase.ui.BillSupplier;
import biz.turnonline.ecosystem.widget.purchase.ui.BillUploader;
import biz.turnonline.ecosystem.widget.purchase.ui.EditBillTabs;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.account.Image;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Scan;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditBillView
        extends View<Bill>
        implements EditBillPresenter.IView
{
    private static final EditBillsViewUiBinder binder = GWT.create( EditBillsViewUiBinder.class );

    private final PlaceController controller;

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    BillUploader billUploader;

    @UiField
    EditBillTabs tabs;

    @UiField
    BillDetail detail;

    @UiField( provided = true )
    BillSupplier supplier;

    @UiField
    ConfirmationWindow confirmationDelete;

    @UiField
    ConfirmationWindow confirmationApprove;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton deleteBill;

    @UiField
    MaterialAnchorButton approveBill;

    @Inject
    public EditBillView( @Named( "EditBillBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                         PlaceController controller,
                         AddressLookupListener addressLookup,
                         EventBus eventBus)
    {
        super();

        this.breadcrumb = breadcrumb;
        this.controller = controller;
        setActive( Route.BILLS );

        supplier = new BillSupplier( eventBus, addressLookup );

        add( binder.createAndBindUi( this ) );

        confirmationDelete.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeleteBillEvent( getRawModel() ) ) );
        confirmationApprove.getBtnOk().addClickHandler( event -> bus().fireEvent( new ApproveBillEvent( getRawModel() ) ) );
    }

    @Override
    protected void beforeGetModel( Bill bill )
    {
        detail.bind( bill );
        supplier.bind( bill );

        if ( bill.getId() == null && billUploader.getBillId() != null )
        {
            // Use case when the Bill just has been created while Blob was uploaded
            bill.setId( billUploader.getBillId() );
        }

        Scan scan = new Scan();
        scan.setOrder( 1 );
        scan.setServingUrl( billUploader.getValue().getServingUrl() );
        scan.setStorageName( billUploader.getValue().getStorageName() );
        bill.setScans( Collections.singletonList( scan ) );
    }

    @Override
    protected void afterSetModel( Bill bill )
    {
        detail.fill( bill );
        supplier.fill( bill );

        Scheduler.get().scheduleDeferred( () -> {
            EditBill where = ( EditBill ) controller.getWhere();
            tabs.selectTab( where.getTab() );
        } );

        deleteBill.setEnabled( bill.getId() != null );
        Boolean approved = Optional.ofNullable( bill.isApproved() ).orElse( false );
        btnSave.setVisible( !approved );
        approveBill.setEnabled( !approved && bill.getId() != null );
        deleteBill.setEnabled( !approved && bill.getId() != null );

        List<Scan> scans = Optional.ofNullable( bill.getScans() ).orElse( new ArrayList<>() );
        Scan scan = scans.isEmpty() ? new Scan() : scans.get( 0 );

        Image image = new Image();
        image.setServingUrl( scan.getServingUrl() );
        image.setStorageName( scan.getStorageName() );
        billUploader.setValue( image );
        billUploader.setEnabled( !approved );
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new BackBillEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new SaveBillEvent( getModel() ) );
    }

    @UiHandler( "deleteBill" )
    public void deleteBill( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmationDelete.open( AppMessages.INSTANCE.questionDeleteRecord() );
    }

    @UiHandler( "approveBill" )
    public void approveBill( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmationApprove.open( AppMessages.INSTANCE.questionApproveBill() );
    }

    @Override
    public void setReadOnly()
    {
        detail.setReadOnly( true );
        supplier.readOnly( true );
        btnSave.setVisible( false );
        approveBill.setEnabled( false );
    }

    interface EditBillsViewUiBinder
            extends UiBinder<HTMLPanel, EditBillView>
    {
    }
}