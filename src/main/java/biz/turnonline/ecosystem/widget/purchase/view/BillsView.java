/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.ApproveAllBillsEvent;
import biz.turnonline.ecosystem.widget.purchase.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.BillsPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.BillOverviewCard;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.BatchDropBox;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScrollLoader;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRange;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRangeListBox;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Configuration.BILLING_PROCESSOR_STORAGE;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BillsView
        extends View<List<Bill>>
        implements BillsPresenter.IView
{
    private static final BillsViewUiBinder binder = GWT.create( BillsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    PredefinedRangeListBox range;

    @UiField
    MaterialButton approveAll;

    @UiField( provided = true )
    BatchDropBox batchDropBox = new BatchDropBox( BILLING_PROCESSOR_STORAGE );

    @UiField( provided = true )
    InfiniteScroll<Bill> scroll = new InfiniteScroll<Bill>( 0, Integer.MAX_VALUE, 10000 )
    {
        @Override
        protected void onLoad()
        {
            super.onLoad();
            scroll.addLoadedHandler( event -> getElement().getStyle().setProperty( "height", "auto" ) );
        }
    };

    @UiField
    MaterialAnchorButton newBill;

    @UiField
    ConfirmationWindow approveAllConfirmation;

    private List<Bill> cachedResults = new ArrayList<>();

    @Inject
    public BillsView( @Named( "BillsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.BILLS );

        batchDropBox.setAcceptedFiles( "image/*,application/pdf" );

        add( binder.createAndBindUi( this ) );

        // refresh action setup
        breadcrumb.setRefreshTooltip( messages.tooltipBillListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> scroll.reload() );

        breadcrumb.setClearFilterVisible( false );

        approveAllConfirmation.getBtnOk()
                .addClickHandler( event -> bus().fireEvent( new ApproveAllBillsEvent( cachedResults ) ) );

        scroll.setOverflow( Style.Overflow.VISIBLE );
        scroll.setRenderer( this::createCard );
        scroll.setInfiniteScrollLoader( new InfiniteScrollLoader( messages.labelBillLoading() ) );
        scroll.addLoadedHandler( e -> cachedResults = e.getResult().getData() );

        range.setSingleValue( PredefinedRange.CURRENT_MONTH );
        range.addValueChangeHandler( event -> {
            BillScrollCallback dataSource = ( BillScrollCallback ) scroll.getDataSource();

            PredefinedRange.Range range = this.range.getSingleValue().getRangeSupplier().get();
            dataSource.setFrom( range.getFrom() );
            dataSource.setTo( range.getTo() );

            scroll.reload();
        } );
    }

    private Widget createCard( Bill bill )
    {
        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
        column.add( new BillOverviewCard( bill, ( AppEventBus ) bus() ) );
        return column;
    }

    @Override
    public void scrollTo( @Nullable String scrollspy )
    {
        scroll.scrollTo( scrollspy );
    }

    @Override
    public void setDataSource( BillScrollCallback callback )
    {
        scroll.setDataSource( callback );
    }

    @UiHandler( "newBill" )
    public void handleNew( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new EditBillEvent() );
    }

    @UiHandler( "approveAll" )
    public void handleApproveAll( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        if ( cachedResults.isEmpty() )
        {
            return;
        }

        approveAllConfirmation.open( new ConfirmationWindow.Question()
        {
            @Override
            public int selectedRecords()
            {
                return cachedResults.size();
            }

            @Override
            public String msgOneRecord()
            {
                return msgMultipleRecords();
            }

            @Override
            public String msgMultipleRecords()
            {
                return messages.questionApproveSelectedBills( selectedRecords() );
            }
        } );
    }

    interface BillsViewUiBinder
            extends UiBinder<HTMLPanel, BillsView>
    {
    }
}