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

package biz.turnonline.ecosystem.widget.bill;

import biz.turnonline.ecosystem.widget.bill.presenter.BillsPresenter;
import biz.turnonline.ecosystem.widget.bill.presenter.EditBillPresenter;
import org.ctoolkit.gwt.client.presenter.PresenterController;

import javax.inject.Inject;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BillController
        extends PresenterController
{
    @Inject
    public BillController( BillsPresenter bills, EditBillPresenter editBill )
    {
        super( bills, editBill );
    }
}
