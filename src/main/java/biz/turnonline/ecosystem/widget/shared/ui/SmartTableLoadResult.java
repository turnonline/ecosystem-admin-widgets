/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
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
