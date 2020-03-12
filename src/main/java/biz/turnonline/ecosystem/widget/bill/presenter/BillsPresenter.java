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

import biz.turnonline.ecosystem.widget.bill.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.bill.place.Bills;
import biz.turnonline.ecosystem.widget.bill.place.EditBill;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BillsPresenter
        extends Presenter<BillsPresenter.IView>
{
    @Inject
    public BillsPresenter(IView view, PlaceController placeController )
    {
        super( view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditBillEvent.TYPE, event -> controller().goTo( new EditBill( event.getId(), "tabDetail" ) ) );

        view().setDataSource( ( offset, limit, callback ) ->
                bus().bill().getBills( offset, limit, true, callback ) );
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
            extends org.ctoolkit.gwt.client.view.IView
    {
        void scrollTo( @Nullable String scrollspy );

        void clear();

        void setDataSource( InfiniteScroll.Callback<Bill> callback );
    }
}