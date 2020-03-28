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

import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;
import java.util.Date;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents an Order's due date number of days change event.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DueDateNumberOfDaysEvent
        extends GwtEvent<DueDateNumberOfDaysEventHandler>
{
    public static Type<DueDateNumberOfDaysEventHandler> TYPE = new Type<>();

    private final Date nextBillingDate;

    private final Integer numberOfDays;

    public DueDateNumberOfDaysEvent( @Nonnull Date nextBillingDate, @Nonnull Integer numberOfDays )
    {
        this.nextBillingDate = checkNotNull( nextBillingDate );
        this.numberOfDays = checkNotNull( numberOfDays );
    }

    /**
     * Returns the current order's Next billing date.
     */
    public Date getNextBillingDate()
    {
        return nextBillingDate;
    }

    /**
     * Returns the current order's Due date number of days.
     */
    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    public Type<DueDateNumberOfDaysEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DueDateNumberOfDaysEventHandler handler )
    {
        handler.onDueDateNumberOfDays( this );
    }
}
