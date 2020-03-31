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

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a request to delete specified product.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteProductEvent
        extends GwtEvent<DeleteProductEventHandler>
{
    public static Type<DeleteProductEventHandler> TYPE = new Type<>();

    private final Product product;

    public DeleteProductEvent( @Nonnull Product product )
    {
        this.product = checkNotNull( product, "Product cannot be null" );
    }

    public Type<DeleteProductEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteProductEventHandler handler )
    {
        handler.onDeleteProduct( this );
    }

    public Product getProduct()
    {
        return product;
    }
}
