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

package biz.turnonline.ecosystem.widget.billing;

import biz.turnonline.ecosystem.widget.billing.place.HistoryMapper;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.billing.presenter.EditInvoicePresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.EditOrderPresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.InvoicesPresenter;
import biz.turnonline.ecosystem.widget.billing.presenter.OrdersPresenter;
import biz.turnonline.ecosystem.widget.billing.view.EditInvoiceView;
import biz.turnonline.ecosystem.widget.billing.view.EditOrderView;
import biz.turnonline.ecosystem.widget.billing.view.InvoicesView;
import biz.turnonline.ecosystem.widget.billing.view.OrdersView;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountStewardFacade;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductBillingFacade;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchFacade;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
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

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@Module
public abstract class BillingModule
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
        handler.register( controller, eventBus, BillingEntryPoint.DEFAULT_PLACE );
        return handler;
    }

    @Binds
    @Singleton
    abstract ActivityMapper provideActivityMapper( BillingController controller );

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
    static AccountStewardFacade provideContactFacade()
    {
        return GWT.create( AccountStewardFacade.class );
    }

    @Singleton
    @Provides
    static ProductBillingFacade provideProductBilling()
    {
        return GWT.create( ProductBillingFacade.class );
    }

    @Singleton
    @Provides
    static SearchFacade provideSearchFacade()
    {
        return GWT.create( SearchFacade.class );
    }

    @Singleton
    @Provides
    static AddressLookupListener provideAddressLookupListener( Configuration config )
    {
        return config.initAddressLookupListener();
    }

    // -- breadcrumbs

    @Provides
    @Singleton
    @Named( "EditOrderBreadcrumb" )
    static ScaffoldBreadcrumb provideEditOrderBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new Orders(), IconType.SHOPPING_CART, messages.labelOrders() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.LIST, messages.labelEditOrder() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    @Provides
    @Singleton
    @Named( "OrdersBreadcrumb" )
    static ScaffoldBreadcrumb provideOrdersBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.SHOPPING_CART, messages.labelOrders() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    @Provides
    @Singleton
    @Named( "EditInvoiceBreadcrumb" )
    static ScaffoldBreadcrumb provideEditInvoiceBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new Invoices(), IconType.ASSIGNMENT, messages.labelInvoices() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.LIST, messages.labelEditInvoice() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    @Provides
    @Singleton
    @Named( "InvoicesBreadcrumb" )
    static ScaffoldBreadcrumb provideInvoicesBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT, messages.labelInvoices() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    // -- event bus

    @Binds
    @Singleton
    abstract EventBus provideEventBus( AppEventBus bus );

    // -- MVP

    @Binds
    @Singleton
    abstract OrdersPresenter.IView provideOrdersView( OrdersView view );


    @Binds
    @Singleton
    abstract EditOrderPresenter.IView provideEditOrderView( EditOrderView view );

    @Binds
    @Singleton
    abstract InvoicesPresenter.IView provideInvoicesView( InvoicesView view );


    @Binds
    @Singleton
    abstract EditInvoicePresenter.IView provideEditInvoiceView( EditInvoiceView view );
}
