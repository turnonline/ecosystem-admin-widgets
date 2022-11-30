package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.JSON;
import biz.turnonline.ecosystem.widget.shared.rest.dashboard.ExpenseByCounterpartyItem;
import biz.turnonline.ecosystem.widget.shared.rest.dashboard.ExpenseByCounterpartyItem.ExpenseByCounterpartyItemObjectMapper;
import com.google.gwt.core.client.GWT;
import gwt.material.design.amcharts.client.Am4Charts;
import gwt.material.design.amcharts.client.PieChart;
import gwt.material.design.amcharts.client.series.PieSeries;
import gwt.material.design.amcore.client.Am4Core;
import gwt.material.design.amcore.client.base.Percent;
import gwt.material.design.amcore.client.color.Color;
import gwt.material.design.amcore.client.ui.Label;
import gwt.material.design.client.ui.MaterialPanel;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DebitsByCategoryChart
        extends MaterialPanel
{
    private final AppMessages messages = AppMessages.INSTANCE;

    private static ExpenseByCounterpartyItemObjectMapper mapper = GWT.create( ExpenseByCounterpartyItemObjectMapper.class );

    private String chartTitle;

    public void init( List<ExpenseByCounterpartyItem> data )
    {
        PieChart chart = ( PieChart ) Am4Core.create( this, Am4Charts.PieChart );
        chart.data = JSON.toJavaScriptObject( data, mapper );

        // title
        String icon = "<i class=\"blue-text left material-icons\" style=\"cursor: pointer; margin-right: 5px; margin-top: -3px; padding-bottom: 10px;\">pie_chart</i>";
        Label title = new Label();
        title.html = "<h6 class='blue-text'>" + icon + chartTitle + "</h6>";
        chart.titles.push( title );

        // Set inner radius
        chart.innerRadius = new Percent( 50 );

        // Add and configure Series
        PieSeries pieSeries = chart.series.push( new PieSeries() );
        pieSeries.dataFields.value = "amount";
        pieSeries.dataFields.category = "counterparty";
        pieSeries.slices.template.stroke = new Color( "#fff" );
        pieSeries.slices.template.strokeWidth = 2;
        pieSeries.slices.template.tooltipText = "{category}: [bold]{value}[/] " + Configuration.get().getCurrency();
        pieSeries.slices.template.strokeOpacity = 1;
        pieSeries.labels.template.text = "{category}";
    }

    public void setChartTitle( String chartTitle )
    {
        this.chartTitle = chartTitle;
    }
}
