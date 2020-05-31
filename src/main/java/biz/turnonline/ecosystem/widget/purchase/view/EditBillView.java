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
import biz.turnonline.ecosystem.widget.purchase.event.BackEvent;
import biz.turnonline.ecosystem.widget.purchase.event.DeleteBillEvent;
import biz.turnonline.ecosystem.widget.purchase.event.SaveBillEvent;
import biz.turnonline.ecosystem.widget.purchase.place.EditBill;
import biz.turnonline.ecosystem.widget.purchase.presenter.EditBillPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.BillDetail;
import biz.turnonline.ecosystem.widget.purchase.ui.BillSupplier;
import biz.turnonline.ecosystem.widget.purchase.ui.EditBillTabs;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
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
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditBillView
        extends View<Bill>
        implements EditBillPresenter.IView
{
    private static EditBillsViewUiBinder binder = GWT.create( EditBillsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

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

    private PlaceController controller;

    @Inject
    public EditBillView( @Named( "EditBillBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                         PlaceController controller,
                         AddressLookupListener addressLookup )
    {
        super();

        this.breadcrumb = breadcrumb;
        this.controller = controller;
        setActive( Route.BILLS );

        supplier = new BillSupplier( addressLookup );

        add( binder.createAndBindUi( this ) );

        confirmationDelete.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeleteBillEvent( getRawModel() ) ) );
        confirmationApprove.getBtnOk().addClickHandler( event -> bus().fireEvent( new ApproveBillEvent( getRawModel() ) ) );
    }

    @Override
    protected void beforeGetModel()
    {
        Bill bill = getRawModel();

        detail.bind( bill );
        supplier.bind( bill );
    }

    @Override
    protected void afterSetModel()
    {
        Bill bill = getRawModel();

        detail.fill( bill );
        supplier.fill( bill );

        Scheduler.get().scheduleDeferred( () -> {
            EditBill where = ( EditBill ) controller.getWhere();
            tabs.selectTab( where.getTab() );
        } );

        deleteBill.setEnabled( bill.getId() != null );
    }

    @UiHandler( "btnBack" )
    public void handleBack( ClickEvent event )
    {
        bus().fireEvent( new BackEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( ClickEvent event )
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

    interface EditBillsViewUiBinder
            extends UiBinder<HTMLPanel, EditBillView>
    {
    }
}