package biz.turnonline.ecosystem.widget.shared.rest.dashboard;

import com.github.nmorel.gwtjackson.client.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class IncomeVsExpenseTotalItem
{
    public interface IncomeVsExpenseTotalItemObjectMapper
            extends ObjectMapper<List<IncomeVsExpenseTotalItem>>
    {
    }

    private int year;

    private BigDecimal income;

    private BigDecimal expense;

    private BigDecimal diff;

    public IncomeVsExpenseTotalItem( int year, BigDecimal income, BigDecimal expense )
    {
        this.year = year;
        this.income = income;
        this.expense = expense;
        this.diff = income.subtract( expense );
    }

    public int getYear()
    {
        return year;
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
