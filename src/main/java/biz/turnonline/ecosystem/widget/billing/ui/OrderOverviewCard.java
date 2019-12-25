/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.EditOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderStatus;
import biz.turnonline.ecosystem.widget.shared.ui.PriceLabel;
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
import static gwt.material.design.client.constants.Color.CYAN_LIGHTEN_2;
import static gwt.material.design.client.constants.Color.CYAN_LIGHTEN_3;
import static gwt.material.design.client.constants.Color.CYAN_LIGHTEN_4;
import static gwt.material.design.client.constants.Color.CYAN_LIGHTEN_5;
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
            }
            else
            {
                name = customer.getFirstName() + " " + customer.getLastName();
            }
        }
        title.setText( name );

        // order periodicity
        OrderPeriodicity periodicityEnum = order.getPeriodicity() == null
                ? MANUALLY : OrderPeriodicity.valueOf( order.getPeriodicity() );

        periodicity.setBackgroundColor( periodicityColor( periodicityEnum ) );
        periodicity.setText( periodicityText( periodicityEnum ) );

        // total price at order of current items
        String currency = order.getCurrency() == null ? bus.config().getCurrency() : order.getCurrency();
        Double priceExclVat = order.getTotalPriceExclVat();
        Double totalPrice = order.getTotalPrice();

        totalPriceExclVat.setValue( priceExclVat, currency );
        this.totalPrice.setValue( totalPrice, currency );

        // billing dates
        lastBillingDate.getPlaceholderLabel().setFontSize( totalPriceExclVat.getFontSize() );
        lastBillingDate.setReadOnly( true );
        lastBillingDate.setValue( order.getLastBillingDate() );
        lastBillingDate.setVisible( order.getLastBillingDate() != null );

        nextBillingDate.getPlaceholderLabel().setFontSize( totalPriceExclVat.getFontSize() );
        nextBillingDate.setReadOnly( true );
        nextBillingDate.setValue( order.getNextBillingDate() );

        // order status
        Order.Status status = order.getStatus() == null ? SUSPENDED : Order.Status.valueOf( order.getStatus() );
        nextBillingDate.setVisible( ( status == TRIALING || status == ACTIVE ) && order.getNextBillingDate() != null );

        statusChanged( status );
    }

    private void statusChanged( Order.Status status )
    {
        orderStatus.setBackgroundColor( statusColor( status ) );
        orderStatus.setText( statusText( status ) );
        orderStatus.setIconType( statusIconType( status ) );

        // action buttons
        activate.setVisible( status == SUSPENDED || status == ISSUE );
        pause.setVisible( status == ACTIVE );
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
                return CYAN_LIGHTEN_2;
            case SEMI_ANNUALLY:
                return CYAN_LIGHTEN_3;
            case QUARTERLY:
            case MONTHLY:
            case WEEKLY:
                return CYAN_LIGHTEN_4;
            case MANUALLY:
                return CYAN_LIGHTEN_5;
        }
        String error = "Unknown order status: " + periodicity;
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