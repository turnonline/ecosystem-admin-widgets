package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import com.google.gwt.i18n.shared.DateTimeFormat;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.html.Option;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class PredefinedRangeListBox
        extends MaterialComboBox<PredefinedRange>
{
    private static AppMessages messages = AppMessages.INSTANCE;

    private static Map<PredefinedRange, String> localization = new HashMap<>();

    static
    {
        localization.put( PredefinedRange.CURRENT_MONTH, messages.labelRangeCurrentMonth() );
        localization.put( PredefinedRange.LAST_MONTH, messages.labelRangeLastMonth() );
        localization.put( PredefinedRange.LAST_3_MONTHS, messages.labelRangeLastThreeMonths() );
        localization.put( PredefinedRange.LAST_6_MONTHS, messages.labelRangeLastSixMonths() );
        localization.put( PredefinedRange.ALL, messages.labelRangeAll() );
    }

    public PredefinedRangeListBox()
    {
        setItems( Arrays.asList( PredefinedRange.values() ) );

        addValueChangeHandler( event -> setHelperText( formattedRange() ) );
    }

    @Override
    protected void onLoad()
    {
        super.onLoad();
        setHelperText( formattedRange() );
    }

    @Override
    protected Option buildOption( String text, PredefinedRange value )
    {
        Option option = super.buildOption( text, value );
        option.setText( localization.get( value ) );
        return option;
    }

    private String formattedRange()
    {
        PredefinedRange.Range range = getSingleValue().getRangeSupplier().get();
        String fromFormatted = range.getFrom() != null ? DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_SHORT ).format( range.getFrom() ) : messages.labelUnbounded();
        String toFormatted = range.getTo() != null ? DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_SHORT ).format( range.getTo() ) : messages.labelUnbounded();

        return fromFormatted + " - " + toFormatted;
    }
}
