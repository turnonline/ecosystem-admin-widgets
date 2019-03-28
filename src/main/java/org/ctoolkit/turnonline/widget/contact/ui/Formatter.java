package org.ctoolkit.turnonline.widget.contact.ui;

import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;

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
}
