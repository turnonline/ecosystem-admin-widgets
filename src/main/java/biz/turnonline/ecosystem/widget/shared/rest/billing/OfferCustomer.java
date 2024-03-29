/*
 *  Copyright (c) 2021 TurnOnline.biz s.r.o.
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

/**
 * Offer customer.
 */
public class OfferCustomer
        implements RelevantNullChecker
{
    private Long account;

    /**
     * The user account identification - TurnOnline.biz Ecosystem account ID.
     **/
    public Long getAccount()
    {
        return account;
    }

    public void setAccount( Long account )
    {
        this.account = account;
    }

    @Override
    public boolean allNull()
    {
        return allNull( account );
    }
}

