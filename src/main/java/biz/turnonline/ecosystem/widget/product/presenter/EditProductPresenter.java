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

package biz.turnonline.ecosystem.widget.product.presenter;

import biz.turnonline.ecosystem.widget.product.event.ProductListEvent;
import biz.turnonline.ecosystem.widget.product.event.RemovePictureEvent;
import biz.turnonline.ecosystem.widget.product.event.SaveProductEvent;
import biz.turnonline.ecosystem.widget.product.place.EditProduct;
import biz.turnonline.ecosystem.widget.product.place.Products;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPicture;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditProductPresenter
        extends Presenter<EditProductPresenter.IView, AppEventBus>
{
    @Inject
    public EditProductPresenter( AppEventBus eventBus,
                                 IView view,
                                 PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( RemovePictureEvent.TYPE, event -> {
            EditProduct where = ( EditProduct ) controller().getWhere();

            if ( where.getId() != null )
            {
                ProductPicture picture = event.getPicture();
                bus().billing().deleteProductPicture( where.getId(), picture.getOrder(), this::deletePictureDone );
            }
        } );

        bus().addHandler( ProductListEvent.TYPE, event -> controller().goTo( new Products( event.getScrollspy() ) ) );

        bus().addHandler( SaveProductEvent.TYPE, event -> {
            Product product = event.getProduct();

            if ( product.getId() == null )
            {
                bus().billing().createProduct( false, product, ( SuccessCallback<Product> ) response -> {
                    success( messages.msgRecordCreated() );
                    controller().goTo( new EditProduct( response.getId(), "tabDetail" ) );
                } );
            }
            else
            {
                bus().billing().updateProduct( product.getId(), false, product,
                        ( SuccessCallback<Product> ) this::onProductSuccess );
            }
        } );

        bus().addHandler( RecalculatedPricingEvent.TYPE, event -> view().update( event.getPricing() ) );

    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newProduct() );

        EditProduct where = ( EditProduct ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().billing().findProductById( where.getId(), true,
                    ( SuccessCallback<Product> ) response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    private Product newProduct()
    {
        return new Product();
    }

    private void deletePictureDone( Void response, FacadeCallback.Failure failure )
    {
        if ( failure.isSuccess() )
        {
            success( messages.msgPictureDeleted() );
        }
        // silently ignore 404 - not an error, it means that we want to delete picture
        // which is not assigned to product yet
        else if ( !failure.isNotFound() )
        {
            error( messages.msgErrorRemoteServiceCall() );
        }
    }

    private void onProductSuccess( Product response )
    {
        ProductPricing pricing = response.getPricing();
        view().updatePriceExclVat( pricing == null ? 0.0 : pricing.getPriceExclVat() );
        success( messages.msgRecordUpdated() );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Product>
    {
        /**
         * Updates the product pricing items UI by recalculated pricing.
         *
         * @param pricing the recalculated pricing
         */
        void update( Pricing pricing );

        /**
         * Updates the product's price (excl. VAT) at pricing panel.
         *
         * @param price the price to be shown to user
         */
        void updatePriceExclVat( @Nullable Double price );
    }
}