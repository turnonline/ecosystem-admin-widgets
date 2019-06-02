package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTableLoadResult;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

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

        eventBus.billing().getOrders(
                offset,
                limit,
                true,
                response -> callback.onSuccess( new SmartTableLoadResult<>( response.getItems(), loadConfig ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
