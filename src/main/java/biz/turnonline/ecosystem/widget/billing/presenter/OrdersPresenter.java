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

package biz.turnonline.ecosystem.widget.billing.presenter;

import biz.turnonline.ecosystem.widget.billing.event.DeleteOrderEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditOrder;
import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrdersPresenter
        extends Presenter<OrdersPresenter.IView>
{
    @Inject
    public OrdersPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( Orders.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditOrderEvent.TYPE,
                event -> controller().goTo( new EditOrder( event.getId(), "tabDetail" ) ) );

        bus().addHandler( DeleteOrderEvent.TYPE, event -> bus().billing().deleteOrder( event.getOrder().getId(),
                ( SuccessCallback<Void> ) response -> {
                    success( messages.msgRecordDeleted( Formatter.formatOrderName( event.getOrder() ) ) );
                    controller().goTo( new Orders() );
                } ) );

        view().setDataSource( ( offset, limit, callback ) ->
                bus().billing().getOrders( offset, limit, true, callback ) );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();

        Orders where = ( Orders ) controller().getWhere();
        if ( where.getScrollspy() != null )
        {
            view().scrollTo( where.getScrollspy() );
        }
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<List<Order>>
    {
        void scrollTo( @Nullable String scrollspy );

        void clear();

        void setDataSource( InfiniteScroll.Callback<Order> callback );
    }
}