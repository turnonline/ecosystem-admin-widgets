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

import biz.turnonline.ecosystem.widget.billing.presenter.EditInvoicePresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.InvoicesPresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.OrdersPresenter;
import org.ctoolkit.gwt.client.presenter.PresenterController;

import javax.inject.Inject;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BillingController
        extends PresenterController
{
    @Inject
    public BillingController( OrdersPresenter orders,
                              EditOrderPresenter editOrder,
                              InvoicesPresenter invoices,
                              EditInvoicePresenter editInvoice )
    {
        super( orders, editOrder, invoices, editInvoice );
    }
}
