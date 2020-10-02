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

public final class EventEnd
        implements RelevantNullChecker
{
    private Integer from;

    private Date on;

    private Boolean show;

    private Integer to;

    public Integer getFrom()
    {
        return from;
    }

    public EventEnd setFrom( Integer from )
    {
        this.from = from;
        return this;
    }

    public Date getOn()
    {
        return on;
    }

    public EventEnd setOn( Date on )
    {
        this.on = on;
        return this;
    }

    public Boolean getShow()
    {
        return show;
    }

    public EventEnd setShow( Boolean show )
    {
        this.show = show;
        return this;
    }

    public Integer getTo()
    {
        return to;
    }

    public EventEnd setTo( Integer to )
    {
        this.to = to;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( from, on, to );
    }
}
