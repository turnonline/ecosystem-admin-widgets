package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
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
}
