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

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

public final class ProductInvoicing
        implements RelevantNullChecker
{
    private String code;

    private NumberSeries numberSeries;

    private Integer trialPeriod;

    private String unit;

    /**
     * The product code, unique only for product catalog within associated accounting system (the value generated by the service).
     **/
    public String getCode()
    {
        return code;
    }

    public ProductInvoicing setCode( String code )
    {
        this.code = code;
        return this;
    }

    /**
     * The number series to be used to increment the invoice number. In order to assign an existing number series provide one of the code from the number series codebook. Omit this property or use DEFAULT value in order to use accounting system default.
     **/
    public NumberSeries getNumberSeries()
    {
        return numberSeries;
    }

    public ProductInvoicing setNumberSeries( NumberSeries numberSeries )
    {
        this.numberSeries = numberSeries;
        return this;
    }

    public void setNumberSeriesIf( NumberSeries numberSeries )
    {
        setIfNotAllNull( this::setNumberSeries, numberSeries );
    }

    /**
     * The number of days of the trial period. The period to postpone invoicing.
     **/
    public Integer getTrialPeriod()
    {
        return trialPeriod;
    }

    public ProductInvoicing setTrialPeriod( Integer trialPeriod )
    {
        this.trialPeriod = trialPeriod;
        return this;
    }

    /**
     * The billing unit code - unit of measure at invoice (time interval, items). It’s case insensitive.
     **/
    public String getUnit()
    {
        return unit;
    }

    public ProductInvoicing setUnit( String unit )
    {
        this.unit = unit;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( code, numberSeries, trialPeriod, unit );
    }
}
