/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderStatus;
import biz.turnonline.ecosystem.widget.shared.ui.PriceLabel;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.presenter.Presenter.success;
import static biz.turnonline.ecosystem.widget.shared.presenter.Presenter.warn;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ACTIVE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ISSUE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.SUSPENDED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.TRIALING;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.MANUALLY;
import static gwt.material.design.client.constants.Color.BLUE;
import static gwt.material.design.client.constants.Color.BLUE_GREY_DARKEN_2;
import static gwt.material.design.client.constants.Color.CYAN;
import static gwt.material.design.client.constants.Color.CYAN_DARKEN_1;
import static gwt.material.design.client.constants.Color.CYAN_DARKEN_2;
import static gwt.material.design.client.constants.Color.CYAN_LIGHTEN_1;
import static gwt.material.design.client.constants.Color.GREEN;
import static gwt.material.design.client.constants.Color.GREY;
import static gwt.material.design.client.constants.Color.RED_DARKEN_2;
import static gwt.material.design.client.constants.Color.RED_LIGHTEN_2;
import static gwt.material.design.client.constants.Color.YELLOW;

/**
 * Order overview card component.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrderOverviewCard
        extends Composite
{
    private static OrderCardUiBinder binder = GWT.create( OrderCardUiBinder.class );

    private final AppEventBus bus;

    @UiField
    MaterialCard card;

    @UiField
    MaterialChip id;

    @UiField
    MaterialLabel title;

    @UiField
    MaterialChip orderStatus;

    @UiField
    MaterialChip periodicity;

    @UiField
    PriceLabel totalPriceExclVat;

    @UiField
    PriceLabel totalPrice;

    @UiField
    MaterialDatePicker lastBillingDate;

    @UiField
    MaterialDatePicker nextBillingDate;

    @UiField
    MaterialLink editLink;

    @UiField
    MaterialLink activate;

    @UiField
    MaterialLink pause;

    @UiField
    MaterialLink issueInvoice;

    @UiField
    MaterialImage debtorLogo;

    @UiField
    MaterialIcon through;

    private Order order;

    private AppMessages messages = AppMessages.INSTANCE;

    public OrderOverviewCard( @Nonnull Order order, @Nonnull AppEventBus bus )
    {
        this.bus = bus;
        this.order = order;

        initWidget( binder.createAndBindUi( this ) );

        card.setScrollspy( Orders.getScrollspy( order ) );
        id.setText( String.valueOf( order.getId() ) );

        orderStatus.getChipLabel().setHideOn( HideOn.HIDE_ON_MED );
        periodicity.getChipLabel().setHideOn( HideOn.HIDE_ON_MED );
        id.setHideOn( HideOn.HIDE_ON_MED );

        // customer as a title
        Customer customer = order.getCustomer();
        String name = "";
        if ( customer != null )
        {
            if ( customer.getCompany() )
            {
                name = customer.getBusinessName();
                String logo = customer.getLogoServingUrl();
                if ( !Strings.isNullOrEmpty( logo ) )
                {
                    debtorLogo.setUrl( logo );
                }
            }
            else
            {
                name = customer.getFirstName() + " " + customer.getLastName();
            }
        }
        title.setText( name );

        boolean isEcosystemCustomerAccount = customer != null && customer.getAccountId() != null;
        through.setVisible( isEcosystemCustomerAccount );

        if ( isEcosystemCustomerAccount )
        {
            through.setIconType( IconType.EXPLICIT );
            through.setIconColor( BLUE_GREY_DARKEN_2 );
            through.setTooltip( messages.tooltipCustomerEcosystemInside() );
        }

        // order periodicity
        OrderPeriodicity periodicityEnum = order.getPeriodicity() == null
                ? MANUALLY : OrderPeriodicity.valueOf( order.getPeriodicity() );

        periodicity.setBackgroundColor( Color.WHITE );
        periodicity.setTextColor( periodicityColor( periodicityEnum ) );
        periodicity.setBorder( "1px solid" );
        periodicity.setText( periodicityText( periodicityEnum ) );

        // total price at order of current items
        String currency = order.getCurrency() == null ? bus.config().getCurrency() : order.getCurrency();
        Double priceExclVat = order.getTotalPriceExclVat();
        Double totalPrice = order.getTotalPrice();

        totalPriceExclVat.setValue( priceExclVat, currency );
        this.totalPrice.setValue( totalPrice, currency );

        // billing dates
        lastBillingDate.getLabelWidget().setFontSize( totalPriceExclVat.getFontSize() );
        lastBillingDate.setReadOnly( true );
        lastBillingDate.setValue( order.getLastBillingDate() );
        lastBillingDate.setVisible( order.getLastBillingDate() != null );

        nextBillingDate.getLabelWidget().setFontSize( totalPriceExclVat.getFontSize() );
        nextBillingDate.setReadOnly( true );
        nextBillingDate.setValue( order.getNextBillingDate() );

        // order status
        Order.Status status = order.getStatus() == null ? SUSPENDED : Order.Status.valueOf( order.getStatus() );
        nextBillingDate.setVisible( ( status == TRIALING || status == ACTIVE ) && order.getNextBillingDate() != null );

        statusChanged( status );
    }

    private void statusChanged( Order.Status status )
    {
        orderStatus.setTextColor( statusColor( status ) );
        orderStatus.setBackgroundColor( Color.WHITE );
        orderStatus.setBorder( "1px solid" );
        orderStatus.setText( statusText( status ) );
        orderStatus.setIconType( statusIconType( status ) );

        // action buttons
        activate.setVisible( status == SUSPENDED || status == ISSUE );
        pause.setVisible( status == ACTIVE );
        issueInvoice.setVisible( MANUALLY.name().equals( order.getPeriodicity() ) && (ACTIVE == status || TRIALING == status) );
    }

    @UiHandler( "editLink" )
    public void editLink( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        // don't add history record if there is already an another token not managing scrollspy
        if ( Orders.isCurrentTokenScrollspy() )
        {
            // add record in to history (to manage scrolling to selected card once going back), but don't fire event
            History.newItem( Orders.PREFIX + ":" + Orders.getScrollspy( order ), false );
        }
        bus.fireEvent( new EditOrderEvent( order.getId() ) );
    }

    @UiHandler( "activate" )
    public void changeStatus( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        activate.setEnabled( false );
        callChangeStatus( ACTIVE );
    }

    @UiHandler( "pause" )
    public void pause( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        pause.setEnabled( false );
        callChangeStatus( SUSPENDED );
    }

    @UiHandler( "issueInvoice" )
    public void issueInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus.billing().createOrderInvoice( order.getId(), new Invoice(), ( response, failure ) -> {
            if ( failure.isSuccess() )
            {
                bus.fireEvent( new EditInvoiceEvent( response ) );
            }

            success( messages.msgInvoiceIssued(), failure );
        } );
    }

    private void callChangeStatus( Order.Status status )
    {
        OrderStatus os = new OrderStatus();
        os.setStatus( status.name() );

        bus.billing().changeOrderStatus( order.getId(), os, ( response, failure ) -> {
            statusChanged( status );

            activate.setEnabled( true );
            pause.setEnabled( true );

            if ( ACTIVE == status )
            {
                success( messages.msgOrderStatusActive(), failure );
            }
            else if ( SUSPENDED == status )
            {
                warn( messages.msgOrderStatusSuspended(), failure );
            }
        } );
    }

    private Color statusColor( Order.Status status )
    {
        switch ( status )
        {
            case TRIALING:
            {
                return YELLOW;
            }
            case ACTIVE:
            {
                return BLUE;
            }
            case SUSPENDED:
            {
                return RED_LIGHTEN_2;
            }
            case ISSUE:
            {
                return RED_DARKEN_2;
            }
            case FINISHED:
            {
                return GREEN;
            }
        }

        return GREY;
    }

    private String statusText( Order.Status status )
    {
        switch ( status )
        {
            case TRIALING:
            {
                return messages.descriptionOrderStatusTrialing();
            }
            case ACTIVE:
            {
                return messages.descriptionOrderStatusActive();
            }
            case SUSPENDED:
            {
                return messages.descriptionOrderStatusSuspended();
            }
            case ISSUE:
            {
                return messages.descriptionOrderStatusIssue();
            }
            case FINISHED:
            {
                return messages.descriptionOrderStatusFinished();
            }
        }
        String error = "Unknown order status: " + status;
        GWT.log( error );
        throw new IllegalArgumentException( error );
    }

    private IconType statusIconType( Order.Status status )
    {
        switch ( status )
        {
            case TRIALING:
            {
                return IconType.ALARM;
            }
            case ACTIVE:
            {
                return IconType.ALARM_ON;
            }
            case SUSPENDED:
            {
                return IconType.PAUSE_CIRCLE_FILLED;
            }
            case ISSUE:
            {
                return IconType.CLOSE;
            }
            case FINISHED:
            {
                return IconType.CHECK_CIRCLE;
            }
        }
        String error = "Unknown order status: " + status;
        GWT.log( error );
        throw new IllegalArgumentException( error );
    }

    private Color periodicityColor( OrderPeriodicity periodicity )
    {
        switch ( periodicity )
        {
            case ANNUALLY:
                return CYAN;
            case SEMI_ANNUALLY:
                return CYAN_LIGHTEN_1;
            case QUARTERLY:
            case MONTHLY:
            case WEEKLY:
                return CYAN_DARKEN_1;
            case MANUALLY:
                return CYAN_DARKEN_2;
        }
        String error = "Unknown order periodicity: " + periodicity;
        GWT.log( error );
        throw new IllegalArgumentException( error );
    }

    private String periodicityText( OrderPeriodicity periodicity )
    {
        switch ( periodicity )
        {
            case ANNUALLY:
                return messages.labelAnnually();
            case SEMI_ANNUALLY:
                return messages.labelSemiAnnually();
            case QUARTERLY:
                return messages.labelQuarterly();
            case MONTHLY:
                return messages.labelMonthly();
            case WEEKLY:
                return messages.labelWeekly();
            case MANUALLY:
                return messages.labelManually();
        }
        String error = "Unknown order periodicity: " + periodicity;
        GWT.log( error );
        throw new IllegalArgumentException( error );
    }

    interface OrderCardUiBinder
            extends UiBinder<MaterialCard, OrderOverviewCard>
    {
    }
}