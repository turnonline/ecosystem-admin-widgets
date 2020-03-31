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

import biz.turnonline.ecosystem.widget.product.ui.ProductPictureUploader;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * This event is only a workaround how to deliver a Product.ID to the product picture uploader.
 * It's needed to be delivered before onAttach event of the {@link ProductPictureUploader}.
 * So make sure this event will be fired right before {@link EditProductEvent}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ProductIdChangeEvent
        extends GwtEvent<ProductIdChangeEventHandler>
{
    public static Type<ProductIdChangeEventHandler> TYPE = new Type<>();

    private final Long productId;

    public ProductIdChangeEvent( @Nonnull Long productId )
    {
        this.productId = checkNotNull( productId );
    }

    public Type<ProductIdChangeEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    public Long getProductId()
    {
        return productId;
    }

    protected void dispatch( ProductIdChangeEventHandler handler )
    {
        handler.onProductIdChange( this );
    }
}
