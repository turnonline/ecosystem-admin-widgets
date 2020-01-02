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

package biz.turnonline.ecosystem.widget.purchase;

import biz.turnonline.ecosystem.widget.purchase.place.HistoryMapper;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoices;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrders;
import biz.turnonline.ecosystem.widget.purchase.presenter.IncomingInvoiceDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.IncomingInvoicesPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.PurchaseOrderDetailsPresenter;
import biz.turnonline.ecosystem.widget.purchase.presenter.PurchaseOrdersPresenter;
import biz.turnonline.ecosystem.widget.purchase.view.IncomingInvoiceDetailsView;
import biz.turnonline.ecosystem.widget.purchase.view.IncomingInvoicesView;
import biz.turnonline.ecosystem.widget.purchase.view.PurchaseOrderDetailsView;
import biz.turnonline.ecosystem.widget.purchase.view.PurchaseOrdersView;
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
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;

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
    static SearchFacade provideSearchFacade()
    {
        return GWT.create( SearchFacade.class );
    }

    @Provides
    @Singleton
    @Named( "ViewPurchaseOrderBreadcrumb" )
    static ScaffoldBreadcrumb provideViewPurchaseOrderBreadcrumb( PlaceController placeController )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
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
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( new PurchaseOrders(), IconType.SHOPPING_CART, messages.labelPurchases() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT, messages.labelOrder() ) );

        return new ScaffoldBreadcrumb( items, placeController );
    }

    @Provides
    @Singleton
    @Named( "PurchaseOrdersBreadcrumb" )
    static ScaffoldBreadcrumb providePurchaseOrdersBreadcrumb( PlaceController controller )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.SHOPPING_CART, messages.labelPurchases() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT_TURNED_IN, messages.labelOrders() ) );

        return new ScaffoldBreadcrumb( items, controller, providePurchaseViewTypes( controller ) );
    }

    @Provides
    @Singleton
    @Named( "IncomingInvoicesBreadcrumb" )
    static ScaffoldBreadcrumb provideIncomingInvoicesBreadcrumb( PlaceController controller )
    {
        List<ScaffoldBreadcrumb.BreadcrumbItem> items = new ArrayList<>();
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.HOME, messages.labelHome() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.SHOPPING_CART, messages.labelPurchases() ) );
        items.add( new ScaffoldBreadcrumb.BreadcrumbItem( IconType.ASSIGNMENT, messages.labelInvoices() ) );

        return new ScaffoldBreadcrumb( items, controller, providePurchaseViewTypes( controller ) );
    }

    private static MaterialRow providePurchaseViewTypes( PlaceController controller )
    {
        MaterialRow viewTypes = new MaterialRow();

        MaterialLink ordersButton = new MaterialLink();
        viewTypes.add( ordersButton );
        ordersButton.setIconType( IconType.ASSIGNMENT_TURNED_IN );
        ordersButton.setIconColor( Color.BLACK );
        ordersButton.setWaves( WavesType.LIGHT );
        ordersButton.setPaddingRight( 0 );
        ordersButton.setTooltip( messages.labelOrders() );
        ordersButton.addClickHandler( event -> controller.goTo( new PurchaseOrders() ) );

        MaterialLink invoicesButton = new MaterialLink();
        viewTypes.add( invoicesButton );
        invoicesButton.setIconType( IconType.ASSIGNMENT );
        invoicesButton.setIconColor( Color.BLACK );
        invoicesButton.setWaves( WavesType.LIGHT );
        invoicesButton.setPaddingRight( 0 );
        invoicesButton.setTooltip( messages.labelInvoices() );
        invoicesButton.addClickHandler( event -> controller.goTo( new IncomingInvoices() ) );

        return viewTypes;
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
    abstract IncomingInvoicesPresenter.IView provideIncomingInvoicesView( IncomingInvoicesView view );

    @Binds
    @Singleton
    abstract IncomingInvoiceDetailsPresenter.IView provideIncomingInvoiceDetailsView( IncomingInvoiceDetailsView view );
}
