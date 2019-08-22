/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollPanel;
import gwt.material.design.incubator.client.infinitescroll.data.DataSource;
import gwt.material.design.incubator.client.infinitescroll.data.LoadCallback;
import gwt.material.design.incubator.client.infinitescroll.data.LoadConfig;
import gwt.material.design.incubator.client.infinitescroll.data.LoadResult;
import gwt.material.design.incubator.client.infinitescroll.recycle.RecycleManager;
import org.ctoolkit.gwt.client.facade.Items;

import javax.annotation.Nullable;
import java.util.List;

/**
 * {@link InfiniteScrollPanel} extension with pre-configured {@link LoadConfig} and other default values.
 * {@link RecycleManager} is not used for now, on Safari scrolling does not work well (unexpectedly cards jumping).
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InfiniteScroll<T>
        extends InfiniteScrollPanel<T>
{
    private static int MAX_TOTAL_LENGTH;

    /**
     * Default constructor with predefined values.
     * <ul>
     *     <li>Initial offset: 0</li>
     *     <li>Limit: 20</li>
     *     <li>Max. total length: 10000</li>
     * </ul>
     */
    public InfiniteScroll()
    {
        this( 0, 20, 10000 );
    }

    /**
     * Constructor
     *
     * @param offset      the initial position of the first result to retrieve
     * @param limit       The maximum number of results to retrieved per single call
     * @param totalLength the maximum number of results all together to be handled
     */
    public InfiniteScroll( int offset, int limit, int totalLength )
    {
        this( new LoadConfig<>( offset, limit ), totalLength );
    }

    /**
     * Constructor
     *
     * @param loadConfig  the initial load configuration (offset, limit)
     * @param totalLength the maximum number of results all together to be handled
     */
    private InfiniteScroll( LoadConfig<T> loadConfig, int totalLength )
    {
        super( null, loadConfig );

        setOverflow( Style.Overflow.AUTO );
        MAX_TOTAL_LENGTH = totalLength;
    }

    /**
     * Sets infinite scroll data source callback.
     *
     * @param callback the client callback impl. to be set
     */
    public void setDataSource( Callback<T> callback )
    {
        super.setDataSource( callback );
    }

    /**
     * Initialized only on first {@link #onLoad()} event in order to avoid firing a duplicated load event
     * That might happen on repeated adding initialized scroll in to a view (for example on history back / forward).
     */
    @Override
    protected void load()
    {
        if ( getChildren().size() == 0 )
        {
            super.load();
        }
    }

    /**
     * Scrolls the specified element into view.
     *
     * @param scrollspy the element id to scroll
     */
    public void scrollTo( @Nullable String scrollspy )
    {
        Element elementById = scrollspy == null ? null : Document.get().getElementById( scrollspy );
        if ( elementById != null )
        {
            elementById.scrollIntoView();
        }
        else
        {
            GWT.log( "Element with id '" + scrollspy + "' not found in DOM" );
        }
    }

    /**
     * Data source interface for infinite scroll.
     *
     * @param <T> the infinite scroll data type
     */
    public interface Callback<T>
            extends DataSource<T>
    {
        @Override
        default void load( LoadConfig<T> config, LoadCallback<T> callback )
        {
            load( config.getOffset(), config.getLimit(), new SuccessCallback<Items<T>>()
            {
                private int totalLength = MAX_TOTAL_LENGTH;

                private int currentLength = 0;

                @Override
                public void onSuccess( Items<T> response )
                {
                    List<T> items = response.getItems();
                    int size = items.size();
                    currentLength = currentLength == 0 ? size : currentLength;

                    if ( size < config.getLimit() )
                    {
                        totalLength = currentLength;
                    }
                    else
                    {
                        currentLength = currentLength + size;
                    }

                    callback.onSuccess( new LoadResult<>( items, config.getOffset(), totalLength ) );
                }
            } );
        }

        void load( int offset, int limit, SuccessCallback<Items<T>> callback );
    }
}
