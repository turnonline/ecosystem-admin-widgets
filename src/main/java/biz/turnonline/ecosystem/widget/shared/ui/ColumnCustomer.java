package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.account.Country;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.HasCustomer;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import static biz.turnonline.ecosystem.widget.shared.util.Formatter.formatPostcode;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnCustomer<T extends HasCustomer>
        extends WidgetColumn<T, FlowPanel>
{
    @Override
    public FlowPanel getValue( T object )
    {
        Customer customer = object.getCustomer();

        FlowPanel cell = new FlowPanel();
        cell.getElement().getStyle().setPaddingTop( 10, Style.Unit.PX );
        cell.getElement().getStyle().setPaddingBottom( 10, Style.Unit.PX );

        if ( customer == null )
        {
            return cell;
        }

        if ( !"".equals( customer.getBusinessName() ) )
        {
            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );

            InlineLabel label = new InlineLabel( customer.getBusinessName() );
            label.getElement().getStyle().setFontWeight( Style.FontWeight.BOLD );
            cellItem.add( label );

            ColumnBadge companyId = new ColumnBadge( customer.getCompanyId(), Color.WHITE, Color.GREEN );
            companyId.setMarginLeft( 5 );
            cellItem.add( companyId );
        }

        if ( customer.getFirstName() != null || customer.getLastName() != null )
        {
            StringBuilder sb = new StringBuilder();
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

            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );
            cellItem.add( new InlineLabel( sb.toString() ) );
        }

        if ( customer.getStreet() != null )
        {
            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );

            cellItem.add( new InlineLabel( customer.getStreet() ) );
        }

        if ( customer.getCity() != null )
        {
            StringBuilder sb = new StringBuilder();
            if ( customer.getPostcode() != null )
            {
                sb.append( formatPostcode( customer.getPostcode() ) );
                sb.append( " " );
            }

            sb.append( customer.getCity() );

            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );
            cellItem.add( new InlineLabel( sb.toString() ) );
        }

        if ( customer.getCountry() != null )
        {
            CodeBookReadOnlyBox<Country> country = new CodeBookReadOnlyBox<>( customer.getCountry(), Country.class );
            country.getElement().addClassName( "grey-text" );

            FlowPanel cellItem = new FlowPanel();
            cell.add( cellItem );
            cellItem.add( country );
        }

        return cell;
    }
}
