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

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.ClearIncomingInvoicesFilterEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.IncomingInvoicesPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.IncomingInvoiceOverviewCard;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Invoice list view implemented by infinite scroll where single invoice is rendered by {@link IncomingInvoiceOverviewCard}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoicesView
        extends View<IncomingInvoice>
        implements IncomingInvoicesPresenter.IView
{
    private static InvoicesViewUiBinder binder = GWT.create( InvoicesViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    InfiniteScroll<IncomingInvoice> scroll;

    private int headerHeight;

    @Inject
    public IncomingInvoicesView( @Named( "IncomingInvoicesBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.PURCHASES );

        add( binder.createAndBindUi( this ) );

        scroll.setRenderer( this::createCard );
        scroll.setInfiniteScrollLoader( new InfiniteScrollLoader( messages.labelInvoiceLoading() ) );

        Window.addResizeHandler( event -> scroll.setMinHeight( ( event.getHeight() - headerHeight ) + "px" ) );
        Scheduler.get().scheduleDeferred( () -> {
            headerHeight = scaffoldHeader.getElement().getClientHeight()
                    + breadcrumb.getElement().getClientHeight()
                    - 22;
            scroll.setMinHeight( ( Window.getClientHeight() - headerHeight ) + "px" );
        } );

        // refresh action setup
        breadcrumb.setRefreshTooltip( messages.tooltipInvoiceListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> scroll.reload() );

        // clear filter action setup
        breadcrumb.setClearFilterEnabled( false );
        breadcrumb.setClearFilterTooltip( messages.tooltipInvoiceListClearFilter() );
        breadcrumb.addClearFilterClickHandler( event -> bus().fireEvent( new ClearIncomingInvoicesFilterEvent() ) );
    }

    @Override
    public void downloadInvoice( @Nonnull String url )
    {
        JavaScriptObject newWindow = newWindow( "", "_blank", "" );
        setWindowTarget( newWindow, checkNotNull( url, "Invoice PDF URL can't be null" ) );
    }

    @Override
    public void scrollTo( @Nullable String scrollspy )
    {
        scroll.scrollTo( scrollspy );
    }

    @Override
    public void setDataSource( InfiniteScroll.Callback<IncomingInvoice> callback )
    {
        scroll.unload();
        scroll.setDataSource( callback );
    }

    @Override
    public void clear()
    {
        scroll.unload();
    }

    @Override
    public void setClearFilterEnabled( boolean enabled )
    {
        breadcrumb.setClearFilterEnabled( enabled );
    }

    private Widget createCard( IncomingInvoice invoice )
    {
        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
        column.add( new IncomingInvoiceOverviewCard( invoice, bus() ) );
        return column;
    }

    interface InvoicesViewUiBinder
            extends UiBinder<HTMLPanel, IncomingInvoicesView>
    {
    }
}