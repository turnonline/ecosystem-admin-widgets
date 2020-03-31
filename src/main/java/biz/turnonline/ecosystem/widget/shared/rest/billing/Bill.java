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

public final class Bill
{
    private Long id;

    private Long invoiceId;

    private Long orderId;

    public Long getId()
    {
        return id;
    }

    public Bill setId( Long id )
    {
        this.id = id;
        return this;
    }

    public Long getInvoiceId()
    {
        return invoiceId;
    }

    public Bill setInvoiceId( Long invoiceId )
    {
        this.invoiceId = invoiceId;
        return this;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public Bill setOrderId( Long orderId )
    {
        this.orderId = orderId;
        return this;
    }
}
