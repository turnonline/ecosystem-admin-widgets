package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollLoader;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class TimelinePanel<T>
        extends Composite
{
    private static TimelinePanelUiBinder binder = GWT.create( TimelinePanelUiBinder.class );

    @UiField
    protected MaterialRow content;

    @UiField
    SectionTitle currentMonthTitle;

    @UiField
    SectionTitle lastMonthsTitle;

    @UiField( provided = true )
    InfiniteScroll<T> scrollCurrentMonth = new InfiniteScroll<T>( 0, Integer.MAX_VALUE, 10000 )
    {
        @Override
        protected void onLoad()
        {
            super.onLoad();
            scrollCurrentMonth.addLoadedHandler( event -> getElement().getStyle().setProperty( "height", "auto" ) );
        }
    };

    @UiField
    InfiniteScroll<T> scrollLastMonths;

    private AppMessages messages = AppMessages.INSTANCE;

    public TimelinePanel( String loaderText )
    {
        initWidget( binder.createAndBindUi( this ) );

        scrollCurrentMonth.setOverflow( Style.Overflow.VISIBLE );
        scrollCurrentMonth.setRenderer( this::createCard );
        scrollCurrentMonth.setInfiniteScrollLoader( new InfiniteScrollLoader( loaderText ) );

        scrollLastMonths.setRenderer( this::createCard );
        scrollLastMonths.setInfiniteScrollLoader( new InfiniteScrollLoader( loaderText ) );

        currentMonthTitle.setTitle( currentMonthTitle() );
        lastMonthsTitle.setTitle( lastMonthsTitle() );
    }

    public static Date firstDayOfCurrentMonth()
    {
        Date firstDayOfMonth = new Date();
        firstDayOfMonth.setDate( 1 );
        return firstDayOfMonth;
    }

    public static Date lastDayOfCurrentMonth()
    {
        Date lastDayOfCurrentMonthDate = firstDayOfCurrentMonth();
        lastDayOfCurrentMonthDate.setMonth( lastDayOfCurrentMonthDate.getMonth() + 1 );
        lastDayOfCurrentMonthDate.setDate( lastDayOfCurrentMonthDate.getDate() - 1 );

        return lastDayOfCurrentMonthDate;
    }

    public static Date lastDayOfPreviousMonth()
    {
        Date lastDayOfPreviousMonthDate = firstDayOfCurrentMonth();
        lastDayOfPreviousMonthDate.setDate( lastDayOfPreviousMonthDate.getDate() - 1 );

        return lastDayOfPreviousMonthDate;
    }

    protected abstract Widget createCard( T model );

    public void reload()
    {
        scrollCurrentMonth.reload();
        scrollLastMonths.reload();
    }

    public void scrollTo( @Nullable String scrollspy )
    {
        scrollLastMonths.scrollTo( scrollspy );
    }

    // -- helpers

    public void clear()
    {
        scrollCurrentMonth.unload();
        scrollLastMonths.unload();
    }

    public void setDataSourceCurrentMonth( InfiniteScroll.Callback<T> callback )
    {
        scrollCurrentMonth.unload();
        scrollCurrentMonth.setDataSource( callback );
    }

    public void setDataSourceLastMonths( InfiniteScroll.Callback<T> callback )
    {
        scrollLastMonths.unload();
        scrollLastMonths.setDataSource( callback );
    }

    private String currentMonthTitle()
    {
        String lastDayFormatted = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.MONTH_DAY ).format( lastDayOfCurrentMonth() );
        String firstDayFormatted = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.MONTH_DAY ).format( firstDayOfCurrentMonth() );

        return messages.labelCurrentMonth( firstDayFormatted, lastDayFormatted );
    }

    private String lastMonthsTitle()
    {
        String lastDateFormatted = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.MONTH_DAY ).format( lastDayOfPreviousMonth() );

        return messages.labelLastMonths( lastDateFormatted );
    }

    interface TimelinePanelUiBinder
            extends UiBinder<MaterialRow, TimelinePanel>
    {
    }
}
