package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

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

        eventBus.productBilling().getProducts(
                offset,
                limit,
                true,
                false,
                response -> callback.onSuccess( new LoadResult<>( response.getItems(), offset, response.getItems().size(), false ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
