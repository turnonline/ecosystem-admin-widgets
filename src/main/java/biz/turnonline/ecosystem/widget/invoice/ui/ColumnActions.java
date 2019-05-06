package biz.turnonline.ecosystem.widget.invoice.ui;

import biz.turnonline.ecosystem.widget.invoice.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Invoice;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnActions
        extends WidgetColumn<Invoice, MaterialButton>
{
    protected AppMessages messages = AppMessages.INSTANCE;

    private final EventBus eventBus;

    public ColumnActions( EventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public MaterialButton getValue( Invoice value )
    {
        MaterialButton btnEdit = new MaterialButton();
        btnEdit.addClickHandler( event -> {
            event.stopPropagation();
            eventBus.fireEvent( new EditInvoiceEvent( value ) );
        } );

        btnEdit.setType( ButtonType.FLOATING );
        btnEdit.setBackgroundColor( Color.BLUE );

        btnEdit.setIconType( IconType.EDIT );
        btnEdit.setIconColor( Color.WHITE );
        btnEdit.setWaves( WavesType.DEFAULT );
        btnEdit.setSize( ButtonSize.MEDIUM );

        btnEdit.setTooltip( messages.tooltipEditInvoice() );

        return btnEdit;
    }
}