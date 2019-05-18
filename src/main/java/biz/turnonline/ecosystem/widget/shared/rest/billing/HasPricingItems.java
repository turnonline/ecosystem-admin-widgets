package biz.turnonline.ecosystem.widget.shared.rest.billing;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface HasPricingItems
{
    List<PricingItem> getItems();

    void setItems( List<PricingItem> items );
}
