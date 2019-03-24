package org.ctoolkit.turnonline.widget.shared.ui;

import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class RefreshableDataSource<T>
        implements DataSource<T>
{
    private LoadConfig<T> loadConfig;

    private LoadCallback<T> callback;

    @Override
    public void load( LoadConfig<T> loadConfig, LoadCallback<T> callback )
    {
        this.loadConfig = loadConfig;
        this.callback = callback;
    }

    public void refresh()
    {
        load( loadConfig, callback );
    }
}
