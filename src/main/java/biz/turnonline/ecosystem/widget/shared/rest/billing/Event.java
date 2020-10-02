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

import java.util.Date;

public final class Event
        implements RelevantNullChecker
{
    private EventBegin begin;

    private Date deadline;

    private EventEnd end;

    private EventLocation location;

    private Integer seats;

    public EventBegin getBegin()
    {
        return begin;
    }

    public Event setBegin( EventBegin begin )
    {
        this.begin = begin;
        return this;
    }

    public void setBeginIf( EventBegin begin )
    {
        setIfNotAllNull( this::setBegin, begin );
    }

    public Date getDeadline()
    {
        return deadline;
    }

    public Event setDeadline( Date deadline )
    {
        this.deadline = deadline;
        return this;
    }

    public EventEnd getEnd()
    {
        return end;
    }

    public Event setEnd( EventEnd end )
    {
        this.end = end;
        return this;
    }

    public void setEndIf( EventEnd end )
    {
        setIfNotAllNull( this::setEnd, end );
    }

    /**
     * @return value or {@code null} for none
     */
    public EventLocation getLocation()
    {
        return location;
    }

    public Event setLocation( EventLocation location )
    {
        this.location = location;
        return this;
    }

    public void setLocationIf( EventLocation location )
    {
        setIfNotAllNull( this::setLocation, location );
    }

    /**
     * @return value or {@code null} for none
     */
    public Integer getSeats()
    {
        return seats;
    }

    public Event setSeats( Integer seats )
    {
        this.seats = seats;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( begin, deadline, end, location, seats );
    }
}
