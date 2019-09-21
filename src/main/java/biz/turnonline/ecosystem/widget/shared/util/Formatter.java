package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import com.google.gwt.i18n.client.NumberFormat;

import javax.annotation.Nonnull;

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

    public static String formatPrice( String currency, Double price )
    {
        if ( currency == null )
        {
            return price != null ? price.toString() : "0";
        }

        return price != null ? formatPriceInternal( currency, price ) : formatPriceInternal( currency, 0D );
    }

    private static String formatPriceInternal( @Nonnull String currency, @Nonnull Double number )
    {
        return NumberFormat.getCurrencyFormat( currency ).format( number );
    }
}
