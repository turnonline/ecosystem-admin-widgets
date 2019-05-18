package biz.turnonline.ecosystem.widget.invoice.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoicesDataSource
        extends RefreshableDataSource<Invoice>
{
    private AppEventBus eventBus;

    public InvoicesDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<Invoice> loadConfig, LoadCallback<Invoice> callback )
    {
        super.load( loadConfig, callback );

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.billing().getInvoices(
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
