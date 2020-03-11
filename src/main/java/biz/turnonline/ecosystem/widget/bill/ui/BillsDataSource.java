package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTableLoadResult;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillsDataSource
        extends RefreshableDataSource<Bill> {
    private AppEventBus eventBus;

    public BillsDataSource(AppEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void load(LoadConfig<Bill> loadConfig, LoadCallback<Bill> callback) {
        super.load(loadConfig, callback);

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.bill().getBills(offset,
                limit,
                true,
                response -> callback.onSuccess(new SmartTableLoadResult<>(response.getItems(), loadConfig)));
    }

    @Override
    public boolean useRemoteSort() {
        return true;
    }
}
