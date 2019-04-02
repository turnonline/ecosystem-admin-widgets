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

package org.ctoolkit.turnonline.widget.product.presenter;

import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.turnonline.widget.product.AppEventBus;
import org.ctoolkit.turnonline.widget.product.event.BackEvent;
import org.ctoolkit.turnonline.widget.product.event.SaveProductEvent;
import org.ctoolkit.turnonline.widget.product.event.SaveProductEventHandler;
import org.ctoolkit.turnonline.widget.product.place.EditProduct;
import org.ctoolkit.turnonline.widget.product.place.Products;
import org.ctoolkit.turnonline.widget.shared.presenter.Presenter;
import org.ctoolkit.turnonline.widget.shared.rest.FacadeCallback;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.fusesource.restygwt.client.Method;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditProductPresenter
        extends Presenter<EditProductPresenter.IView, AppEventBus>
{
    public interface IView
            extends org.ctoolkit.turnonline.widget.shared.view.IView<Product>
    {
    }

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
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new Products() ) );

        bus().addHandler( SaveProductEvent.TYPE, new SaveProductEventHandler()
        {
            @Override
            public void onSaveContact( SaveProductEvent event )
            {
                Product product = event.getProduct();

                if ( product.getId() == null )
                {
                    bus().productBilling().create( false, product, new FacadeCallback<Product>()
                    {
                        @Override
                        public void onSuccess( Method method, Product response )
                        {
                            super.onSuccess( method, response );
                            success( messages.msgRecordCreated() );

                            controller().goTo( new Products() );
                        }
                    } );
                }
                else
                {
                    bus().productBilling().update( product.getId(), false, product, new FacadeCallback<Product>()
                    {
                        @Override
                        public void onSuccess( Method method, Product response )
                        {
                            super.onSuccess( method, response );
                            success( messages.msgRecordUpdated() );

                            controller().goTo( new Products() );
                        }
                    } );
                }
            }
        } );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newProduct() );

        EditProduct where = ( EditProduct ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().productBilling().findById( where.getId(), new FacadeCallback<Product>()
            {
                @Override
                public void onSuccess( Method method, Product response )
                {
                    super.onSuccess( method, response );
                    view().setModel( response );
                }
            } );
        }

        onAfterBackingObject();
    }

    private Product newProduct()
    {
        Product product = new Product();

        return product;
    }
}