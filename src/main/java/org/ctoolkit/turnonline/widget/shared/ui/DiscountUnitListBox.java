package org.ctoolkit.turnonline.widget.shared.ui;

import org.ctoolkit.turnonline.widget.shared.AppMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DiscountUnitListBox
        extends StaticCodeBookListBox
{
    private static List<StaticCodeBook> units = new ArrayList<>(  );

    private static final AppMessages messages = AppMessages.INSTANCE;

    static {
        units.add( new StaticCodeBook( "PERCENTAGE", messages.labelPercentage() ) );
        units.add( new StaticCodeBook( "AMOUNT", messages.labelFixed() ) );
    }


    @Override
    protected List<StaticCodeBook> values()
    {
        return units;
    }
}
