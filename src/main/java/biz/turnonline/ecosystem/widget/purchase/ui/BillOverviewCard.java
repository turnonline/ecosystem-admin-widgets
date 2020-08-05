/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.purchase.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Bills;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.UploaderAssociatedIdChangeEvent;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Scan;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Supplier;
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
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static biz.turnonline.ecosystem.widget.shared.rest.bill.Bill.TypeEnum.INVOICE;
import static biz.turnonline.ecosystem.widget.shared.rest.bill.Bill.TypeEnum.RECEIPT;
import static gwt.material.design.client.constants.Color.BROWN_LIGHTEN_2;
import static gwt.material.design.client.constants.Color.GREEN;
import static gwt.material.design.client.constants.Color.RED;
import static gwt.material.design.client.constants.Color.TEAL_LIGHTEN_2;
import static gwt.material.design.client.constants.IconType.EDIT;
import static gwt.material.design.client.constants.IconType.VISIBILITY;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillOverviewCard
        extends Composite
{
    private static BillCardUiBinder binder = GWT.create( BillCardUiBinder.class );

    private final AppEventBus bus;

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
    MaterialLabel description;

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

    @UiField
    MaterialIcon approved;

    private final Bill bill;

    private final AppMessages messages = AppMessages.INSTANCE;

    public BillOverviewCard( @Nonnull Bill bill, AppEventBus bus )
    {
        this.bill = bill;
        this.bus = bus;

        initWidget( binder.createAndBindUi( this ) );

        description.setText( Optional.ofNullable( bill.getDescription() ).orElse( "-" ) );
        billNumber.setText( Optional.ofNullable( bill.getBillNumber() ).orElse( "-" ) );
        type.setText( typeText( bill.getType().name() ) );
        type.setBackgroundColor( typeColor( bill.getType().name() ) );
        totalPrice.setValue( bill.getTotalPrice(), bill.getCurrency() );
        supplier.setValue( formatSupplier( bill.getSupplier() ) );
        dateOfIssue.setValue( DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_FULL ).format( bill.getDateOfIssue() ) );

        // bill image
        List<Scan> scans = Optional.ofNullable( bill.getScans() ).orElse( new ArrayList<>() );
        Scan scan = scans.isEmpty() ? new Scan() : scans.get( 0 );

        boolean hasImageUrl = scan.getServingUrl() != null;
        billImage.getElement().getStyle().setProperty( "margin", "auto" );
        if ( hasImageUrl )
        {
            billImage.setUrl( scan.getServingUrl() );
            billImage.addClickHandler( e -> overlay.open( billImage ) );
            overlayImage.setUrl( scan.getServingUrl() + "=s1200" );
            btnCloseOverlay.addClickHandler( e -> overlay.close() );
            btnCloseOverlay.getElement().getStyle().setProperty( "margin", "auto" );
            btnCloseOverlay.setMarginBottom( 10 );
            overlay.addClickHandler( event -> overlay.close() );
        }
        else
        {
            billImage.setVisible( false );
        }

        if ( Optional.ofNullable( bill.isApproved() ).orElse( false ) )
        {
            approved.setBackgroundColor( GREEN );
            approved.setIconType( IconType.ASSIGNMENT_TURNED_IN );
            approved.setTooltip( messages.tooltipBillApproved() );

            editLink.setIconType( VISIBILITY );
        }
        else
        {
            approved.setBackgroundColor( RED );
            approved.setIconType( IconType.ASSIGNMENT_LATE );
            approved.setTooltip( messages.tooltipBillWaitingForApproval() );

            editLink.setIconType( EDIT );
        }

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
        bus.fireEvent( new UploaderAssociatedIdChangeEvent( bill.getId() ) );
        bus.fireEvent( new EditBillEvent( bill.getId() ) );
    }

    private Color typeColor( String type )
    {
        if ( type.equalsIgnoreCase( RECEIPT.name() ) )
        {
            return BROWN_LIGHTEN_2;
        }

        return TEAL_LIGHTEN_2;
    }

    private String typeText( String type )
    {
        if ( type.equalsIgnoreCase( RECEIPT.name() ) )
        {
            return messages.labelReceipt();
        }

        if ( type.equalsIgnoreCase( INVOICE.name() ) )
        {
            return messages.labelInvoice();
        }

        return "-";
    }

    private String formatSupplier( Supplier supplier )
    {
        if ( supplier == null )
        {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        if ( supplier.getBusinessName() != null )
        {
            sb.append( supplier.getBusinessName() );
        }

        if ( supplier.getCompanyId() != null )
        {
            sb.append( sb.length() == 0 ? sb.append( supplier.getCompanyId() ) : " [" + supplier.getCompanyId() + "]" );
        }

        if ( sb.length() == 0 )
        {
            sb.append( "-" );
        }

        return sb.toString();
    }

    interface BillCardUiBinder
            extends UiBinder<MaterialCard, BillOverviewCard>
    {
    }
}
