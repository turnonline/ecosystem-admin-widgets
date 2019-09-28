/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
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
