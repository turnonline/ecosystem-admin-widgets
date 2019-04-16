package biz.turnonline.ecosystem.widget.order.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Order;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class OrdersDataSource
        extends RefreshableDataSource<Order>
{
    private AppEventBus eventBus;

    public OrdersDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<Order> loadConfig, LoadCallback<Order> callback )
    {
        super.load( loadConfig, callback );

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.productBilling().getOrders(
                offset,
                limit,
                true,
                response -> callback.onSuccess( new LoadResult<>( response.getItems(), offset, response.getItems().size(), false ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
