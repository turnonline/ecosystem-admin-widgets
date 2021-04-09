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

public final class PricingProduct
        implements RelevantNullChecker
{
    private Long id;

    private Integer templateId;


    /**
     * The unique product identification within product catalog, provided by the client to populate this pricing item if any. If not found, the response won't contain the product property at all.
     **/
    public Long getId()
    {
        return id;
    }

    public PricingProduct setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * The identification of the structure template item (PricingStructureTemplate) if defined. Unique only within product. If template ID is present, this pricing item will be initialized based on values of the corresponding structure item that serves as a template.
     **/
    public Integer getTemplateId()
    {
        return templateId;
    }

    public PricingProduct setTemplateId( Integer templateId )
    {
        this.templateId = templateId;
        return this;
    }

    @Override
    public boolean allNull()
    {
        return allNull( id );
    }
}
