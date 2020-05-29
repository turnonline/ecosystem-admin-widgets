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

package biz.turnonline.ecosystem.widget.purchase.event;

import biz.turnonline.ecosystem.widget.purchase.place.Expenses;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Expense;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nullable;

/**
 * Represents a request to show list of expenses.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ExpenseListEvent
        extends GwtEvent<ExpenseListEventHandler>
{
    public static Type<ExpenseListEventHandler> TYPE = new Type<>();

    private IncomingInvoice from;

    private Expense fromExpense;

    public ExpenseListEvent( @Nullable IncomingInvoice from )
    {
        this.from = from;
    }

    public ExpenseListEvent( @Nullable Expense from )
    {
        this.fromExpense = from;
    }

    public Type<ExpenseListEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public String getScrollspy()
    {
        return from != null ? Expenses.getScrollspy( from ) : Expenses.getScrollspy( fromExpense );
    }

    protected void dispatch( ExpenseListEventHandler handler )
    {
        handler.onExpenseList( this );
    }
}
