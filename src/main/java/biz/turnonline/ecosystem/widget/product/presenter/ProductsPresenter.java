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

package biz.turnonline.ecosystem.widget.product.presenter;

import biz.turnonline.ecosystem.widget.product.event.DeleteProductEvent;
import biz.turnonline.ecosystem.widget.product.event.EditProductEvent;
import biz.turnonline.ecosystem.widget.product.place.EditProduct;
import biz.turnonline.ecosystem.widget.product.place.Products;
import biz.turnonline.ecosystem.widget.shared.event.UploaderAssociatedIdChangeEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ProductsPresenter
        extends Presenter<ProductsPresenter.IView>
{
    @Inject
    public ProductsPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditProductEvent.TYPE, event -> {
            bus().fireEvent( new UploaderAssociatedIdChangeEvent( event.getId() ) );
            controller().goTo( new EditProduct( event.getId(), "tabDetail" ) );
        } );

        bus().addHandler( DeleteProductEvent.TYPE, event ->
                bus().billing().deleteProduct( event.getProduct().getId(), ( SuccessCallback<Void> ) response -> {
                    success( messages.msgRecordDeleted( Formatter.formatProductName( event.getProduct() ) ) );
                    controller().goTo( new Products() );
                } ) );

        view().setDataSource( ( offset, limit, callback ) ->
                bus().billing().getProducts( offset, limit, true, false, callback ) );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();

        Products where = ( Products ) controller().getWhere();
        if ( where.getScrollspy() != null )
        {
            view().scrollTo( where.getScrollspy() );
        }
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<List<Product>>
    {
        void scrollTo( @Nullable String scrollspy );

        void clear();

        void setDataSource( InfiniteScroll.Callback<Product> callback );
    }
}