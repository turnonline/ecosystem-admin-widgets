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

package org.ctoolkit.turnonline.widget.contact.presenter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.turnonline.widget.contact.AppEventBus;
import org.ctoolkit.turnonline.widget.contact.event.DeleteContactEvent;
import org.ctoolkit.turnonline.widget.contact.event.EditContactEvent;
import org.ctoolkit.turnonline.widget.contact.place.EditContact;
import org.ctoolkit.turnonline.widget.shared.presenter.Presenter;
import org.ctoolkit.turnonline.widget.shared.rest.FacadeCallback;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;
import org.ctoolkit.turnonline.widget.shared.util.Formatter;
import org.fusesource.restygwt.client.Method;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ContactsPresenter
        extends Presenter<ContactsPresenter.IView, AppEventBus>
{
    public interface IView
            extends org.ctoolkit.turnonline.widget.shared.view.IView
    {
        void refresh();
    }

    @Inject
    public ContactsPresenter( AppEventBus eventBus,
                              IView view,
                              PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( EditContactEvent.TYPE, event -> {
            controller().goTo( new EditContact( event.getContactCard() != null ? event.getContactCard().getId() : null ) );
        } );

        bus().addHandler( DeleteContactEvent.TYPE, event -> {
            for ( ContactCard contactCard : event.getContactCards() )
            {
                bus().accountSteward().delete( bus().getConfiguration().getLoginId(), contactCard.getId(), new FacadeCallback<Void>()
                {
                    @Override
                    public void onSuccess( Method method, Void response )
                    {
                        super.onSuccess( method, response );

                        success( messages.msgRecordDeleted( Formatter.formatContactName( contactCard ) ) );
                        Scheduler.get().scheduleDeferred( () -> view().refresh() );
                    }
                } );
            }
        } );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();
    }
}