package biz.turnonline.ecosystem.widget.shared.ui;

import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SmartTableLoadResult<T>
        extends LoadResult<T>
{
    public SmartTableLoadResult( List<T> data, LoadConfig<T> loadConfig )
    {
        super( data, loadConfig.getOffset(), totalLength( data, loadConfig ), false );
    }

    private static <T> int totalLength( List<T> data, LoadConfig<T> loadConfig )
    {
        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        boolean lastPage = data.size() < limit;
        int size = offset + limit + ( lastPage ? 0 : 1 );
        if ( lastPage )
        {
            size = offset + data.size();
        }

        return size;
    }
}
