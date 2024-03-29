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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRange;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTableLoadResult;
import com.google.gwt.i18n.shared.DateTimeFormat;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class TransactionsDataSource
        extends RefreshableDataSource<Transaction>
{
    private AppEventBus eventBus;

    private Date from = PredefinedRange.firstDayOfCurrentMonth();

    private Date to = PredefinedRange.lastDayOfCurrentMonth();

    public TransactionsDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<Transaction> loadConfig, LoadCallback<Transaction> callback )
    {
        super.load( loadConfig, callback );

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        DateTimeFormat formatter = DateTimeFormat.getFormat( "yyyy-MM-dd" );

        eventBus.paymentProcessor().getTransactions(
                offset,
                limit,
                null,
                null,
                from != null ? formatter.format( from ) : null,
                to != null ? formatter.format( to ) : null,
                response -> callback.onSuccess( new SmartTableLoadResult<>( response.getItems(), loadConfig ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }

    public void setFrom( Date from )
    {
        this.from = from;
    }

    public void setTo( Date to )
    {
        this.to = to;
    }
}
