package biz.turnonline.ecosystem.widget.shared.rest.billing;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface HasCustomer
{
    Customer getCustomer();

    <T> T setCustomer(Customer customer);
}
