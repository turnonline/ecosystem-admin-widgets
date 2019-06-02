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

package biz.turnonline.ecosystem.widget.billing;

import biz.turnonline.ecosystem.widget.billing.place.EditInvoice;
import biz.turnonline.ecosystem.widget.billing.place.EditOrder;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.billing.presenter.EditInvoicePresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.InvoicesPresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.OrdersPresenter;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BillingController
        implements ActivityMapper
{
    private static HashMap<String, Presenter> presenters = null;

    @Inject
    public BillingController( OrdersPresenter ordersPresenter,
                              EditOrderPresenter editOrderPresenter,
                              InvoicesPresenter invoicesPresenter,
                              EditInvoicePresenter editInvoicePresenter )
    {
        if ( presenters == null )
        {
            presenters = new HashMap<>();

            presenters.put( Orders.class.getName(), ordersPresenter );
            presenters.put( EditOrder.class.getName(), editOrderPresenter );
            presenters.put( Invoices.class.getName(), invoicesPresenter );
            presenters.put( EditInvoice.class.getName(), editInvoicePresenter );
        }
    }

    @Override
    public Activity getActivity( final Place place )
    {
        Presenter presenter = presenters.get( place.getClass().getName() );
        if ( presenter == null )
        {
            presenter = presenters.get( BillingEntryPoint.DEFAULT_PLACE.getClass().getName() );
        }

        return presenter;
    }
}
