package biz.turnonline.ecosystem.widget.shared.event;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Customer;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CustomerSelectEvent
        extends GwtEvent<CustomerSelectEventHandler>
{
    public static Type<CustomerSelectEventHandler> TYPE = new Type<CustomerSelectEventHandler>();

    private Customer customer;

    public Type<CustomerSelectEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( CustomerSelectEventHandler handler )
    {
        handler.onCustomerSelect( this );
    }

    public CustomerSelectEvent( Customer customer )
    {
        this.customer = customer;
    }

    public Customer getCustomer()
    {
        return customer;
    }
}
