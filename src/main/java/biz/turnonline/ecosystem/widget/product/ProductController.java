/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.product;

import biz.turnonline.ecosystem.widget.product.place.EditProduct;
import biz.turnonline.ecosystem.widget.product.place.Products;
import biz.turnonline.ecosystem.widget.product.presenter.EditProductPresenter;
import biz.turnonline.ecosystem.widget.product.presenter.ProductsPresenter;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ProductController
        implements ActivityMapper
{
    private static HashMap<String, Presenter> presenters = null;

    @Inject
    public ProductController( ProductsPresenter productsPresenter,
                              EditProductPresenter editProductPresenter )
    {
        if ( presenters == null )
        {
            presenters = new HashMap<>();

            presenters.put( Products.class.getName(), productsPresenter );
            presenters.put( EditProduct.class.getName(), editProductPresenter );
        }
    }

    @Override
    public Activity getActivity( final Place place )
    {
        Presenter presenter = presenters.get( place.getClass().getName() );
        if ( presenter == null )
        {
            presenter = presenters.get( ProductEntryPoint.DEFAULT_PLACE.getClass().getName() );
        }

        return presenter;
    }
}
