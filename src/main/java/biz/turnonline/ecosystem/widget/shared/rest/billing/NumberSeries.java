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

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import biz.turnonline.ecosystem.widget.shared.rest.RelevantNullChecker;

import java.util.Date;

public final class NumberSeries
        implements RelevantNullChecker
{
    private String code;

    private Increment increment;

    private Date modificationDate;

    private String symbol;

    public String getCode()
    {
        return code;
    }

    public NumberSeries setCode( String code )
    {
        this.code = code;
        return this;
    }

    public Increment getIncrement()
    {
        return increment;
    }

    public NumberSeries setIncrement( Increment increment )
    {
        this.increment = increment;
        return this;
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public NumberSeries setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public NumberSeries setSymbol( String symbol )
    {
        this.symbol = symbol;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( code, increment, symbol );
    }
}
