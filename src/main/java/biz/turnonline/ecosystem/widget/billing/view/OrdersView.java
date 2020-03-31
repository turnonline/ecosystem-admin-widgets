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

package biz.turnonline.ecosystem.widget.billing.view;

import biz.turnonline.ecosystem.widget.billing.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.billing.presenter.OrdersPresenter;
import biz.turnonline.ecosystem.widget.billing.ui.OrderOverviewCard;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
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
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollLoader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Order list view implemented by infinite scroll where single order is rendered by {@link OrderOverviewCard}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrdersView
        extends View<List<Order>>
        implements OrdersPresenter.IView
{
    private static OrdersViewUiBinder binder = GWT.create( OrdersViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    InfiniteScroll<Order> scroll;

    @UiField
    MaterialAnchorButton newOrder;

    private int headerHeight;

    @Inject
    public OrdersView( @Named( "OrdersBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.ORDERS );

        add( binder.createAndBindUi( this ) );

        scroll.setRenderer( this::createCard );
        scroll.setInfiniteScrollLoader( new InfiniteScrollLoader( messages.labelOrderLoading() ) );

        Window.addResizeHandler( event -> scroll.setMinHeight( ( event.getHeight() - headerHeight ) + "px" ) );
        Scheduler.get().scheduleDeferred( () -> {
            headerHeight = scaffoldHeader.getElement().getClientHeight()
                    + breadcrumb.getElement().getClientHeight()
                    - 22;
            scroll.setMinHeight( ( Window.getClientHeight() - headerHeight ) + "px" );
        } );

        // refresh action setup
        breadcrumb.setRefreshTooltip( messages.tooltipOrderListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> scroll.reload() );

        breadcrumb.setClearFilterVisible( false );
    }

    @UiHandler( "newOrder" )
    public void newOrder( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new EditOrderEvent() );
    }

    private Widget createCard( Order order )
    {
        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
        column.add( new OrderOverviewCard( order, ( AppEventBus ) bus() ) );
        return column;
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
    public void setDataSource( InfiniteScroll.Callback<Order> callback )
    {
        scroll.unload();
        scroll.setDataSource( callback );
    }

    interface OrdersViewUiBinder
            extends UiBinder<HTMLPanel, OrdersView>
    {
    }
}