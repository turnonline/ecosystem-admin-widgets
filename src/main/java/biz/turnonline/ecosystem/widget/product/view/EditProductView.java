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

package biz.turnonline.ecosystem.widget.product.view;

import biz.turnonline.ecosystem.widget.product.event.DeleteProductEvent;
import biz.turnonline.ecosystem.widget.product.event.ProductListEvent;
import biz.turnonline.ecosystem.widget.product.event.SaveProductEvent;
import biz.turnonline.ecosystem.widget.product.place.EditProduct;
import biz.turnonline.ecosystem.widget.product.presenter.EditProductPresenter;
import biz.turnonline.ecosystem.widget.product.ui.Content;
import biz.turnonline.ecosystem.widget.product.ui.Detail;
import biz.turnonline.ecosystem.widget.product.ui.EditProductTabs;
import biz.turnonline.ecosystem.widget.product.ui.EventPanel;
import biz.turnonline.ecosystem.widget.product.ui.Invoicing;
import biz.turnonline.ecosystem.widget.product.ui.Pricing;
import biz.turnonline.ecosystem.widget.product.ui.Publishing;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditProductView
        extends View<Product>
        implements EditProductPresenter.IView
{
    private static EditProductViewUiBinder binder = GWT.create( EditProductViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    ConfirmationWindow confirmation;

    @UiField
    EditProductTabs tabs;

    @UiField
    Detail detail;

    @UiField
    Content content;

    @UiField( provided = true )
    Publishing publishing;

    @UiField( provided = true )
    Pricing pricing;

    @UiField
    Invoicing invoicing;

    @UiField
    EventPanel event;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton deleteProduct;

    private PlaceController controller;

    @Inject
    public EditProductView( PlaceController controller,
                            @Named( "EditProductBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                            AddressLookupListener addressLookup )
    {
        super();

        this.controller = controller;

        this.breadcrumb = breadcrumb;
        setActive( Route.PRODUCTS );

        publishing = new Publishing( bus() );
        pricing = new Pricing( AppEventBus.get() );

        add( binder.createAndBindUi( this ) );

        event.init( addressLookup );

        confirmation.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeleteProductEvent( getRawModel() )) );
    }

    @Override
    protected void beforeGetModel()
    {
        Product product = getRawModel();

        detail.bind( product );
        product.setPublishingIf( content.bind( product.getPublishing() ) );
        product.setPublishingIf( publishing.bind( product.getPublishing() ) );
        product.setPricing( pricing.bind( product.getPricing() ) );
        product.setInvoicingIf( invoicing.bind( product.getInvoicing() ) );
        product.setEventIf( event.bind( product.getEvent() ) );
    }

    @Override
    protected void afterSetModel()
    {
        Product product = getRawModel();

        detail.fill( product );
        content.fill( product.getPublishing() );
        publishing.fill( product.getPublishing() );
        pricing.fill( product );
        invoicing.fill( product.getInvoicing() );
        event.fill( product.getEvent() );

        Scheduler.get().scheduleDeferred( () -> {
            EditProduct where = ( EditProduct ) controller.getWhere();
            tabs.selectTab( where.getTab() );
        } );

        deleteProduct.setEnabled( product.getId() != null );
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new ProductListEvent( getRawModel() ) );
    }

    @UiHandler( "btnSave" )
    public void handleSave( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new SaveProductEvent( getModel() ) );
    }

    @UiHandler( "deleteProduct" )
    public void deleteProduct( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmation.open( AppMessages.INSTANCE.questionDeleteRecord() );
    }

    @Override
    public void update( biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing result )
    {
        detail.update( result );
        pricing.update( result );
    }

    @Override
    public void updatePriceExclVat( @Nullable Double price )
    {
        pricing.updatePriceExclVat( price == null ? 0.0 : price );
    }

    interface EditProductViewUiBinder
            extends UiBinder<HTMLPanel, EditProductView>
    {
    }
}