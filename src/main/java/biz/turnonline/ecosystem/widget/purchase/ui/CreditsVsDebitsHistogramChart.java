package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.JSON;
import biz.turnonline.ecosystem.widget.shared.rest.dashboard.IncomeVsExpenseHistogramItem;
import biz.turnonline.ecosystem.widget.shared.rest.dashboard.IncomeVsExpenseHistogramItem.IncomeVsExpenseHistogramItemObjectMapper;
import com.google.gwt.core.client.GWT;
import gwt.material.design.amcharts.client.Am4Charts;
import gwt.material.design.amcharts.client.XYChart;
import gwt.material.design.amcharts.client.axis.CategoryAxis;
import gwt.material.design.amcharts.client.axis.ValueAxis;
import gwt.material.design.amcharts.client.legend.Legend;
import gwt.material.design.amcharts.client.series.ColumnSeries;
import gwt.material.design.amcore.client.Am4Core;
import gwt.material.design.amcore.client.base.Percent;
import gwt.material.design.amcore.client.color.Color;
import gwt.material.design.amcore.client.color.ColorSet;
import gwt.material.design.amcore.client.ui.Label;
import gwt.material.design.client.ui.MaterialPanel;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CreditsVsDebitsHistogramChart
        extends MaterialPanel
{
    private final AppMessages messages = AppMessages.INSTANCE;

    private static IncomeVsExpenseHistogramItemObjectMapper mapper = GWT.create( IncomeVsExpenseHistogramItemObjectMapper.class );

    private String chartTitle;

    public void init( List<IncomeVsExpenseHistogramItem> data )
    {
        XYChart chart = ( XYChart ) Am4Core.create( this, Am4Charts.XYChart );
        chart.data = JSON.toJavaScriptObject( data, mapper );
        chart.legend = new Legend();
        chart.colors = new ColorSet();
        chart.colors.list = new Color[]{new Color( "#8bc34a" ), new Color( "#ef5350" ), new Color( "#9e9e9e" )};

        // title
        String icon = "<i class=\"blue-text left material-icons\" style=\"cursor: pointer; margin-right: 5px; margin-top: -3px; padding-bottom: 10px;\">pie_chart</i>";
        Label title = new Label();
        title.html = "<h6 class='blue-text'>" + icon + chartTitle + "</h6>";
        chart.titles.push( title );

        // x axis
        CategoryAxis categoryAxis = ( CategoryAxis ) chart.xAxes.push( new CategoryAxis() );
        categoryAxis.dataFields.category = "monthHuman";

        // y axis
        chart.yAxes.push( new ValueAxis() );

        // Create series
        createSeries( "income", messages.labelCredit(), chart );
        createSeries( "expense", messages.labelDebit(), chart );
        createSeries( "diff", messages.labelDiff(), chart );
    }

    private static void createSeries( String field, String name, XYChart chart )
    {
        ColumnSeries series = ( ColumnSeries ) chart.series.push( new ColumnSeries() );
        series.dataFields.valueY = field;
        series.dataFields.categoryX = "monthHuman";
        series.name = name;
        series.columns.template.tooltipText = "{name}: [bold]{valueY}[/] " + Configuration.get().getCurrency();
        series.columns.template.height = new Percent( 100 );
    }

    public void setChartTitle( String chartTitle )
    {
        this.chartTitle = chartTitle;
    }
}
