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

import biz.turnonline.ecosystem.widget.purchase.event.IncomingInvoiceListEvent;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoiceDetails;
import biz.turnonline.ecosystem.widget.purchase.place.IncomingInvoices;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Incoming invoice details presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceDetailsPresenter
        extends Presenter<IncomingInvoiceDetailsPresenter.IView>
{
    @Inject
    public IncomingInvoiceDetailsPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( IncomingInvoiceDetails.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( IncomingInvoiceListEvent.TYPE, this::goToList );
        bus().addHandler( RecalculatedPricingEvent.TYPE, this::recalculated );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( new IncomingInvoice() );

        IncomingInvoiceDetails where = ( IncomingInvoiceDetails ) controller().getWhere();
        if ( where.getInvoiceId() != null )
        {
            bus().billing().getIncomingOrderInvoice( where.getOrderId(), where.getInvoiceId(), this::setModel );
        }

        onAfterBackingObject();
    }

    private void goToList( IncomingInvoiceListEvent event )
    {
        controller().goTo( new IncomingInvoices( event.getScrollspy() ) );
    }

    private void recalculated( RecalculatedPricingEvent event )
    {
        view().update( event.getPricing() );
    }

    private void setModel( IncomingInvoice response )
    {
        IncomingInvoiceDetails where = ( IncomingInvoiceDetails ) controller().getWhere();
        view().setModel( response );
        Scheduler.get().scheduleDeferred( () -> view().selectTab( where.getTab() ) );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<IncomingInvoice>
    {
        /**
         * Selects the specified tab to be visible to the user.
         *
         * @param tab the name of the tab to be selected
         */
        void selectTab( String tab );

        /**
         * Updates the order's pricing (details and items) UI by recalculated price.
         *
         * @param pricing the recalculated price
         */
        void update( @Nonnull Pricing pricing );
    }
}