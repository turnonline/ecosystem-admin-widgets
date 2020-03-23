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

package biz.turnonline.ecosystem.widget.bill.presenter;

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
        bill.setType(Bill.TypeEnum.RECEIPT);

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
                    success( messages.msgRecordDeleted( bill.getItemName() ) );
                    controller().goTo( new Bills() );
                } );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Bill>
    {
    }
}