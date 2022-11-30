package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.DashboardRangeChangeEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.DashboardPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.CreditsVsDebitsHistogramChart;
import biz.turnonline.ecosystem.widget.purchase.ui.CreditsVsDebitsTotalChart;
import biz.turnonline.ecosystem.widget.purchase.ui.DebitsByCategoryChart;
import biz.turnonline.ecosystem.widget.purchase.ui.TransactionFullTextSearch;
import biz.turnonline.ecosystem.widget.shared.rest.dashboard.DashboardStatistics;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRange;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRangeListBox;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DashboardView
        extends View<DashboardStatistics>
        implements DashboardPresenter.IView
{
    private static final DashboardViewUiBinder binder = GWT.create( DashboardViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    PredefinedRangeListBox range;

    @UiField
    TransactionFullTextSearch transactionExcluder;

    @UiField
    MaterialRow chartsRoot;

    @UiField
    CreditsVsDebitsHistogramChart incomeVsExpensesHistogram;

    @UiField
    CreditsVsDebitsTotalChart incomeVsExpensesTotalChart;

    @UiField
    DebitsByCategoryChart expensesByCategoryChart;

    @UiField
    MaterialPanel loader;

    @Inject
    public DashboardView( @Named( "DashboardViewBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.DASHBOARD );

        add( binder.createAndBindUi( this ) );

        range.setSingleValue( PredefinedRange.CURRENT_YEAR );
        range.addValueChangeHandler( event -> bus().fireEvent( new DashboardRangeChangeEvent( this.range.getSingleValue() ) ) );

        transactionExcluder.addSelectionHandler( event -> {
            String transactionId = event.getSelectedItem().getReplacementString();

            DashboardView.this.getRawModel().getTransactions().removeIf( t -> t.getTransactionId().toString().equals( transactionId ) );
            DashboardView.this.initCharts( DashboardView.this.getRawModel() );
        } );
        transactionExcluder.addTransactionSuggestRemoveHandler( event -> {
            DashboardView.this.getRawModel().getTransactions().add( event.getTransaction() );
            DashboardView.this.initCharts( DashboardView.this.getRawModel() );
        } );
    }

    @Override
    public void show()
    {
        super.show();

        loader.setDisplay( Display.BLOCK );
        chartsRoot.setDisplay( Display.NONE );
    }

    @Override
    protected void afterSetModel( DashboardStatistics statistics )
    {
        loader.setDisplay( Display.NONE );
        chartsRoot.setDisplay( Display.BLOCK );

        initCharts( statistics );

        transactionExcluder.setValue( new ArrayList<>() );
        transactionExcluder.setTransactions( statistics.getTransactions() );
    }

    @Override
    public void setPredefinedRange( PredefinedRange range )
    {
        this.range.setSingleValue( range );
    }

    private void initCharts( DashboardStatistics statistics )
    {
        statistics.recalculate();

        incomeVsExpensesHistogram.init( statistics.getIncomeVsExpenseHistogramItems() );
        incomeVsExpensesTotalChart.init( statistics.getIncomeVsExpenseTotalItems() );
        expensesByCategoryChart.init( statistics.getExpenseByCounterpartyItems() );
    }

    interface DashboardViewUiBinder
            extends UiBinder<HTMLPanel, DashboardView>
    {
    }
}
