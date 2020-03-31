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
