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

import java.util.List;

public final class ProductMetaFields
        implements RelevantNullChecker
{
    private List<String> available;

    private List<String> mandatory;

    public List<String> getAvailable()
    {
        return available;
    }

    public ProductMetaFields setAvailable( List<String> available )
    {
        this.available = available;
        return this;
    }

    public List<String> getMandatory()
    {
        return mandatory;
    }

    public ProductMetaFields setMandatory( List<String> mandatory )
    {
        this.mandatory = mandatory;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( available, mandatory );
    }
}
