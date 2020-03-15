package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.bill.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.bill.place.Bills;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.PriceLabel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.rest.bill.Bill.TypeEnum.CASH_REGISTER_DOCUMENT;
import static gwt.material.design.client.constants.Color.BLUE;
import static gwt.material.design.client.constants.Color.GREEN;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillOverviewCard
        extends Composite
{
    private static BillCardUiBinder binder = GWT.create( BillCardUiBinder.class );

    @UiField
    MaterialImage billImage;

    @UiField
    MaterialOverlay overlay;

    @UiField
    MaterialImage overlayImage;

    @UiField
    MaterialButton btnCloseOverlay;

    @UiField
    MaterialCard card;

    @UiField
    MaterialLabel itemName;

    @UiField
    MaterialLabel billNumber;

    @UiField
    MaterialChip type;

    @UiField
    MaterialLabel supplier;

    @UiField
    MaterialLabel dateOfIssue;

    @UiField
    PriceLabel totalPrice;

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
        billNumber.setText( bill.getBillNumber() );
        type.setText( typeText( bill.getType().name() ) );
        type.setBackgroundColor( typeColor( bill.getType().name() ) );
        totalPrice.setValue( bill.getTotalPrice(), bill.getCurrency() );
        supplier.setValue( bill.getSupplier().getBusinessName() + " [" + bill.getSupplier().getCompanyId() + "]" );
        dateOfIssue.setValue( DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_FULL).format( bill.getDateOfIssue() ) );

        // bill image
        boolean hasImageUrl = bill.getServingUrl() != null;
        billImage.getElement().getStyle().setProperty( "margin", "auto" );
        if ( hasImageUrl )
        {
            billImage.setUrl( bill.getServingUrl() );
            billImage.addClickHandler( e -> overlay.open( billImage ) );
            overlayImage.setUrl( bill.getServingUrl() + "=s1200" );
            btnCloseOverlay.addClickHandler( e -> overlay.close() );
            btnCloseOverlay.getElement().getStyle().setProperty( "margin", "auto" );
            btnCloseOverlay.setMarginBottom( 10 );
            overlay.addClickHandler( event -> overlay.close() );
        }
        else
        {
            billImage.setVisible( false );
        }

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

    private Color typeColor( String type )
    {
        if ( type.equalsIgnoreCase( CASH_REGISTER_DOCUMENT.name() ) )
        {
            return GREEN;
        }

        return BLUE;
    }

    private String typeText( String type )
    {
        if ( type.equalsIgnoreCase( CASH_REGISTER_DOCUMENT.name() ) )
        {
            return messages.labelCashRegisterDocument();
        }

        return messages.labelIncomingInvoice();
    }

    interface BillCardUiBinder
            extends UiBinder<MaterialCard, BillOverviewCard>
    {
    }
}
