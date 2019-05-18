package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.CodeBookRestFacade;
import biz.turnonline.ecosystem.widget.shared.rest.account.Country;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.HasCustomer;

import static biz.turnonline.ecosystem.widget.shared.util.Formatter.formatPostcode;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnCustomer<T extends HasCustomer>
        extends NotSafeHtmlColumn<T>
{
    @Override
    public String getValue( T object )
    {
        Customer customer = object.getCustomer();
        StringBuilder sb = new StringBuilder();

        sb.append( "<div style='padding: 10px 0;'>" );
        if ( customer != null )
        {
            if ( !"".equals( customer.getBusinessName() ) )
            {
                sb.append( "<b>" );
                sb.append( customer.getBusinessName() );
                sb.append( "</b>" );

                sb.append( " " );
                sb.append( "<span class='white-text badge green' style='position:relative;right:0;top:-1px;font-size:0.9em;border-radius:3px;'>" );
                sb.append( customer.getCompanyId() );
                sb.append( "</span>" );

                sb.append( "<br/>" );
            }

            if ( customer.getFirstName() != null || customer.getLastName() != null )
            {
                if ( customer.getPrefix() != null )
                {
                    sb.append( customer.getPrefix() );
                    sb.append( " " );
                }
                sb.append( customer.getFirstName() );
                sb.append( " " );
                sb.append( customer.getLastName() );

                if ( customer.getSuffix() != null )
                {
                    sb.append( " " );
                    sb.append( customer.getSuffix() );
                }

                sb.append( "<br/>" );
            }


            if ( customer.getStreet() != null )
            {
                sb.append( customer.getStreet() );
                sb.append( "<br/>" );
            }

            if ( customer.getCity() != null )
            {
                if ( customer.getPostcode() != null )
                {
                    sb.append( formatPostcode( customer.getPostcode() ) );
                    sb.append( " " );
                }

                sb.append( customer.getCity() );
                sb.append( "<br/>" );
            }

            if ( customer.getCountry() != null )
            {
                Country country = CodeBookRestFacade.getCodeBookValue( Country.class, customer.getCountry() );

                sb.append( "<i class='grey-text'>" );
                sb.append( country != null ? country.getLabel() : customer.getCountry() );
                sb.append( "</i>" );
            }
        }

        sb.append( "</div>" );
        return sb.toString();
    }
}
