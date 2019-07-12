package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DiscountRuleComboBox
        extends StaticCodeBookListBox
{
    private static List<StaticCodeBook> rules = new ArrayList<>();

    private static final AppMessages messages = AppMessages.INSTANCE;

    static
    {
        rules.add( new StaticCodeBook( "DiscountCode", messages.labelDiscountCode() ) );
        rules.add( new StaticCodeBook( "AllChildrenCheckedIn", messages.labelAllChildrenCheckedIn() ) );
    }

    @Override
    protected List<StaticCodeBook> values()
    {
        return rules;
    }

    @Override
    protected String defaultValue()
    {
        return "DiscountCode";
    }
}
