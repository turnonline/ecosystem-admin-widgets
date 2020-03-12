/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.bill.view;

import biz.turnonline.ecosystem.widget.bill.event.BackEvent;
import biz.turnonline.ecosystem.widget.bill.event.DeleteBillEvent;
import biz.turnonline.ecosystem.widget.bill.event.SaveBillEvent;
import biz.turnonline.ecosystem.widget.bill.place.EditBill;
import biz.turnonline.ecosystem.widget.bill.presenter.EditBillPresenter;
import biz.turnonline.ecosystem.widget.bill.ui.BillDetail;
import biz.turnonline.ecosystem.widget.bill.ui.EditBillTabs;
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

    @UiField
    ConfirmationWindow confirmation;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton deleteBill;

    private PlaceController controller;

    @Inject
    public EditBillView(@Named( "EditBillBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                        PlaceController controller )
    {
        super();

        this.breadcrumb = breadcrumb;
        this.controller = controller;
        setActive( Route.BILLS );

        add( binder.createAndBindUi( this ) );

        confirmation.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeleteBillEvent( getRawModel() )) );
    }

    @Override
    protected void beforeGetModel()
    {
        Bill bill = getRawModel();

        detail.bind( bill );
        // TODO: implement
    }

    @Override
    protected void afterSetModel()
    {
        Bill bill = getRawModel();

        detail.fill( bill );

        // TODO: implement
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
    public void deleteBill(@SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmation.open( AppMessages.INSTANCE.questionDeleteRecord() );
    }

    interface EditBillsViewUiBinder
            extends UiBinder<HTMLPanel, EditBillView>
    {
    }
}