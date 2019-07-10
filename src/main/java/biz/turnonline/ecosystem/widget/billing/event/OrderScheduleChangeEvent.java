/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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
