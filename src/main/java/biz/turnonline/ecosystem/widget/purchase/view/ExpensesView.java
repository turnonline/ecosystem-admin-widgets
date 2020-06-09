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

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.ClearIncomingInvoicesFilterEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.ExpensesPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.ExpenseOverviewCard;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Expense;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScrollLoader;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Invoice list view implemented by infinite scroll where single invoice is rendered by {@link ExpenseOverviewCard}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ExpensesView
        extends View<Expense>
        implements ExpensesPresenter.IView
{
    private static InvoicesViewUiBinder binder = GWT.create( InvoicesViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    InfiniteScroll<Expense> scroll;

    private int headerHeight;

    @Inject
    public ExpensesView( @Named( "IncomingInvoicesBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
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
        breadcrumb.setRefreshTooltip( messages.tooltipExpenseListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> scroll.reload() );

        // clear filter action setup
        breadcrumb.setClearFilterEnabled( false );
        breadcrumb.setClearFilterTooltip( messages.tooltipInvoiceListClearFilter() );
        breadcrumb.addClearFilterClickHandler( event -> bus().fireEvent( new ClearIncomingInvoicesFilterEvent() ) );
    }

    @Override
    public void downloadDocument( @Nonnull String url )
    {
        JavaScriptObject newWindow = newWindow( "", "_blank", "" );
        setWindowTarget( newWindow, checkNotNull( url, "PDF URL can't be null" ) );
    }

    @Override
    public void scrollTo( @Nullable String scrollspy )
    {
        scroll.scrollTo( scrollspy );
    }

    @Override
    public void setDataSource( InfiniteScroll.Callback<Expense> callback )
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

    private Widget createCard( Expense invoice )
    {
        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
        column.add( new ExpenseOverviewCard( invoice, bus() ) );
        return column;
    }

    interface InvoicesViewUiBinder
            extends UiBinder<HTMLPanel, ExpensesView>
    {
    }
}