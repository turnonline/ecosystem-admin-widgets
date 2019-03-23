package org.ctoolkit.turnonline.widget.contact.ui;

import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.table.cell.WidgetColumn;
import org.ctoolkit.turnonline.widget.shared.AppMessages;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnActions
        extends WidgetColumn<ContactCard, MaterialButton>
{
    protected AppMessages messages = AppMessages.INSTANCE;

    @Override
    public MaterialButton getValue( ContactCard value )
    {
        MaterialButton btnEdit = new MaterialButton();

        btnEdit.setType( ButtonType.RAISED );
        btnEdit.setBackgroundColor( Color.WHITE );
        btnEdit.setTextColor( Color.BLACK );

        btnEdit.setIconType( IconType.EDIT );
        btnEdit.setWaves( WavesType.DEFAULT );
        btnEdit.setSize( ButtonSize.MEDIUM );
        btnEdit.setValue( messages.labelEdit() );

        btnEdit.getElement().getStyle().setPaddingLeft( 10, Style.Unit.PX );
        btnEdit.getElement().getStyle().setPaddingRight( 20, Style.Unit.PX );

        btnEdit.setTooltip( messages.tooltipEditContact() );

        return btnEdit;
    }
}
