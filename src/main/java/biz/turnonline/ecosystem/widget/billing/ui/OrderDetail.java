package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;
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
import com.google.gwt.user.datepicker.client.CalendarUtil;
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
    public OrderDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        lastBillingDate.setReadOnly( true );
        nextBillingDate.setReadOnly( true );
        dueDate.setReadOnly( true );

        created.setReadOnly( true );
        modified.setReadOnly( true );

        priceExclVat.setReadOnly( true );
        vatBase.setReadOnly( true );
        priceInclVat.setReadOnly( true );

        periodicity.addValueChangeHandler( this::adjustNextBillingDate );
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

        // init as last
        try
        {
            adjustNextBillingDate( valueOf( oPeriodicity ) );
        }
        catch ( IllegalArgumentException e )
        {
            adjustNextBillingDate( MANUALLY );
        }
    }

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

    private void adjustNextBillingDate( ValueChangeEvent<StaticCodeBook> event )
    {
        adjustNextBillingDate( valueOf( event.getValue().getCode() ) );
    }

    private void adjustNextBillingDate( OrderPeriodicity value )
    {
        beginOn.setVisible( value != MANUALLY );

        Date nextDate;
        Date last = lastBillingDate.getValue();
        if ( last == null )
        {
            last = beginOn.getValue();
        }

        switch ( value )
        {
            case MANUALLY:
            {
                // show date that will be assigned when an invoice would be issued
                nextDate = new Date();
                break;
            }
            case MONTHLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 1 );
                nextDate = last;
                break;
            }
            case QUARTERLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 3 );
                nextDate = last;
                break;
            }
            case ANNUALLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 12 );
                nextDate = last;
                break;
            }
            case SEMI_ANNUALLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 6 );
                nextDate = last;
                break;
            }
            case WEEKLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addDaysToDate( last, 7 );
                nextDate = last;
                break;
            }
            default:
            {
                nextDate = null;
                break;
            }
        }

        nextBillingDate.setValue( nextDate );

        // + adjust due date
        Integer days = numberOfDays.getValue();

        if ( days != null )
        {
            Date due = new Date( nextDate.getTime() );
            CalendarUtil.addDaysToDate( due, days );

            dueDate.setValue( due );
        }
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, OrderDetail>
    {
    }
}
