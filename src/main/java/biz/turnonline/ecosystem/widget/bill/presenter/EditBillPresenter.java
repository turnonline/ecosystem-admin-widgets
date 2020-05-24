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

package biz.turnonline.ecosystem.widget.bill.presenter;

import biz.turnonline.ecosystem.widget.bill.event.ApproveBillEvent;
import biz.turnonline.ecosystem.widget.bill.event.BackEvent;
import biz.turnonline.ecosystem.widget.bill.event.DeleteBillEvent;
import biz.turnonline.ecosystem.widget.bill.event.SaveBillEvent;
import biz.turnonline.ecosystem.widget.bill.place.Bills;
import biz.turnonline.ecosystem.widget.bill.place.EditBill;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditBillPresenter
        extends Presenter<EditBillPresenter.IView>
{
    @Inject
    public EditBillPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( EditBill.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new Bills() ) );
        bus().addHandler( SaveBillEvent.TYPE, this::save );
        bus().addHandler( DeleteBillEvent.TYPE, this::delete );
        bus().addHandler( ApproveBillEvent.TYPE, this::approve );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newBill() );

        EditBill where = ( EditBill ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().bill().findBillById( where.getId(),
                    ( SuccessCallback<Bill> ) response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    private Bill newBill()
    {
        Bill bill = new Bill();
        bill.setType( Bill.TypeEnum.RECEIPT );

        return bill;
    }

    private void save( SaveBillEvent event )
    {
        Bill bill = event.getBill();

        if ( bill.getId() == null )
        {
            bus().bill().createBill( bill, ( SuccessCallback<Bill> ) response -> {
                success( messages.msgRecordCreated() );
                controller().goTo( new EditBill( response.getId(), "tabDetail" ) );
            } );
        }
        else
        {
            bus().bill().updateBill( bill.getId(), bill,
                    ( SuccessCallback<Bill> ) response -> success( messages.msgRecordUpdated() ) );
        }
    }

    private void delete( DeleteBillEvent event )
    {
        Bill bill = event.getBill();

        bus().bill().deleteBill( bill.getId(),
                ( SuccessCallback<Void> ) response -> {
                    success( messages.msgRecordDeleted( bill.getDescription() ) );
                    controller().goTo( new Bills() );
                } );
    }

    private void approve( ApproveBillEvent event )
    {
        Bill bill = event.getBill();

        bus().bill().approveBill( bill.getId(),
                ( SuccessCallback<Void> ) response -> {
                    success( messages.msgBillApproved( bill.getDescription() ) );
                    controller().goTo( new EditBill( bill.getId(), "tabDetail" ) );
                } );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Bill>
    {
    }
}