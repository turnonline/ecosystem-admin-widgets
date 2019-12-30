/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.BackEvent;
import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrderDetail;
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
        setPlace( PurchaseOrderDetail.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new PurchaseOrders() ) );
    }

    @Override
    public void onBackingObject()
    {
        PurchaseOrderDetail where = ( PurchaseOrderDetail ) controller().getWhere();
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
        PurchaseOrderDetail where = ( PurchaseOrderDetail ) controller().getWhere();
        view().setModel( response );
        Scheduler.get().scheduleDeferred( () -> view().selectTab( where.getTab() ) );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<PurchaseOrder>
    {
        void selectTab( String tab );
    }
}