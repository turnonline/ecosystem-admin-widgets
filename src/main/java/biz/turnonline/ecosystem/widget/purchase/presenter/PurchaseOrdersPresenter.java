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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.DeclinePurchaseOrderEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderDetailEvent;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrderDetail;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrders;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * Purchase order list view presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrdersPresenter
        extends Presenter<PurchaseOrdersPresenter.IView>
{
    @Inject
    public PurchaseOrdersPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( PurchaseOrders.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( DeclinePurchaseOrderEvent.TYPE, this::declined );
        bus().addHandler( PurchaseOrderDetailEvent.TYPE,
                event -> controller().goTo( new PurchaseOrderDetail( event.getId(), "tabDetails" ) ) );

        view().setDataSource( ( offset, limit, callback ) ->
                bus().billing().searchPurchaseOrders( offset, limit, true, callback ) );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();

        PurchaseOrders where = ( PurchaseOrders ) controller().getWhere();
        if ( where.getScrollspy() != null )
        {
            view().scrollTo( where.getScrollspy() );
        }
    }

    private void declined( DeclinePurchaseOrderEvent event )
    {
        bus().billing().declinePurchaseOrder( event.getId(),
                ( response, failure ) -> success( messages.msgPurchaseOrderDeclined(), failure ) );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<List<PurchaseOrder>>
    {
        void scrollTo( @Nullable String scrollspy );

        void clear();

        void setDataSource( InfiniteScroll.Callback<PurchaseOrder> callback );
    }
}