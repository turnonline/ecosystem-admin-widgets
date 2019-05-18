package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.CodeBookRestFacade;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.VatRate;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnProductVat
        extends NotSafeHtmlColumn<Product>
{
    @Override
    public String getValue( Product object )
    {
        StringBuilder sb = new StringBuilder();
        if ( object.getPricing() != null && object.getPricing().getVat() != null )
        {
            VatRate vatRate = CodeBookRestFacade.getCodeBookValue( VatRate.class, object.getPricing().getVat() );
            if (vatRate != null)
            {
                sb.append( vatRate.getLabel() );
            }
        }

        return sb.toString();
    }
}
