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

public final class Increment
        implements RelevantNullChecker
{
    private String current;

    private Long id;

    private Integer length;

    private String postfix;

    private String prefix;

    private Integer year;

    public String getCurrent()
    {
        return current;
    }

    public Increment setCurrent( String current )
    {
        this.current = current;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public Increment setId( Long id )
    {
        this.id = id;
        return this;
    }

    public Integer getLength()
    {
        return length;
    }

    public Increment setLength( Integer length )
    {
        this.length = length;
        return this;
    }

    public String getPostfix()
    {
        return postfix;
    }

    public Increment setPostfix( String postfix )
    {
        this.postfix = postfix;
        return this;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public Increment setPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    public Integer getYear()
    {
        return year;
    }

    public Increment setYear( Integer year )
    {
        this.year = year;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( current, length, postfix, prefix, year );
    }
}
