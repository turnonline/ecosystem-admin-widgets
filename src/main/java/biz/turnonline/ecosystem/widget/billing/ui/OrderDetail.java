package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.DueDateNumberOfDaysEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderScheduleChangeEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeListBox;
import biz.turnonline.ecosystem.widget.shared.ui.OrderPeriodicityListBox;
import biz.turnonline.ecosystem.widget.shared.ui.StaticCodeBook;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.MANUALLY;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.valueOf;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class OrderDetail
        extends Composite
        implements HasModel<Order>
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

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
    OrderPeriodicityListBox periodicity;

    @UiField
    InvoiceTypeListBox invoiceType;

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
    }

    @Override
    public void bind( Order order )
    {
        order.setBeginAt( beginOn.getValue() );
        order.setNumberOfDays( numberOfDays.getValue() );
        order.setPeriodicity( periodicity.getSingleValueByCode() );
        order.setInvoiceType( invoiceType.getSingleValueByCode() );
    }

    @Override
    public void fill( Order order )
    {
        beginOn.setValue( order.getBeginAt() );
        lastBillingDate.setValue( order.getLastBillingDate() );
        nextBillingDate.setValue( order.getNextBillingDate() );

        String oPeriodicity = order.getPeriodicity() == null ? MANUALLY.name() : order.getPeriodicity();

        numberOfDays.setValue( order.getNumberOfDays() );
        periodicity.setSingleValueByCode( oPeriodicity );
        invoiceType.setSingleValueByCode( order.getInvoiceType() );

        created.setValue( null );
        modified.setValue( order.getModificationDate() );

        updatePricing( order.getTotalPriceExclVat(), order.getTotalVatBase(), order.getTotalPrice(), order.getItems() );
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

    private void fireOrderPeriodicityChangeEvent( ValueChangeEvent<StaticCodeBook> event )
    {
        bus.fireEvent( new OrderScheduleChangeEvent(
                valueOf( event.getValue().getCode() ),
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
    public void setNextBillingDate( Date next )
    {
        nextBillingDate.setValue( next );
    }

    /**
     * Sets order's due date (a date placed at invoice once issued).
     * Evaluated based on the due date number of days.
     *
     * @param date the due date to be set
     */
    public void setDueDate( Date date )
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
