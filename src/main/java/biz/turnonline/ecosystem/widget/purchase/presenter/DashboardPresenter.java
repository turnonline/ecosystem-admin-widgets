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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.DashboardRangeChangeEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Dashboard;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.dashboard.DashboardStatistics;
import biz.turnonline.ecosystem.widget.shared.ui.PredefinedRange;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class DashboardPresenter
        extends Presenter<DashboardPresenter.IView>
{
    private DateTimeFormat formatter = DateTimeFormat.getFormat( "yyyy-MM-dd" );

    @Inject
    public DashboardPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( Dashboard.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( DashboardRangeChangeEvent.TYPE, event -> loadTransactions( event.getPredefinedRange() ) );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();
        loadTransactions( PredefinedRange.CURRENT_YEAR );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<DashboardStatistics>
    {
        void setPredefinedRange( PredefinedRange range);
    }

    private void loadTransactions( PredefinedRange predefinedRange )
    {
        PredefinedRange.Range range = predefinedRange.getRangeSupplier().get();

        bus().paymentProcessor().getTransactions(
                0,
                1000,
                null,
                null,
                formatter.format( range.getFrom() ),
                formatter.format( range.getTo() ),
                response -> {
                    DashboardStatistics dashboardStatistics = new DashboardStatistics( response.getItems() );
                    view().setModel( dashboardStatistics );
                    view().setPredefinedRange( predefinedRange );
                }
        );
    }
}