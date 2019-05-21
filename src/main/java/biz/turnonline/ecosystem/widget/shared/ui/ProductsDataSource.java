package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ProductsDataSource
        extends RefreshableDataSource<Product>
{
    private AppEventBus eventBus;

    public ProductsDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<Product> loadConfig, LoadCallback<Product> callback )
    {
        super.load( loadConfig, callback );

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.billing().getProducts(
                offset,
                limit,
                true,
                false,
                response -> callback.onSuccess( new SmartTableLoadResult<>( response.getItems(), loadConfig ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
