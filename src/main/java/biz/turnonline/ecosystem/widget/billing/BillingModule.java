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
import biz.turnonline.ecosystem.widget.shared.rest.bill.BillFacade;
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
    static PlaceHistoryHandler providePlaceHistoryHandler( PlaceHistoryMapper mapper,
                                                           PlaceHistoryHandler.Historian historian,
                                                           PlaceController controller,
                                                           EventBus eventBus )
    {
        PlaceHistoryHandler handler = new PlaceHistoryHandler( mapper, historian );
        handler.register( controller, eventBus, BillingEntryPoint.DEFAULT_PLACE );
        return handler;
    }

    @Singleton
    @Provides
    static Configuration provideConfiguration()
    {
        return Configuration.get();
    }

    // -- configuration

    @Singleton
    @Provides
    static AccountStewardFacade provideContactFacade()
    {
        return GWT.create( AccountStewardFacade.class );
    }

    // rest facade

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
    static BillFacade provideBillFacade()
    {
        return GWT.create( BillFacade.class );
    }

    @Singleton
    @Provides
    static AddressLookupListener provideAddressLookupListener( Configuration config )
    {
        return config.initAddressLookupListener();
    }

    @Provides
    @Singleton
    @Named( "EditOrderBreadcrumb" )
    static ScaffoldBreadcrumb provideEditOrderBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new Orders(), IconType.ASSIGNMENT_TURNED_IN, messages.labelOrders() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.LIST, messages.labelEditOrder() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    // -- breadcrumbs

    @Provides
    @Singleton
    @Named( "OrdersBreadcrumb" )
    static ScaffoldBreadcrumb provideOrdersBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT_TURNED_IN, messages.labelOrders() ) );

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

    @Binds
    @Singleton
    abstract ActivityMapper provideActivityMapper( BillingController controller );

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
