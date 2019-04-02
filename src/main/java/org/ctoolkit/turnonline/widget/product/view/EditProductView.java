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

package org.ctoolkit.turnonline.widget.product.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;
import org.ctoolkit.turnonline.widget.product.event.BackEvent;
import org.ctoolkit.turnonline.widget.product.event.SaveProductEvent;
import org.ctoolkit.turnonline.widget.product.presenter.EditProductPresenter;
import org.ctoolkit.turnonline.widget.product.ui.Content;
import org.ctoolkit.turnonline.widget.product.ui.Detail;
import org.ctoolkit.turnonline.widget.product.ui.Publishing;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.ctoolkit.turnonline.widget.shared.ui.Route;
import org.ctoolkit.turnonline.widget.shared.ui.ScaffoldBreadcrumb;
import org.ctoolkit.turnonline.widget.shared.view.View;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditProductView
        extends View<Product>
        implements EditProductPresenter.IView
{
    private static EditProductViewUiBinder binder = GWT.create( EditProductViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    Detail detail;

    @UiField
    Content content;

    @UiField
    Publishing publishing;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    interface EditProductViewUiBinder
            extends UiBinder<HTMLPanel, EditProductView>
    {
    }

    @Inject
    public EditProductView( EventBus eventBus, @Named( "EditProductBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        scaffoldNavBar.setActive( Route.PRODUCTS );

        add( binder.createAndBindUi( this ) );


    }

    @Override
    protected void bind()
    {
        Product product = getRawModel();

        detail.bind( product );
        content.bind( product );
        publishing.bind( product );
    }

    @Override
    protected void fill()
    {
        Product product = getRawModel();

        detail.fill( product );
        content.fill( product );
        publishing.fill( product );
    }

    @UiHandler( "btnBack" )
    public void handleBack( ClickEvent event )
    {
        bus().fireEvent( new BackEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( ClickEvent event )
    {
        bus().fireEvent( new SaveProductEvent( getModel() ) );
    }
}