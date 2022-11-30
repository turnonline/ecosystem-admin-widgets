package biz.turnonline.ecosystem.widget.shared.rest.dashboard;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DashboardStatistics
{
    private List<IncomeVsExpenseHistogramItem> incomeVsExpenseHistogramItems = new ArrayList<>();

    private final List<IncomeVsExpenseTotalItem> incomeVsExpenseTotalItems = new ArrayList<>();

    private final List<ExpenseByCounterpartyItem> expenseByCounterpartyItems = new ArrayList<>();

    private final List<Transaction> transactions;

    public DashboardStatistics( List<Transaction> transactions )
    {
        this.transactions = transactions;
    }

    public void recalculate()
    {
        incomeVsExpenseHistogramItems.clear();
        incomeVsExpenseTotalItems.clear();
        expenseByCounterpartyItems.clear();

        calculateIncomeVsExpenseStatisticItems( transactions );
        calculateIncomeVsExpanseTotalStatistics( incomeVsExpenseHistogramItems );
        calculateExpenseByCounterpartyStatistics( transactions );
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public List<IncomeVsExpenseHistogramItem> getIncomeVsExpenseHistogramItems()
    {
        return incomeVsExpenseHistogramItems;
    }

    public List<IncomeVsExpenseTotalItem> getIncomeVsExpenseTotalItems()
    {
        return incomeVsExpenseTotalItems;
    }

    public List<ExpenseByCounterpartyItem> getExpenseByCounterpartyItems()
    {
        return expenseByCounterpartyItems;
    }

    // -- private helpers

    private void calculateIncomeVsExpenseStatisticItems( List<Transaction> transactions )
    {
        Map<Integer, IncomeVsExpenseItem> map = new LinkedHashMap<>();

        transactions.forEach( transaction -> {
            Date completedAt = transaction.getCompletedAt();
            if ( completedAt == null )
            {
                return;
            }

            int month = completedAt.getMonth();

            if ( map.containsKey( month ) )
            {
                map.get( month ).add( transaction );
            }
            else
            {
                map.put( month, new IncomeVsExpenseItem().add( transaction ) );
            }
        } );

        List<IncomeVsExpenseHistogramItem> items = new ArrayList<>();
        map.forEach( ( key, value ) ->
        {
            items.add( new IncomeVsExpenseHistogramItem( key, value ) );
        } );

        incomeVsExpenseHistogramItems = items.stream()
                        .sorted( Comparator.comparing( IncomeVsExpenseHistogramItem::getMonth ) )
                        .collect( toList() );
    }

    private void calculateIncomeVsExpanseTotalStatistics( List<IncomeVsExpenseHistogramItem> incomeVsExpanseStatisticsItem )
    {
        BigDecimal incomeTotal = incomeVsExpanseStatisticsItem.stream()
                .map( IncomeVsExpenseHistogramItem::getIncome )
                .reduce( BigDecimal::add )
                .orElse( BigDecimal.ZERO );

        BigDecimal expenseTotal = incomeVsExpanseStatisticsItem.stream()
                .map( IncomeVsExpenseHistogramItem::getExpense )
                .reduce( BigDecimal::add )
                .orElse( BigDecimal.ZERO );

        incomeVsExpenseTotalItems.add( new IncomeVsExpenseTotalItem( new Date().getYear(), incomeTotal, expenseTotal ) );
    }

    private void calculateExpenseByCounterpartyStatistics( List<Transaction> transactions )
    {
        // categorized
        expenseByCounterpartyItems.addAll(
                transactions.stream()
                        .filter( transaction -> !transaction.isCredit() )
                        .filter( transaction -> transaction.getCategories() != null )
                        .filter( transaction -> transaction.getAmount() != null )
                        .collect( Collectors.groupingBy(
                                transaction -> transaction.getCategories().get( 0 ).getName(),
                                summingDouble( Transaction::getAmount )
                        ) )
                        .entrySet()
                        .stream()
                        .map( e -> new ExpenseByCounterpartyItem( e.getKey(), e.getValue() ) )
                        .collect( toList() )
        );

        // uncategorized
        Double uncategorized = transactions.stream()
                .filter( transaction -> !transaction.isCredit() )
                .filter( transaction -> transaction.getCategories() == null )
                .map( Transaction::getAmount )
                .filter( Objects::nonNull )
                .reduce( Double::sum )
                .orElse( 0D );

        expenseByCounterpartyItems.add( new ExpenseByCounterpartyItem( "N/A", uncategorized ) );
    }
}
