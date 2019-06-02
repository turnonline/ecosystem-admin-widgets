package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTableLoadResult;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

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
                response -> callback.onSuccess( new SmartTableLoadResult<>( response.getItems(), loadConfig ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
