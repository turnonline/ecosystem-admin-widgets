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

import biz.turnonline.ecosystem.widget.billing.event.DueDateNumberOfDaysEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderScheduleChangeEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderStatusChangeEvent;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.OrderPeriodicityComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import biz.turnonline.ecosystem.widget.shared.ui.StaticCodeBook;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.stepper.MaterialStep;
import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.js.Window;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ACTIVE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.COMPLETED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.FINISHED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.SUSPENDED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.MANUALLY;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.valueOf;

/**
 * Management all of the single order properties.
 * Fires following events:
 * <ul>
 * <li>{@link OrderScheduleChangeEvent}</li>
 * <li>{@link DueDateNumberOfDaysEvent}</li>
 * </ul>
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class OrderDetail
        extends Composite
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    private static AppMessages messages = AppMessages.INSTANCE;

    private final EventBus bus;

    @UiField
    MaterialDatePicker beginOn;

    @UiField
    MaterialDatePicker lastBillingDate;

    @UiField
    MaterialDatePicker nextBillingDate;

    @UiField
    MaterialDatePicker dueDate;

    @UiField
    MaterialIntegerBox numberOfDays;

    @UiField
    OrderPeriodicityComboBox periodicity;

    @UiField
    InvoiceTypeComboBox invoiceType;

    @UiField
    MaterialDatePicker created;

    @UiField
    MaterialDatePicker modified;

    @UiField
    MaterialTextBox priceExclVat;

    @UiField
    MaterialTextBox vatBase;

    @UiField
    MaterialTextBox priceInclVat;

    @UiField
    MaterialStepper stepper;

    @UiField
    MaterialPanel stepperPanel;

    @UiField
    MaterialStep trialing;

    @UiField
    MaterialStep active;

    @UiField
    MaterialStep suspended;

    @UiField
    MaterialStep finished;

    private Order.Status currentStatus;

    private Long orderId;

    private HandlerRegistration activeHandler;

    private HandlerRegistration suspendedHandler;

    @Inject
    public OrderDetail( EventBus eventBus )
    {
        this.bus = eventBus;
        initWidget( binder.createAndBindUi( this ) );

        invoiceType.setPaddingBottom( 7 );

        lastBillingDate.setReadOnly( true );
        nextBillingDate.setReadOnly( true );
        dueDate.setReadOnly( true );

        created.setReadOnly( true );
        modified.setReadOnly( true );

        priceExclVat.setReadOnly( true );
        vatBase.setReadOnly( true );
        priceInclVat.setReadOnly( true );

        periodicity.addValueChangeHandler( this::fireOrderPeriodicityChangeEvent );
        beginOn.addValueChangeHandler( this::fireBeginOnChangeEvent );
        numberOfDays.addValueChangeHandler( this::fireNumberOfDaysChangeEvent );

        // trialing for now is not visible, go to next step
        trialing.setVisible( false );
        stepper.nextStep();

        Window.addResizeHandler( resizeEvent -> detectAndApplyOrientation() );
        detectAndApplyOrientation();
    }

    private void detectAndApplyOrientation()
    {
        if ( Window.matchMedia( "(orientation: portrait)" ) )
        {
            if ( trialing.isVisible() )
            {
                stepperPanel.setHeight( "350px" );
            }
            else
            {
                stepperPanel.setHeight( "250px" );
            }
        }
        else
        {
            stepperPanel.setHeight( "70px" );
        }
    }

    public void bind( @Nonnull Order order )
    {
        order.setBeginOn( beginOn.getValue() );
        order.setNumberOfDays( numberOfDays.getValue() );
        order.setPeriodicity( periodicity.getSingleValueByCode() );
        order.setInvoiceType( invoiceType.getSingleValueByCode() );
        order.setStatus( currentStatus == null ? SUSPENDED.name() : currentStatus.name() );
    }

    public void fill( @Nonnull Order order )
    {
        this.orderId = order.getId();

        beginOn.setValue( order.getBeginOn() );
        lastBillingDate.setValue( order.getLastBillingDate() );
        nextBillingDate.setValue( order.getNextBillingDate() );

        String oPeriodicity = order.getPeriodicity() == null ? MANUALLY.name() : order.getPeriodicity();
        String type = order.getInvoiceType() == null ? InvoiceType.PROFORMA.name() : order.getInvoiceType();

        numberOfDays.setValue( order.getNumberOfDays() );
        periodicity.setSingleValueByCode( oPeriodicity );
        invoiceType.setSingleValueByCode( type );

        created.setValue( order.getCreatedDate() );
        modified.setValue( order.getModificationDate() );

        try
        {
            currentStatus = order.getStatus() == null ? SUSPENDED : Order.Status.valueOf( order.getStatus() );
        }
        catch ( IllegalArgumentException e )
        {
            currentStatus = SUSPENDED;
        }

        setStatus( currentStatus );
        updatePricing( order.getTotalPriceExclVat(), order.getTotalVatBase(), order.getTotalPrice(), order.getItems() );
    }

    /**
     * Sets the current order status, visualized by 4 steps ({@link Order.Status#TRIALING} is for time being hidden).
     * Following steps has click handlers added in order to give possibility to change.
     * <ul>
     * <li>{@link Order.Status#ACTIVE}</li>
     * <li>{@link Order.Status#SUSPENDED}</li>
     * <li>{@link Order.Status#ISSUE}</li>
     * </ul>
     *
     * @param status the current status to be set
     */
    public void setStatus( @Nonnull Order.Status status )
    {
        currentStatus = checkNotNull( status, "Order status can't be null" );
        suspended.setSuccessText( messages.descriptionOrderStatusSuspend() );
        stepper.reset();

        readOnly( FINISHED == currentStatus || COMPLETED == currentStatus );

        if ( activeHandler != null )
        {
            active.removeHandler( activeHandler );
        }

        if ( suspendedHandler != null )
        {
            suspended.removeHandler( suspendedHandler );
        }

        switch ( currentStatus )
        {
            case TRIALING:
            {
                break;
            }
            case ISSUE:
            {
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActivate() );
                active.clearStatusText();
                suspended.setErrorText( messages.descriptionOrderStatusIssue() );

                activeHandler = active.addClickHandler( e -> fireOrderStatusChangeEvent( ACTIVE ) );

                break;
            }
            case SUSPENDED:
            {
                // difference comparing to ISSUE status is setErrorText has different description
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActivate() );
                active.clearStatusText();
                suspended.setErrorText( messages.descriptionOrderStatusSuspended() );

                activeHandler = active.addClickHandler( e -> fireOrderStatusChangeEvent( ACTIVE ) );

                break;
            }
            case ACTIVE:
            {
                stepper.nextStep();
                stepper.nextStep();
                active.setSuccessText( messages.descriptionOrderStatusActive() );

                suspendedHandler = suspended.addClickHandler( e -> fireOrderStatusChangeEvent( SUSPENDED ) );

                break;
            }
            case FINISHED:
            case COMPLETED:
                stepper.nextStep();
                stepper.nextStep();
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActive() );
                suspended.setSuccessText( messages.descriptionOrderStatusSuspended() );
                if ( currentStatus == FINISHED )
                {
                    finished.setSuccessText( messages.descriptionOrderStatusFinished() );
                    finished.setTitle( FINISHED.name() );
                }
                else
                {
                    finished.setSuccessText( messages.descriptionOrderStatusCompleted() );
                    finished.setTitle( COMPLETED.name() );
                }

                break;
        }
    }

    private void readOnly( boolean all )
    {
        setBeginOnReadOnly( all );
        numberOfDays.setReadOnly( all );
        invoiceType.setReadOnly( all );
        periodicity.setReadOnly( all );
    }

    /**
     * Updates total price details.
     */
    public void updatePricing( @Nullable Double totalPriceExclVat,
                               @Nullable Double totalVatBase,
                               @Nullable Double totalPrice,
                               @Nullable List<PricingItem> items )
    {
        PricingItemsPanel.updatePricing(
                totalPriceExclVat,
                totalVatBase,
                totalPrice,
                items,
                priceExclVat, vatBase, priceInclVat );
    }

    private void fireOrderPeriodicityChangeEvent( ValueChangeEvent<List<StaticCodeBook>> event )
    {
        bus.fireEvent( new OrderScheduleChangeEvent(
                valueOf( event.getValue().get( 0 ).getCode() ),
                beginOn.getValue(),
                lastBillingDate.getValue(),
                numberOfDays.getValue() ) );
    }

    private void fireBeginOnChangeEvent( ValueChangeEvent<Date> event )
    {
        bus.fireEvent( new OrderScheduleChangeEvent(
                valueOf( periodicity.getSingleValueByCode() ),
                event.getValue(),
                lastBillingDate.getValue(),
                numberOfDays.getValue() ) );
    }

    private void fireNumberOfDaysChangeEvent( ValueChangeEvent<Integer> event )
    {
        bus.fireEvent( new DueDateNumberOfDaysEvent( nextBillingDate.getValue(), event.getValue() ) );
    }

    private void fireOrderStatusChangeEvent( @Nonnull Order.Status status )
    {
        if ( orderId == null )
        {
            // for this case component local status handling is sufficient
            setStatus( status );
        }
        else
        {
            bus.fireEvent( new OrderStatusChangeEvent( status, orderId ) );
        }
    }

    /**
     * Sets the order's Begin on date whether it's allowed to be edited by user or not.
     *
     * @param readOnly true to be read only
     */
    public void setBeginOnReadOnly( boolean readOnly )
    {
        beginOn.setReadOnly( readOnly );
    }

    /**
     * Sets the order's Next billing date, evaluated based on the current periodicity.
     *
     * @param next the next billing date to be set
     */
    public void setNextBillingDate( @Nonnull Date next )
    {
        nextBillingDate.setValue( next );
    }

    /**
     * Sets order's due date (a date placed at invoice once issued).
     * Evaluated based on the due date number of days.
     *
     * @param date the due date to be set
     */
    public void setDueDate( @Nullable Date date )
    {
        dueDate.setValue( date );
    }

    /**
     * Sets the number of days to calculate due date.
     *
     * @param days the number of days to be set
     */
    public void setNumberOfDays( Integer days )
    {
        numberOfDays.setValue( days );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, OrderDetail>
    {
    }
}
