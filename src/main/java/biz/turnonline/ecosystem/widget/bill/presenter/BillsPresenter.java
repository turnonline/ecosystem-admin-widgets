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

import biz.turnonline.ecosystem.widget.bill.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.bill.event.NewBillEvent;
import biz.turnonline.ecosystem.widget.bill.place.Bills;
import biz.turnonline.ecosystem.widget.bill.place.EditBill;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.TimelinePanel;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nullable;
import javax.inject.Inject;
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
        bus().addHandler( EditBillEvent.TYPE, event -> controller().goTo( new EditBill( event.getId(), "tabDetail" ) ) );
        bus().addHandler( NewBillEvent.TYPE, event -> {
            success( messages.msgBatchCreated( event.getBill().getItemName() ) );
            bus().bill().createBill( event.getBill(), ( response, failure ) -> {
            } );
        } );

        view().setDataSourceCurrentMonth( ( offset, limit, callback ) ->
                bus().bill().getBills( offset, limit, TimelinePanel.firstDayOfCurrentMonth(), null, true, callback ) );
        view().setDataSourceLastMonths( ( offset, limit, callback ) ->
                bus().bill().getBills( offset, limit, null, TimelinePanel.lastDayOfPreviousMonth(), true, callback ) );
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

        void setDataSourceCurrentMonth( InfiniteScroll.Callback<Bill> callback );

        void setDataSourceLastMonths( InfiniteScroll.Callback<Bill> callback );
    }
}