package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Formatter
{
    public static String formatContactName( ContactCard contactCard )
    {
        return contactCard.getBusinessName() != null && !"".equals( contactCard.getBusinessName() )
                ? contactCard.getBusinessName()
                : contactCard.getFirstName() + " " + contactCard.getLastName();
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
