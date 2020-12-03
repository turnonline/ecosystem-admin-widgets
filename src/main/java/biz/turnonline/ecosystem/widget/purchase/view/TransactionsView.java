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

import biz.turnonline.ecosystem.widget.purchase.presenter.TransactionsPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnTransactionAmount;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnTransactionBank;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnTransactionCompletedAt;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnTransactionMerchant;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnTransactionStatus;
import biz.turnonline.ecosystem.widget.purchase.ui.TransactionsDataSource;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTable;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class TransactionsView
        extends View<List<Transaction>>
        implements TransactionsPresenter.IView
{
    private static final TransactionsViewUiBinder binder = GWT.create( TransactionsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    SmartTable<Transaction> table;

    @Inject
    public TransactionsView( @Named( "TransactionsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.CONTACTS );

        add( binder.createAndBindUi( this ) );
        initTable();

        // refresh action setup
        breadcrumb.setRefreshTooltip( messages.tooltipTransactionListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> refresh() );

        breadcrumb.setClearFilterVisible( false );
    }

    @Override
    public void refresh()
    {
        table.refresh();
    }

    private void initTable()
    {
        ColumnTransactionStatus status = new ColumnTransactionStatus();
        status.width( "10%" );

        ColumnTransactionAmount amount = new ColumnTransactionAmount();
        amount.width( "10%" );

        ColumnTransactionCompletedAt completedAt = new ColumnTransactionCompletedAt();
        completedAt.width("10%");

        ColumnTransactionBank bank = new ColumnTransactionBank();
        bank.width("10%");

        ColumnTransactionMerchant merchant = new ColumnTransactionMerchant();
        merchant.width("10%");

        table.addColumn( messages.labelStatus(), status );
        table.addColumn( messages.labelPayment(), amount );
        table.addColumn( messages.labelCompletedAt(), completedAt );
        table.addColumn( messages.labelBank(), bank );
        table.addColumn( messages.labelMerchant(), merchant );

        table.configure( new TransactionsDataSource( ( AppEventBus ) bus() ) );
        table.getPager().setLimit( 100 );
        table.getPager().getRowSelection().setVisible( false );
    }

    interface TransactionsViewUiBinder
            extends UiBinder<HTMLPanel, TransactionsView>
    {
    }
}