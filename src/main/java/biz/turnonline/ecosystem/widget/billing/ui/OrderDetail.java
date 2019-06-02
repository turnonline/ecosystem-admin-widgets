package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeListBox;
import biz.turnonline.ecosystem.widget.shared.ui.OrderPeriodicityListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIntegerBox;

import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class OrderDetail
        extends Composite
        implements HasModel<Order>
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, OrderDetail>
    {
    }

    @UiField
    MaterialDatePicker beginAt;

    @UiField
    MaterialIntegerBox numberOfDays;

    @UiField
    OrderPeriodicityListBox periodicity;

    @UiField
    InvoiceTypeListBox invoiceType;

    @Inject
    public OrderDetail()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    @Override
    public void bind( Order order )
    {
        order.setBeginAt( beginAt.getValue() );
        order.setNumberOfDays( numberOfDays.getValue() );
        order.setPeriodicity( periodicity.getSingleValueByCode() );
        order.setInvoiceType( invoiceType.getSingleValueByCode() );
    }

    @Override
    public void fill( Order order )
    {
        beginAt.setValue( order.getBeginAt() );
        numberOfDays.setValue( order.getNumberOfDays() );
        periodicity.setSingleValueByCode( order.getPeriodicity() );
        invoiceType.setSingleValueByCode( order.getInvoiceType() );
    }
}
