package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class OrderPeriodicityComboBox
        extends StaticCodeBookListBox
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static List<StaticCodeBook> types = new ArrayList<>();

    static
    {
        types.add( new StaticCodeBook( OrderPeriodicity.ANNUALLY.name(), messages.labelAnnually() ) );
        types.add( new StaticCodeBook( OrderPeriodicity.SEMI_ANNUALLY.name(), messages.labelSemiAnnually() ) );
        types.add( new StaticCodeBook( OrderPeriodicity.QUARTERLY.name(), messages.labelQuarterly() ) );
        types.add( new StaticCodeBook( OrderPeriodicity.MONTHLY.name(), messages.labelMonthly() ) );
        types.add( new StaticCodeBook( OrderPeriodicity.WEEKLY.name(), messages.labelWeekly() ) );
        types.add( new StaticCodeBook( OrderPeriodicity.MANUALLY.name(), messages.labelManually() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return types;
    }

    @Override
    protected String defaultValue()
    {
        return OrderPeriodicity.MANUALLY.name();
    }
}
