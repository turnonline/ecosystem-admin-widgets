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

package biz.turnonline.ecosystem.widget.order.presenter;

import biz.turnonline.ecosystem.widget.order.AppEventBus;
import biz.turnonline.ecosystem.widget.order.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.order.place.EditOrder;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class OrdersPresenter
        extends Presenter<OrdersPresenter.IView, AppEventBus>
{
    public interface IView
            extends biz.turnonline.ecosystem.widget.shared.view.IView
    {
        void refresh();
    }

    @Inject
    public OrdersPresenter( AppEventBus eventBus,
                            IView view,
                            PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditOrderEvent.TYPE, event ->
                controller().goTo( new EditOrder( event.getOrder() != null ? event.getOrder().getId() : null, "tabDetail" ) ) );
//
//        bus().addHandler( DeleteProductEvent.TYPE, event -> {
//            for ( Product produt : event.getProducts() )
//            {
//                bus().productBilling().delete( produt.getId(), new FacadeCallback<Void>()
//                {
//                    @Override
//                    public void onSuccess( Method method, Void response )
//                    {
//                        super.onSuccess( method, response );
//
//                        success( messages.msgRecordDeleted( Formatter.formatProductName( produt ) ) );
//                        Scheduler.get().scheduleDeferred( () -> view().refresh() );
//                    }
//                } );
//            }
//        } );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();
    }
}