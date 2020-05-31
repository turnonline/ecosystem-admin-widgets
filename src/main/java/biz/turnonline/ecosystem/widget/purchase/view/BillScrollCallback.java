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

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRange;
import org.ctoolkit.gwt.client.facade.Items;

import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class BillScrollCallback
        implements InfiniteScroll.Callback<Bill>
{
    private Date from = PredefinedRange.firstDayOfCurrentMonth();

    private Date to = PredefinedRange.lastDayOfCurrentMonth();

    @Override
    public void load( int offset, int limit, SuccessCallback<Items<Bill>> callback )
    {
        load( offset, limit, from, to, callback );
    }

    public abstract void load( int offset, int limit, Date from, Date to, SuccessCallback<Items<Bill>> callback );

    public void setFrom( Date from )
    {
        this.from = from;
    }

    public void setTo( Date to )
    {
        this.to = to;
    }
}
