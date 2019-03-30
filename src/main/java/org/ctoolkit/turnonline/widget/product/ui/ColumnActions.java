package org.ctoolkit.turnonline.widget.product.ui;

import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.table.cell.WidgetColumn;
import org.ctoolkit.turnonline.widget.product.event.EditProductEvent;
import org.ctoolkit.turnonline.widget.shared.AppMessages;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnActions
        extends WidgetColumn<Product, MaterialButton>
{
    protected AppMessages messages = AppMessages.INSTANCE;

    private final EventBus eventBus;

    public ColumnActions( EventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public MaterialButton getValue( Product value )
    {
        MaterialButton btnEdit = new MaterialButton();
        btnEdit.addClickHandler( event -> {
            event.stopPropagation();
            eventBus.fireEvent( new EditProductEvent( value ) );
        } );

        btnEdit.setType( ButtonType.FLOATING );
        btnEdit.setBackgroundColor( Color.BLUE );

        btnEdit.setIconType( IconType.EDIT );
        btnEdit.setIconColor( Color.WHITE );
        btnEdit.setWaves( WavesType.DEFAULT );
        btnEdit.setSize( ButtonSize.MEDIUM );

        btnEdit.setTooltip( messages.tooltipEditProduct() );

        return btnEdit;
    }
}
