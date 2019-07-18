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

package biz.turnonline.ecosystem.widget.shared.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel;
import com.google.gwt.event.shared.GwtEvent;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Represents a notification that user has selected a product from autocomplete search box.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ProductAutoCompleteEvent
        extends GwtEvent<ProductAutoCompleteEventHandler>
{
    public static Type<ProductAutoCompleteEventHandler> TYPE = new Type<>();

    private final Product product;

    private final TreeItemWithModel item;

    public ProductAutoCompleteEvent( @Nonnull Product product,
                                     @Nonnull TreeItemWithModel item )
    {
        this.product = checkNotNull( product, "Product can't be null" );
        this.item = checkNotNull( item, "Tree item can't be null" );
    }

    public Type<ProductAutoCompleteEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the product selected by user.
     */
    public Product getProduct()
    {
        return product;
    }

    /**
     * Returns the tree item instance that has been added to the tree and
     * user has executed a product search on it to be populated from selected product
     *
     * @see #getProduct()
     */
    public TreeItemWithModel getItem()
    {
        return item;
    }

    protected void dispatch( ProductAutoCompleteEventHandler handler )
    {
        handler.onProductAutoComplete( this );
    }
}
