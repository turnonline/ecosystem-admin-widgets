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

package biz.turnonline.ecosystem.widget.product.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditProductEvent
        extends GwtEvent<EditProductEventHandler>
{
    public static Type<EditProductEventHandler> TYPE = new Type<>();

    private Product product;

    public EditProductEvent()
    {
    }

    public EditProductEvent( Product product )
    {
        this.product = product;
    }

    public Type<EditProductEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditProductEventHandler handler )
    {
        handler.onEditProduct( this );
    }

    /**
     * Returns the product ID or {@code null} if the event represents a new product request.
     *
     * @return the product ID or {@code null}
     */
    public Long getId()
    {
        return product == null ? null : product.getId();
    }
}
