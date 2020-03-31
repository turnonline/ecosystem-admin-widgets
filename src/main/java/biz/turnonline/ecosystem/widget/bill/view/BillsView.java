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

package biz.turnonline.ecosystem.widget.bill.view;

import biz.turnonline.ecosystem.widget.bill.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.bill.event.NewBillEvent;
import biz.turnonline.ecosystem.widget.bill.presenter.BillsPresenter;
import biz.turnonline.ecosystem.widget.bill.ui.BillOverviewCard;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Scan;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Supplier;
import biz.turnonline.ecosystem.widget.shared.ui.BatchDropBox;
import biz.turnonline.ecosystem.widget.shared.ui.InfiniteScroll;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.TimelinePanel;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import org.ctoolkit.gwt.client.facade.UploadItem;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Configuration.BILLING_PROCESSOR_STORAGE;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BillsView
        extends View<List<Bill>>
        implements BillsPresenter.IView
{
    private static BillsViewUiBinder binder = GWT.create( BillsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField( provided = true )
    BatchDropBox batchDropBox = new BatchDropBox( BILLING_PROCESSOR_STORAGE )
    {
        @Override
        public void onUpload( UploadItem uploadItem )
        {
            BillsView.this.createNewBill( uploadItem );
        }
    };

    @UiField( provided = true )
    TimelinePanel<Bill> timelinePanel = new TimelinePanel<Bill>( messages.labelBillLoading() )
    {
        @Override
        protected Widget createCard( Bill model )
        {
            return BillsView.this.createCard( model );
        }
    };

    @UiField
    MaterialAnchorButton newBill;

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
        breadcrumb.addRefreshClickHandler( event -> timelinePanel.reload() );

        breadcrumb.setClearFilterVisible( false );
    }

    private Widget createCard( Bill bill )
    {
        MaterialColumn column = new MaterialColumn( 12, 6, 6 );
        column.add( new BillOverviewCard( bill, ( AppEventBus ) bus() ) );
        return column;
    }

    private void createNewBill( UploadItem uploadItem )
    {
        Bill bill = new Bill();

        bill.setScans( Collections.singletonList(
                new Scan().servingUrl( uploadItem.getServingUrl() ).storageName( uploadItem.getStorageName() )
        ) );
        bill.setItemName( uploadItem.getFileName() );
        bill.setDateOfIssue( new Date() );
        bill.setSupplier( new Supplier() );
        bill.setItems( new ArrayList<>() );
        bill.setType( Bill.TypeEnum.RECEIPT );

        bus().fireEvent( new NewBillEvent( bill ) );
    }

    @Override
    public void scrollTo( @Nullable String scrollspy )
    {
        timelinePanel.scrollTo( scrollspy );
    }

    @Override
    public void setDataSourceCurrentMonth( InfiniteScroll.Callback<Bill> callback )
    {
        timelinePanel.setDataSourceCurrentMonth( callback );
    }

    @Override
    public void setDataSourceLastMonths( InfiniteScroll.Callback<Bill> callback )
    {
        timelinePanel.setDataSourceLastMonths( callback );
    }

    @UiHandler( "newBill" )
    public void handleNew( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new EditBillEvent() );
    }

    interface BillsViewUiBinder
            extends UiBinder<HTMLPanel, BillsView>
    {
    }
}