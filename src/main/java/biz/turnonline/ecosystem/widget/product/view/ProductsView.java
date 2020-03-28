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

import biz.turnonline.ecosystem.widget.product.event.EditProductEvent;
import biz.turnonline.ecosystem.widget.product.presenter.ProductsPresenter;
import biz.turnonline.ecosystem.widget.product.ui.ProductOverviewCard;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollLoader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Product list view implemented by infinite scroll where single product is rendered by {@link ProductOverviewCard}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ProductsView
        extends View
        implements ProductsPresenter.IView
{
    private static ProductsViewUiBinder binder = GWT.create( ProductsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    InfiniteScroll<Product> scroll;

    private int headerHeight;

    @Inject
    public ProductsView( @Named( "ProductsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.PRODUCTS );

        add( binder.createAndBindUi( this ) );

        scroll.enableMasonry();
        scroll.setRenderer( this::createCard );
        scroll.setInfiniteScrollLoader( new InfiniteScrollLoader( messages.labelProductLoading() ) );

        Window.addResizeHandler( event -> scroll.setMinHeight( ( event.getHeight() - headerHeight ) + "px" ) );
        Scheduler.get().scheduleDeferred( () -> {
            headerHeight = scaffoldHeader.getElement().getClientHeight()
                    + breadcrumb.getElement().getClientHeight()
                    - 22;
            scroll.setMinHeight( ( Window.getClientHeight() - headerHeight ) + "px" );
        } );

        // refresh action setup
        breadcrumb.setRefreshTooltip( messages.tooltipProductListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> scroll.reload() );

        breadcrumb.setClearFilterVisible( false );
    }

    private Widget createCard( Product product )
    {
        MaterialColumn column = new MaterialColumn( 12, 6, 4 );
        column.setPadding( 0 );
        column.add( new ProductOverviewCard( product, bus() ) );
        return column;
    }

    @UiHandler( "newProduct" )
    public void newProduct( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new EditProductEvent() );
    }

    @Override
    public void scrollTo( @Nullable String scrollspy )
    {
        scroll.scrollTo( scrollspy );
    }

    @Override
    public void clear()
    {
        scroll.unload();
    }

    @Override
    public void setDataSource( InfiniteScroll.Callback<Product> callback )
    {
        scroll.unload();
        scroll.setDataSource( callback );
    }

    interface ProductsViewUiBinder
            extends UiBinder<HTMLPanel, ProductsView>
    {
    }
}