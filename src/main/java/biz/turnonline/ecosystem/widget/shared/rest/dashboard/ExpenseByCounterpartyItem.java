package biz.turnonline.ecosystem.widget.shared.rest.dashboard;

import com.github.nmorel.gwtjackson.client.ObjectMapper;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ExpenseByCounterpartyItem
{
    public interface ExpenseByCounterpartyItemObjectMapper
            extends ObjectMapper<List<ExpenseByCounterpartyItem>>
    {
    }

    private String counterparty;

    private Double amount;

    public ExpenseByCounterpartyItem( String counterparty, Double amount )
    {
        this.counterparty = counterparty;
        this.amount = amount;
    }

    public String getCounterparty()
    {
        return counterparty;
    }

    public Double getAmount()
    {
        return amount;
    }
}
