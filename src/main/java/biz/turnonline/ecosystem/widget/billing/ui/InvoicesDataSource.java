package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import gwt.material.design.incubator.client.infinitescroll.data.DataSource;
import gwt.material.design.incubator.client.infinitescroll.data.LoadCallback;
import gwt.material.design.incubator.client.infinitescroll.data.LoadConfig;
import gwt.material.design.incubator.client.infinitescroll.data.LoadResult;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoicesDataSource
        implements DataSource<Invoice>
{
    private AppEventBus bus;

    private int totalLength;

    private int currentLength;

    public InvoicesDataSource( AppEventBus bus )
    {
        this.bus = bus;
        this.totalLength = 10000;
        this.currentLength = 0;
    }

    @Override
    public void load( LoadConfig<Invoice> config, LoadCallback<Invoice> callback )
    {
        bus.billing().getInvoices(
                config.getOffset(),
                config.getLimit(),
                true,
                response -> {
                    List<Invoice> items = response.getItems();
                    int size = items.size();
                    int limit = config.getLimit();
                    if ( size < limit )
                    {
                        totalLength = currentLength;
                    }
                    else
                    {
                        currentLength = currentLength + size;
                    }

                    callback.onSuccess( new LoadResult<>(
                            items,
                            config.getOffset(),
                            totalLength ) );
                } );
    }
}
