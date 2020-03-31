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

package biz.turnonline.ecosystem.widget.billing.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents an Order schedule change event with following payload:
 * <ul>
 * <li>periodicity</li>
 * <li>beginOn</li>
 * <li>lastBillingDate</li>
 * <li>numberOfDays</li>
 * </ul>
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrderScheduleChangeEvent
        extends GwtEvent<OrderScheduleChangeEventHandler>
{
    public static Type<OrderScheduleChangeEventHandler> TYPE = new Type<>();

    private final OrderPeriodicity periodicity;

    private final Date beginOn;

    private final Date lastBillingDate;

    private final Integer numberOfDays;

    public OrderScheduleChangeEvent( @Nonnull OrderPeriodicity periodicity,
                                     @Nonnull Date beginOn,
                                     @Nullable Date lastBillingDate,
                                     @Nullable Integer numberOfDays )
    {
        this.periodicity = checkNotNull( periodicity, "Order periodicity can't be null" );
        this.beginOn = checkNotNull( beginOn, "Order start (beginOn) date can't be null" );
        this.lastBillingDate = lastBillingDate;
        this.numberOfDays = numberOfDays;
    }

    public Type<OrderScheduleChangeEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the current periodicity value.
     *
     * @return the periodicity
     */
    public OrderPeriodicity getPeriodicity()
    {
        return periodicity;
    }

    /**
     * Returns the order's current Begin on date.
     */
    public Date getBeginOn()
    {
        return beginOn;
    }

    /**
     * Returns order's current Last billing date.
     */
    public Date getLastBillingDate()
    {
        return lastBillingDate;
    }

    /**
     * Returns the current order's Due date number of days.
     */
    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    protected void dispatch( OrderScheduleChangeEventHandler handler )
    {
        handler.onOrderPeriodicityChange( this );
    }
}
