package biz.turnonline.ecosystem.widget.shared.ui;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public enum PredefinedRange
{
    CURRENT_MONTH( () -> new Range( firstDayOfCurrentMonth(), lastDayOfCurrentMonth() ) ),
    LAST_MONTH( () -> new Range( firstDayOfPreviousMonth(), lastDayOfPreviousMonth() ) ),
    LAST_3_MONTHS( () -> new Range( firstDayOfPreviousThreeMonths(), lastDayOfCurrentMonth() ) ),
    LAST_6_MONTHS( () -> new Range( firstDayOfPreviousSixMonths(), lastDayOfCurrentMonth() ) ),

    CURRENT_YEAR( () -> new Range( firstDayOfCurrentYear(), lastDayOfCurrentYear() ) ),
    LAST_YEAR( () -> new Range( firstDayOfLastYear(), lastDayOfLastYear() ) ),

    ALL( () -> new Range( null, null ) );

    private RangeSupplier rangeSupplier;

    PredefinedRange( RangeSupplier rangeSupplier )
    {
        this.rangeSupplier = rangeSupplier;
    }

    public RangeSupplier getRangeSupplier()
    {
        return rangeSupplier;
    }

    @FunctionalInterface
    public interface RangeSupplier
            extends Serializable
    {
        Range get();
    }

    public static class Range
    {
        private final Date from;

        private final Date to;

        public Range( Date from, Date to )
        {
            this.from = from;
            this.to = to;
        }

        public Date getFrom()
        {
            return from;
        }

        public Date getTo()
        {
            return to;
        }
    }

    public static Date firstDayOfCurrentMonth()
    {
        Date firstDayOfMonth = new Date();
        firstDayOfMonth.setDate( 1 );
        return firstDayOfMonth;
    }

    public static Date firstDayOfPreviousMonth()
    {
        Date firstDayOfLastMonth = firstDayOfCurrentMonth();
        firstDayOfLastMonth.setMonth( firstDayOfLastMonth.getMonth() - 1 );
        return firstDayOfLastMonth;
    }

    public static Date firstDayOfPreviousThreeMonths()
    {
        Date firstDayOfLastMonth = firstDayOfCurrentMonth();
        firstDayOfLastMonth.setMonth( firstDayOfLastMonth.getMonth() - 2 );
        return firstDayOfLastMonth;
    }

    public static Date firstDayOfPreviousSixMonths()
    {
        Date firstDayOfLastMonth = firstDayOfCurrentMonth();
        firstDayOfLastMonth.setMonth( firstDayOfLastMonth.getMonth() - 5 );
        return firstDayOfLastMonth;
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

    public static Date firstDayOfCurrentYear() {
        Date firstDayOfCurrentYear = new Date();
        firstDayOfCurrentYear.setDate( 1 );
        firstDayOfCurrentYear.setMonth( 0 );

        return firstDayOfCurrentYear;
    }

    public static Date lastDayOfCurrentYear() {
        Date firstDayOfCurrentYear = new Date();
        firstDayOfCurrentYear.setHours( 0 );
        firstDayOfCurrentYear.setMinutes( 0 );
        firstDayOfCurrentYear.setSeconds( 0 );
        firstDayOfCurrentYear.setMonth( 11 );
        firstDayOfCurrentYear.setDate( 31 );

        return firstDayOfCurrentYear;
    }

    public static Date firstDayOfLastYear() {
        Date firstDayOfLastYear = firstDayOfCurrentYear();
        firstDayOfLastYear.setYear( firstDayOfCurrentYear().getYear() - 1);

        return firstDayOfLastYear;
    }

    public static Date lastDayOfLastYear() {
        Date lastDayOfLastYear = lastDayOfCurrentYear();
        lastDayOfLastYear.setYear( firstDayOfCurrentYear().getYear() - 1);

        return lastDayOfLastYear;
    }
}
