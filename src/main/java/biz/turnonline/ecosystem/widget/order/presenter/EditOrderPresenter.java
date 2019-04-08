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
import biz.turnonline.ecosystem.widget.order.event.BackEvent;
import biz.turnonline.ecosystem.widget.order.place.Orders;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditOrderPresenter
        extends Presenter<EditOrderPresenter.IView, AppEventBus>
{
    public interface IView
            extends biz.turnonline.ecosystem.widget.shared.view.IView<Order>
    {
    }

    @Inject
    public EditOrderPresenter( AppEventBus eventBus,
                               IView view,
                               PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new Orders() ) );

//        bus().addHandler( SaveProductEvent.TYPE, new SaveProductEventHandler()
//        {
//            @Override
//            public void onSaveContact( SaveProductEvent event )
//            {
//                Product product = event.getProduct();
//
//                if ( product.getId() == null )
//                {
//                    bus().productBilling().create( false, product, new FacadeCallback<Product>()
//                    {
//                        @Override
//                        public void onSuccess( Method method, Product response )
//                        {
//                            super.onSuccess( method, response );
//                            success( messages.msgRecordCreated() );
//
//                            controller().goTo( new Products() );
//                        }
//                    } );
//                }
//                else
//                {
//                    bus().productBilling().update( product.getId(), false, product, new FacadeCallback<Product>()
//                    {
//                        @Override
//                        public void onSuccess( Method method, Product response )
//                        {
//                            super.onSuccess( method, response );
//                            success( messages.msgRecordUpdated() );
//
//                            controller().goTo( new Products() );
//                        }
//                    } );
//                }
//            }
//        } );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newOrder() );

//        EditProduct where = ( EditProduct ) controller().getWhere();
//        if ( where.getId() != null )
//        {
//            bus().productBilling().findById( where.getId(), new FacadeCallback<Product>()
//            {
//                @Override
//                public void onSuccess( Method method, Product response )
//                {
//                    super.onSuccess( method, response );
//                    view().setModel( response );
//                }
//            } );
//        }
//
        onAfterBackingObject();
    }

    private Order newOrder()
    {
        Order order = new Order();

        return order;
    }
}