/*
 *  Copyright (c) 2022 TurnOnline.biz s.r.o.
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

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import java.util.Date;

/**
 * Model definition for ExchangeRate.
 */
public final class ExchangeRate
{
    private ExchangeAmount fee;

    private ExchangeAmount from;

    private Double rate;

    private Date rateDate;

    private ExchangeAmount to;

    public ExchangeAmount getFee()
    {
        return fee;
    }

    public ExchangeRate setFee( ExchangeAmount fee )
    {
        this.fee = fee;
        return this;
    }

    public ExchangeAmount getFrom()
    {
        return from;
    }

    public ExchangeRate setFrom( ExchangeAmount from )
    {
        this.from = from;
        return this;
    }

    public Double getRate()
    {
        return rate;
    }

    public ExchangeRate setRate( Double rate )
    {
        this.rate = rate;
        return this;
    }

    public Date getRateDate()
    {
        return rateDate;
    }

    public ExchangeRate setRateDate( Date rateDate )
    {
        this.rateDate = rateDate;
        return this;
    }

    public ExchangeAmount getTo()
    {
        return to;
    }

    public ExchangeRate setTo( ExchangeAmount to )
    {
        this.to = to;
        return this;
    }
}
