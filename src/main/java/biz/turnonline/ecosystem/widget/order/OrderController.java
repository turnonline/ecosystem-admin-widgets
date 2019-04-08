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

package biz.turnonline.ecosystem.widget.order;

import biz.turnonline.ecosystem.widget.order.place.EditOrder;
import biz.turnonline.ecosystem.widget.order.place.Orders;
import biz.turnonline.ecosystem.widget.order.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.order.presenter.OrdersPresenter;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class OrderController
        implements ActivityMapper
{
    private static HashMap<String, Presenter> presenters = null;

    @Inject
    public OrderController( OrdersPresenter ordersPresenter,
                            EditOrderPresenter editOrderPresenter )
    {
        if ( presenters == null )
        {
            presenters = new HashMap<>();

            presenters.put( Orders.class.getName(), ordersPresenter );
            presenters.put( EditOrder.class.getName(), editOrderPresenter );
        }
    }

    @Override
    public Activity getActivity( final Place place )
    {
        Presenter presenter = presenters.get( place.getClass().getName() );
        if ( presenter == null )
        {
            presenter = presenters.get( OrderEntryPoint.DEFAULT_PLACE.getClass().getName() );
        }

        return presenter;
    }
}
