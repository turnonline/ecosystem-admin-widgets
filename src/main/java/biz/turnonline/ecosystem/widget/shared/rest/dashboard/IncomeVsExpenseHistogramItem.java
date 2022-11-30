package biz.turnonline.ecosystem.widget.shared.rest.dashboard;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat.MONTH;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class IncomeVsExpenseHistogramItem
{
    public interface IncomeVsExpenseHistogramItemObjectMapper
            extends ObjectMapper<List<IncomeVsExpenseHistogramItem>>
    {
    }

    private int month;

    private String monthHuman;

    private BigDecimal income;

    private BigDecimal expense;

    private BigDecimal diff;

    public IncomeVsExpenseHistogramItem( int month, IncomeVsExpenseItem incomeVsExpanseItem )
    {
        Date dateWithOnlyMonth = new Date();
        dateWithOnlyMonth.setDate( 10 );
        dateWithOnlyMonth.setMonth( month );

        this.month = month;
        this.monthHuman = DateTimeFormat.getFormat( MONTH ).format( dateWithOnlyMonth );
        this.income = incomeVsExpanseItem.getIncome();
        this.expense = incomeVsExpanseItem.getExpense();
        this.diff = this.income.subtract( this.expense );
    }

    public int getMonth()
    {
        return month;
    }

    public String getMonthHuman()
    {
        return monthHuman;
    }

    public BigDecimal getIncome()
    {
        return income;
    }

    public BigDecimal getExpense()
    {
        return expense;
    }

    public BigDecimal getDiff()
    {
        return diff;
    }
}
