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
}
