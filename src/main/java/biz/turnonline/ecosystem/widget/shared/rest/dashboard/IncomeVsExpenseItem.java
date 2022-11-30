package biz.turnonline.ecosystem.widget.shared.rest.dashboard;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class IncomeVsExpenseItem
{
    private BigDecimal income = BigDecimal.ZERO;

    private BigDecimal expense = BigDecimal.ZERO;

    public IncomeVsExpenseItem add( Transaction transaction )
    {
        BigDecimal amount = BigDecimal.valueOf( transaction.getAmount() );

        if ( transaction.isCredit() )
        {
            income = income.add( amount );
        }
        else
        {
            expense = expense.add( amount );
        }

        return this;
    }

    public BigDecimal getIncome()
    {
        return income;
    }

    public BigDecimal getExpense()
    {
        return expense;
    }
}
