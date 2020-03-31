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
