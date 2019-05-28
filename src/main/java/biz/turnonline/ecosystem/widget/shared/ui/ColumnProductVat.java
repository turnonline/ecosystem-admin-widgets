package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.VatRate;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ColumnProductVat
        extends WidgetColumn<Product, CodeBookReadOnlyBox<VatRate>>
{
    @Override
    public CodeBookReadOnlyBox<VatRate> getValue( Product object )
    {
        return new CodeBookReadOnlyBox<>( getCode( object ), VatRate.class );
    }

    private String getCode( Product product )
    {
        return product.getPricing() != null && product.getPricing().getVat() != null ? product.getPricing().getVat() : null;
    }
}
