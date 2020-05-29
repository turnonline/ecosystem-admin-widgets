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

/**
 * Identification of the bill (receipt) or invoice document settled by associated transaction. Valid invoice identification includes order identification too.
 */
public final class Bill
{
    private Long id;

    private Long invoice;

    private Long order;

    private Long receipt;

    /**
     * The unique identification of the bill within Billing Processor service.
     */
    public Long getId()
    {
        return id;
    }

    public Bill setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * The invoice identification, unique only for specified order.
     **/
    public Long getInvoice()
    {
        return invoice;
    }

    public Bill setInvoice( Long invoice )
    {
        this.invoice = invoice;
        return this;
    }

    /**
     * The unique identification of the order associated with the invoice.
     **/
    public Long getOrder()
    {
        return order;
    }

    public Bill setOrder( Long order )
    {
        this.order = order;
        return this;
    }

    /**
     * The unique identification of the receipt within Product Billing service.
     **/
    public Long getReceipt()
    {
        return receipt;
    }

    public Bill setReceipt( Long receipt )
    {
        this.receipt = receipt;
        return this;
    }
}
