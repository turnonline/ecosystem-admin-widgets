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

package org.ctoolkit.turnonline.widget.product;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import gwt.material.design.client.constants.IconType;
import org.ctoolkit.turnonline.widget.product.place.HistoryMapper;
import org.ctoolkit.turnonline.widget.product.place.Products;
import org.ctoolkit.turnonline.widget.product.presenter.EditProductPresenter;
import org.ctoolkit.turnonline.widget.product.presenter.ProductsPresenter;
import org.ctoolkit.turnonline.widget.product.view.EditProductView;
import org.ctoolkit.turnonline.widget.product.view.ProductsView;
import org.ctoolkit.turnonline.widget.shared.AppMessages;
import org.ctoolkit.turnonline.widget.shared.Configuration;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.ProductBillingFacade;
import org.ctoolkit.turnonline.widget.shared.ui.ScaffoldBreadcrumb;
import org.ctoolkit.turnonline.widget.shared.ui.ScaffoldBreadcrumb.BreadcrumbItem;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static org.ctoolkit.turnonline.widget.product.ProductEntryPoint.DEFAULT_PLACE;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Module
public abstract class ProductModule
{
    private static AppMessages messages = AppMessages.INSTANCE;

    // -- activity & places

    @Provides
    static PlaceHistoryMapper providePlaceHistoryMapper()
    {
        return GWT.create( HistoryMapper.class );
    }

    @Singleton
    @Provides
    static PlaceController providePlaceController( EventBus eventBus )
    {
        return new PlaceController( eventBus );
    }

    @Singleton
    @Provides
    static ActivityManager provideActivityManager( ActivityMapper mapper, EventBus eventBus )
    {
        return new ActivityManager( mapper, eventBus );
    }

    @Provides
    static PlaceHistoryHandler.Historian provideHistorian()
    {
        // For best UX at login time (preserving place), use an Html5History: https://gist.github.com/1883821
        return new PlaceHistoryHandler.DefaultHistorian();
    }

    @Provides
    @Singleton
    static PlaceHistoryHandler providePlaceHistoryHandler( PlaceHistoryMapper mapper, PlaceHistoryHandler.Historian historian,
                                                           PlaceController controller, EventBus eventBus )
    {
        PlaceHistoryHandler handler = new PlaceHistoryHandler( mapper, historian );
        handler.register( controller, eventBus, DEFAULT_PLACE );
        return handler;
    }

    @Binds
    @Singleton
    abstract ActivityMapper provideActivityMapper( ProductController controller );

    // -- configuration

    @Singleton
    @Provides
    static Configuration provideConfiguration()
    {
        return Configuration.get();
    }

    // rest facade

    @Singleton
    @Provides
    static ProductBillingFacade provideProductBilling()
    {
        return GWT.create( ProductBillingFacade.class );
    }

    // -- breadcrumbs

    @Provides
    @Singleton
    @Named( "EditProductBreadcrumb" )
    static ScaffoldBreadcrumb provideEditProductBreadcrumb( PlaceController placeController )
    {
        List<BreadcrumbItem> items = new ArrayList<>();
        items.add( new BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new BreadcrumbItem( new Products(), messages.labelProducts() ) );
        items.add( new BreadcrumbItem( IconType.TABLET_MAC, messages.labelEditProduct() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    @Provides
    @Singleton
    @Named( "ProductsBreadcrumb" )
    static ScaffoldBreadcrumb provideProductsBreadcrumb( PlaceController placeController )
    {
        List<BreadcrumbItem> items = new ArrayList<>();
        items.add( new BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new BreadcrumbItem( IconType.TABLET_MAC, messages.labelProducts() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    // -- event bus

    @Binds
    @Singleton
    abstract EventBus provideEventBus( AppEventBus bus );

    // -- MVP

    @Binds
    @Singleton
    abstract ProductsPresenter.IView provideProductsView( ProductsView view );


    @Binds
    @Singleton
    abstract EditProductPresenter.IView provideEditProductView( EditProductView view );
}
