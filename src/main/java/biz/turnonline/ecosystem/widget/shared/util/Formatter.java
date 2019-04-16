package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Formatter
{
    public static String formatContactName( Contact contact )
    {
        return contact.getBusinessName() != null && !"".equals( contact.getBusinessName() )
                ? contact.getBusinessName()
                : contact.getFirstName() + " " + contact.getLastName();
    }

    public static String formatProductName( Product product )
    {
        return product.getItemName();
    }

    public static String formatOrderName( Order order )
    {
        return order.getId().toString();
    }

    public static String formatPostcode( String postcode )
    {
        return postcode.length() == 5 ? postcode.substring( 0, 3 ) + " " + postcode.substring( 3 ) : postcode;
    }
}
