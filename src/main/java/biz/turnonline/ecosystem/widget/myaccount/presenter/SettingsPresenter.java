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

package biz.turnonline.ecosystem.widget.myaccount.presenter;

import biz.turnonline.ecosystem.widget.myaccount.event.CreateDomainEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.DomainDeleteEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.SaveInvoicingEvent;
import biz.turnonline.ecosystem.widget.myaccount.place.Settings;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.account.Domain;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfig;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;
import java.util.List;

/**
 * Settings presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class SettingsPresenter
        extends Presenter<SettingsPresenter.IView, AppEventBus>
{
    @Inject
    public SettingsPresenter( AppEventBus eventBus,
                              IView view,
                              PlaceController controller )
    {
        super( eventBus, view, controller );
        setPlace( Settings.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( SaveInvoicingEvent.TYPE,
                event -> bus()
                        .account()
                        .update( bus().config().getLoginId(), event.getInvoicing(),
                                ( response, failure ) -> message( messages.msgRecordUpdated(), failure ) ) );

        bus().addHandler( CreateDomainEvent.TYPE,
                event -> bus()
                        .account()
                        .create( bus().config().getLoginId(), event.getDomain(), this::domainCreated ) );

        bus().addHandler( DomainDeleteEvent.TYPE,
                event -> {
                    for ( String name : event.getDomainNames() )
                    {
                        bus().account()
                                .delete( bus().config().getLoginId(), name,
                                        ( response, failure ) -> domainDeleted( failure, name ) );
                    }
                } );
    }

    @Override
    public void onBackingObject()
    {
        bus().account().getInvoicingConfig( bus().config().getLoginId(), this::updateInvoicing );
        loadDomains();

        onAfterBackingObject();
    }

    /**
     * Retrieves domains from the remote server with specified limit of records and sets result into view.
     */
    private void loadDomains()
    {
        bus().account().getDomains( bus().config().getLoginId(), 1000, r -> view().setDomains( r.getItems() ) );
    }

    private void domainCreated( Domain domain, FacadeCallback.Failure failure )
    {
        message( messages.msgRecordUpdated(), failure );
        if ( failure.isSuccess() )
        {
            loadDomains();
        }
    }

    private void domainDeleted( FacadeCallback.Failure failure, String name )
    {
        message( messages.msgRecordDeleted( name ), failure );
        if ( failure.isSuccess() )
        {
            loadDomains();
        }
    }

    private void updateInvoicing( InvoicingConfig invoicing, FacadeCallback.Failure failure )
    {
        if ( failure.isFailure() )
        {
            view().setModel( new InvoicingConfig() );

            if ( !failure.isNotFound() )
            {
                error( messages.msgErrorRemoteServiceCall() );
            }
        }
        else
        {
            view().setModel( invoicing );
        }
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<InvoicingConfig>
    {
        void setDomains( List<Domain> data );
    }
}