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

package biz.turnonline.ecosystem.widget.purchase;

import biz.turnonline.ecosystem.widget.purchase.place.Expenses;
import biz.turnonline.ecosystem.widget.purchase.place.HistoryMapper;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrders;
import biz.turnonline.ecosystem.widget.purchase.presenter.ExpensesPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.IncomingInvoiceDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.PurchaseOrderDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.PurchaseOrdersPresenter;
import biz.turnonline.ecosystem.widget.purchase.view.ExpensesView;
import biz.turnonline.ecosystem.widget.purchase.view.IncomingInvoiceDetailsView;
import biz.turnonline.ecosystem.widget.purchase.view.PurchaseOrderDetailsView;
import biz.turnonline.ecosystem.widget.purchase.view.PurchaseOrdersView;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountStewardFacade;
import biz.turnonline.ecosystem.widget.shared.rest.bill.BillFacade;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductBillingFacade;
import biz.turnonline.ecosystem.widget.shared.rest.payment.PaymentProcessorFacade;
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
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Purchases injection configuration module.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@Module
public abstract class PurchaseModule
{
    private static AppMessages messages = AppMessages.INSTANCE;

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
        handler.register( controller, eventBus, PurchaseEntryPoint.DEFAULT_PLACE );
        return handler;
    }

    @Singleton
    @Provides
    static Configuration provideConfiguration()
    {
        return Configuration.get();
    }

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
    static PaymentProcessorFacade providePaymentProcessor()
    {
        return GWT.create( PaymentProcessorFacade.class );
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

    @Provides
    @Singleton
    @Named( "ViewPurchaseOrderBreadcrumb" )
    static ScaffoldBreadcrumb provideViewPurchaseOrderBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new PurchaseOrders(), IconType.SHOPPING_CART, messages.labelPurchases() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT_TURNED_IN, messages.labelOrder() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    @Provides
    @Singleton
    @Named( "ViewIncomingInvoiceBreadcrumb" )
    static ScaffoldBreadcrumb provideViewIncomingInvoiceBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new PurchaseOrders(), IconType.SHOPPING_CART, messages.labelPurchases() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new PurchaseOrders(), IconType.ASSIGNMENT_TURNED_IN, messages.labelOrders() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT, messages.labelInvoice() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    @Provides
    @Singleton
    @Named( "PurchaseOrdersBreadcrumb" )
    static ScaffoldBreadcrumb providePurchaseOrdersBreadcrumb( PlaceController controller )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new PurchaseOrders(), IconType.SHOPPING_CART, messages.labelPurchases() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT_TURNED_IN, messages.labelOrders() ) );

        return new ScaffoldBreadcrumb( items, controller, provideInvoicesNavigationLink( controller ) );
    }

    @Provides
    @Singleton
    @Named( "IncomingInvoicesBreadcrumb" )
    static ScaffoldBreadcrumb provideIncomingInvoicesBreadcrumb( PlaceController controller )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new PurchaseOrders(), IconType.SHOPPING_CART, messages.labelPurchases() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new PurchaseOrders(), IconType.ASSIGNMENT_TURNED_IN, messages.labelOrders() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT, messages.labelExpenses() ) );

        return new ScaffoldBreadcrumb( items, controller, provideInvoicesNavigationLink( controller ) );
    }

    private static MaterialLink provideInvoicesNavigationLink( PlaceController controller )
    {
        MaterialLink invoicesButton = new MaterialLink();
        invoicesButton.setIconType( IconType.ASSIGNMENT );
        invoicesButton.setIconColor( Color.BLACK );
        invoicesButton.setWaves( WavesType.LIGHT );
        invoicesButton.setPaddingRight( 0 );
        invoicesButton.setTooltip( messages.labelExpenses() );
        invoicesButton.addClickHandler( event -> controller.goTo( new Expenses() ) );

        return invoicesButton;
    }

    @Binds
    @Singleton
    abstract ActivityMapper provideActivityMapper( PurchaseController controller );

    @Binds
    @Singleton
    abstract EventBus provideEventBus( AppEventBus bus );

    @Binds
    @Singleton
    abstract PurchaseOrdersPresenter.IView providePurchaseOrdersView( PurchaseOrdersView view );

    @Binds
    @Singleton
    abstract PurchaseOrderDetailsPresenter.IView providePurchaseOrderDetailsView( PurchaseOrderDetailsView view );

    @Binds
    @Singleton
    abstract ExpensesPresenter.IView provideIncomingInvoicesView( ExpensesView view );

    @Binds
    @Singleton
    abstract IncomingInvoiceDetailsPresenter.IView provideIncomingInvoiceDetailsView( IncomingInvoiceDetailsView view );
}
