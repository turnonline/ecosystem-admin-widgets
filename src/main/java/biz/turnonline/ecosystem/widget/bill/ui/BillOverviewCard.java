package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.bill.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.bill.place.Bills;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

import javax.annotation.Nonnull;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillOverviewCard
        extends Composite
{

    private static BillCardUiBinder binder = GWT.create( BillCardUiBinder.class );

    @UiField
    MaterialCard card;

    @UiField
    MaterialLabel itemName;

    @UiField
    MaterialLink editLink;

    private final AppEventBus bus;

    private Bill bill;

    private AppMessages messages = AppMessages.INSTANCE;

    public BillOverviewCard( @Nonnull Bill bill, AppEventBus bus )
    {
        this.bill = bill;
        this.bus = bus;

        initWidget( binder.createAndBindUi( this ) );

        itemName.setText( bill.getItemName() );
        // TODO: add selected fields

        card.setScrollspy( Bills.getScrollspy( bill ) );
    }

    @UiHandler( "editLink" )
    public void editLink( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        // don't add history record if there is already an another token not managing scrollspy
        if ( Bills.isCurrentTokenScrollspy() )
        {
            // add record in to history (to manage scrolling to selected card once going back), but don't fire event
            History.newItem( Bills.PREFIX + ":" + Bills.getScrollspy( bill ), false );
        }
        bus.fireEvent( new EditBillEvent( bill ) );
    }

    interface BillCardUiBinder
            extends UiBinder<MaterialCard, BillOverviewCard>
    {
    }
}
