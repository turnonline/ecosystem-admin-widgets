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
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
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
