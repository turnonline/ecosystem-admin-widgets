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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.DeclinePurchaseOrderEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderDetailEvent;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrderDetails;
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
                event -> controller().goTo( new PurchaseOrderDetails( event.getId(), "tabDetails" ) ) );

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