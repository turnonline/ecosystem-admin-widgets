package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DiscountUnitComboBox
        extends StaticCodeBookListBox
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static List<StaticCodeBook> units = new ArrayList<>();

    static
    {
        units.add( new StaticCodeBook( "PERCENTAGE", messages.labelPercentage() ) );
        units.add( new StaticCodeBook( "AMOUNT", messages.labelFixed() ) );
    }


    @Override
    protected List<StaticCodeBook> values()
    {
        return units;
    }

    @Override
    protected String defaultValue()
    {
        return "PERCENTAGE";
    }
}
