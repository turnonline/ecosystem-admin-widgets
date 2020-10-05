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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.purchase.event.NewBillEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Bills;
import biz.turnonline.ecosystem.widget.purchase.place.EditBill;
import biz.turnonline.ecosystem.widget.purchase.view.BillScrollCallback;
import biz.turnonline.ecosystem.widget.shared.event.UploaderAssociatedIdChangeEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.gwt.client.facade.Items;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BillsPresenter
        extends Presenter<BillsPresenter.IView>
{
    @Inject
    public BillsPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( Bills.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditBillEvent.TYPE, event -> {
            bus().fireEvent( new UploaderAssociatedIdChangeEvent( event.getId() ) );
            controller().goTo( new EditBill( event.getId(), "tabDetail" ) );
        } );
        bus().addHandler( NewBillEvent.TYPE, event -> {
            success( messages.msgBatchCreated( event.getBill().getDescription() ) );
            bus().bill().createBill( event.getBill(), ( response, failure ) -> {
            } );
        } );

        DateTimeFormat formatter = DateTimeFormat.getFormat( "yyyy-MM-dd" );
        view().setDataSource( new BillScrollCallback()
        {
            @Override
            public void load( int offset, int limit, Date from, Date to, SuccessCallback<Items<Bill>> callback )
            {
                bus().bill().getBills(
                        offset,
                        limit,
                        formatter.format( from ),
                        formatter.format( to ),
                        true,
                        callback
                );
            }
        } );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();

        Bills where = ( Bills ) controller().getWhere();
        if ( where.getScrollspy() != null )
        {
            view().scrollTo( where.getScrollspy() );
        }
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<List<Bill>>
    {
        void scrollTo( @Nullable String scrollspy );

        void setDataSource( BillScrollCallback callback );
    }
}