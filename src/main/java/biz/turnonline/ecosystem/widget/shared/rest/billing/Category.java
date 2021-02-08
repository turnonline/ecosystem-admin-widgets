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

import biz.turnonline.ecosystem.widget.shared.rest.CodeBook;

public final class Category
        implements CodeBook
{
    private String code;

    private String label;

    private String locale;

    private String what;

    public String getCode()
    {
        return code;
    }

    public Category setCode( String code )
    {
        this.code = code;
        return this;
    }

    public String getLabel()
    {
        return label;
    }

    public Category setLabel( String label )
    {
        this.label = label;
        return this;
    }

    public String getLocale()
    {
        return locale;
    }

    public Category setLocale( String locale )
    {
        this.locale = locale;
        return this;
    }

    public String getWhat()
    {
        return what;
    }

    public void setWhat( String what )
    {
        this.what = what;
    }

    @Override
    public String getDescription()
    {
        return getWhat();
    }
}
