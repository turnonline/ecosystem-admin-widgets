package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.DueDateNumberOfDaysEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderScheduleChangeEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderStatusChangeEvent;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderStatus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.OrderPeriodicityComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.StaticCodeBook;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.NumberFormat;
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
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.MANUALLY;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.valueOf;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderStatus.ACTIVE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderStatus.FINISHED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderStatus.SUSPENDED;

/**
 * Management all of the single order properties.
 * Fires following events:
 * <ul>
 * <li>{@link OrderScheduleChangeEvent}</li>
 * <li>{@link DueDateNumberOfDaysEvent}</li>
 * </ul>
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class OrderDetail
        extends Composite
        implements HasModel<Order>
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

    private OrderStatus currentStatus;

    private Long orderId;

    private HandlerRegistration activeHandler;

    private HandlerRegistration suspendedHandler;

    @Inject
    public OrderDetail( EventBus eventBus )
    {
        this.bus = eventBus;
        initWidget( binder.createAndBindUi( this ) );

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

        activeHandler = active.addClickHandler( e ->
                bus.fireEvent( new OrderStatusChangeEvent( ACTIVE, orderId ) ) );

        suspendedHandler = suspended.addClickHandler( e ->
                bus.fireEvent( new OrderStatusChangeEvent( SUSPENDED, orderId ) ) );

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

    @Override
    public void bind( Order order )
    {
        order.setBeginOn( beginOn.getValue() );
        order.setNumberOfDays( numberOfDays.getValue() );
        order.setPeriodicity( periodicity.getSingleValueByCode() );
        order.setInvoiceType( invoiceType.getSingleValueByCode() );
        order.setStatus( currentStatus == null ? SUSPENDED.name() : currentStatus.name() );
    }

    @Override
    public void fill( Order order )
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
            currentStatus = order.getStatus() == null ? SUSPENDED : OrderStatus.valueOf( order.getStatus() );
        }
        catch ( IllegalArgumentException e )
        {
            currentStatus = SUSPENDED;
        }

        setStatus( currentStatus );

        readOnly( FINISHED == currentStatus );
        updatePricing( order.getTotalPriceExclVat(), order.getTotalVatBase(), order.getTotalPrice(), order.getItems() );
    }

    /**
     * Sets the current order status, visualized by 4 steps ({@link OrderStatus#TRIALING} is for time being hidden).
     * Following steps has click handlers added in order to give possibility to change.
     * <ul>
     * <li>{@link OrderStatus#ACTIVE}</li>
     * <li>{@link OrderStatus#SUSPENDED}</li>
     * <li>{@link OrderStatus#ISSUE}</li>
     * </ul>
     *
     * @param status the current status to be set
     */
    public void setStatus( @Nonnull OrderStatus status )
    {
        this.currentStatus = checkNotNull( status, "Order status can't be null" );
        suspended.setSuccessText( messages.descriptionOrderStatusSuspend() );
        stepper.reset();

        if ( activeHandler != null || suspendedHandler != null )
        {
            active.removeHandler( activeHandler );
            suspended.removeHandler( suspendedHandler );
        }

        switch ( this.currentStatus )
        {
            case TRIALING:
            {
                break;
            }
            case ACTIVE:
            {
                stepper.nextStep();
                active.setSuccessText( messages.descriptionOrderStatusActive() );

                addActionButtonHandlers();

                break;
            }
            case SUSPENDED:
            case ISSUE:
            {
                stepper.nextStep();
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActivate() );
                suspended.setErrorText( messages.descriptionOrderStatusSuspended() );

                addActionButtonHandlers();

                break;
            }
            case FINISHED:
                stepper.nextStep();
                stepper.nextStep();
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActive() );
                suspended.setSuccessText( messages.descriptionOrderStatusSuspended() );
                finished.setSuccessText( messages.descriptionOrderStatusFinished() );

                break;
        }
    }

    private void addActionButtonHandlers()
    {
        activeHandler = active.addClickHandler( e ->
                bus.fireEvent( new OrderStatusChangeEvent( ACTIVE, orderId ) ) );

        suspendedHandler = suspended.addClickHandler( e ->
                bus.fireEvent( new OrderStatusChangeEvent( SUSPENDED, orderId ) ) );
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
        String price;
        String base;
        String finalPrice;
        String currency = null;

        if ( items != null && !items.isEmpty() )
        {
            currency = items.get( 0 ).getCurrency();
        }

        if ( currency != null )
        {
            if ( totalPriceExclVat != null && totalVatBase != null && totalPrice != null )
            {
                price = NumberFormat.getCurrencyFormat( currency ).format( totalPriceExclVat );
                base = NumberFormat.getCurrencyFormat( currency ).format( totalVatBase );
                finalPrice = NumberFormat.getCurrencyFormat( currency ).format( totalPrice );
            }
            else
            {
                price = NumberFormat.getCurrencyFormat( currency ).format( 0.0 );
                base = NumberFormat.getCurrencyFormat( currency ).format( 0.0 );
                finalPrice = NumberFormat.getCurrencyFormat( currency ).format( 0.0 );
            }
        }
        else
        {
            price = "0";
            base = "0";
            finalPrice = "0";
        }

        priceExclVat.setValue( price );
        vatBase.setValue( base );
        priceInclVat.setValue( finalPrice );
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
