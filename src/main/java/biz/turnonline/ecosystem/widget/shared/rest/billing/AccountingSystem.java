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

public final class AccountingSystem
{
    private Boolean disableGZip;

    private String password;

    private String type;

    private String url;

    private String username;

    public Boolean getDisableGZip()
    {
        return disableGZip;
    }

    public AccountingSystem setDisableGZip( Boolean disableGZip )
    {
        this.disableGZip = disableGZip;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public AccountingSystem setPassword( String password )
    {
        this.password = password;
        return this;
    }

    public String getType()
    {
        return type;
    }

    public AccountingSystem setType( String type )
    {
        this.type = type;
        return this;
    }

    public String getUrl()
    {
        return url;
    }

    public AccountingSystem setUrl( String url )
    {
        this.url = url;
        return this;
    }

    public String getUsername()
    {
        return username;
    }

    public AccountingSystem setUsername( String username )
    {
        this.username = username;
        return this;
    }
}
