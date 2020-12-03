/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase;

import biz.turnonline.ecosystem.widget.purchase.presenter.BillsPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.EditBillPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.ExpensesPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.IncomingInvoiceDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.PurchaseOrderDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.PurchaseOrdersPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.TransactionsPresenter;
import org.ctoolkit.gwt.client.presenter.PresenterController;

import javax.inject.Inject;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseController
        extends PresenterController
{
    @Inject
    public PurchaseController( PurchaseOrdersPresenter orders,
                               PurchaseOrderDetailsPresenter orderDetail,
                               ExpensesPresenter invoices,
                               IncomingInvoiceDetailsPresenter invoiceDetails,
                               BillsPresenter bills,
                               EditBillPresenter editBill,
                               TransactionsPresenter transactions )
    {
        super( orders, orderDetail, invoices, invoiceDetails, bills, editBill, transactions );
    }
}
