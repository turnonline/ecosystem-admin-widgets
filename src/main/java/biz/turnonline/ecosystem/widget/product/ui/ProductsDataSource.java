package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.product.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;
import org.ctoolkit.gwt.client.facade.Items;
import org.fusesource.restygwt.client.Method;

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
                new FacadeCallback<Items<Product>>()
                {
                    @Override
                    public void onSuccess( Method method, Items<Product> response )
                    {
                        super.onSuccess( method, response );
                        callback.onSuccess( new LoadResult<>( response.getItems(), offset, response.getItems().size(), false ) );
                    }
                } );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
