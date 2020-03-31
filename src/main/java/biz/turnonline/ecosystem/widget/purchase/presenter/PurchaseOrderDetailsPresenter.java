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

import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderInvoicesEvent;
import biz.turnonline.ecosystem.widget.purchase.event.PurchaseOrderListEvent;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoices;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrderDetails;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrders;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * Purchase order detail presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderDetailsPresenter
        extends Presenter<PurchaseOrderDetailsPresenter.IView>
{
    @Inject
    public PurchaseOrderDetailsPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( PurchaseOrderDetails.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( PurchaseOrderListEvent.TYPE, e -> controller().goTo( new PurchaseOrders( e.getScrollspy() ) ) );
        bus().addHandler( PurchaseOrderInvoicesEvent.TYPE, e -> controller().goTo( new IncomingInvoices( e.getOrderId() ) ) );
    }

    @Override
    public void onBackingObject()
    {
        PurchaseOrderDetails where = ( PurchaseOrderDetails ) controller().getWhere();
        if ( where.getId() == null )
        {
            setModel( new PurchaseOrder() );
        }
        else
        {
            bus().billing().getPurchaseOrder( where.getId(), 1, this::setModel );
        }

        onAfterBackingObject();
    }

    private void setModel( PurchaseOrder response )
    {
        PurchaseOrderDetails where = ( PurchaseOrderDetails ) controller().getWhere();
        view().setModel( response );
        Scheduler.get().scheduleDeferred( () -> view().selectTab( where.getTab() ) );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<PurchaseOrder>
    {
        /**
         * Selects the specified tab to be visible to the user.
         *
         * @param tab the name of the tab to be selected
         */
        void selectTab( String tab );
    }
}