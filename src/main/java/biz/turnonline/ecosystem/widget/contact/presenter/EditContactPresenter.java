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

package biz.turnonline.ecosystem.widget.contact.presenter;

import biz.turnonline.ecosystem.widget.contact.event.BackEvent;
import biz.turnonline.ecosystem.widget.contact.event.SaveContactEvent;
import biz.turnonline.ecosystem.widget.contact.place.Contacts;
import biz.turnonline.ecosystem.widget.contact.place.EditContact;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditContactPresenter
        extends Presenter<EditContactPresenter.IView, AppEventBus>
{
    public interface IView
            extends biz.turnonline.ecosystem.widget.shared.view.IView<ContactCard>
    {
    }

    @Inject
    public EditContactPresenter( AppEventBus eventBus,
                                 IView view,
                                 PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new Contacts() ) );

        bus().addHandler( SaveContactEvent.TYPE, event -> {
            ContactCard contactCard = event.getContactCard();
            String loginId = bus().getConfiguration().getLoginId();

            if ( contactCard.getId() == null )
            {
                bus().accountSteward().create( loginId, contactCard, response -> {
                    success( messages.msgRecordCreated() );
                } );
            }
            else
            {
                bus().accountSteward().update( loginId, contactCard.getId(), contactCard, response -> {
                    success( messages.msgRecordUpdated() );
                } );
            }
        } );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newContactCard() );

        EditContact where = ( EditContact ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().accountSteward().findById( bus().getConfiguration().getLoginId(), where.getId(),
                    response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    private ContactCard newContactCard()
    {
        ContactCard contactCard = new ContactCard();
        contactCard.setNumberOfDays( 30 );
        contactCard.setHasPostalAddress( false );

        return contactCard;
    }
}