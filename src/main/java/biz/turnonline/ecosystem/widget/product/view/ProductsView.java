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

package biz.turnonline.ecosystem.widget.product.view;

import biz.turnonline.ecosystem.widget.product.event.EditProductEvent;
import biz.turnonline.ecosystem.widget.product.presenter.ProductsPresenter;
import biz.turnonline.ecosystem.widget.product.ui.ProductOverviewCard;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScrollLoader;
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

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Product list view implemented by infinite scroll where single product is rendered by {@link ProductOverviewCard}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ProductsView
        extends View<List<Product>>
        implements ProductsPresenter.IView
{
    private static final ProductsViewUiBinder binder = GWT.create( ProductsViewUiBinder.class );

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
        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
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