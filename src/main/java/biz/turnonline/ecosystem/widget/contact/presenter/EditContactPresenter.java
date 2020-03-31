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

package biz.turnonline.ecosystem.widget.contact.presenter;

import biz.turnonline.ecosystem.widget.contact.event.BackEvent;
import biz.turnonline.ecosystem.widget.contact.event.DeleteContactEvent;
import biz.turnonline.ecosystem.widget.contact.event.SaveContactEvent;
import biz.turnonline.ecosystem.widget.contact.place.Contacts;
import biz.turnonline.ecosystem.widget.contact.place.EditContact;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditContactPresenter
        extends Presenter<EditContactPresenter.IView>
{
    @Inject
    public EditContactPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new Contacts() ) );
        bus().addHandler( SaveContactEvent.TYPE, this::save );
        bus().addHandler( DeleteContactEvent.TYPE, this::delete );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newContactCard() );

        EditContact where = ( EditContact ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().account().findById( bus().config().getLoginId(), where.getId(),
                    ( SuccessCallback<ContactCard> ) response -> view().setModel( response ) );
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

    private void save( SaveContactEvent event )
    {
        ContactCard contactCard = event.getContactCard();
        String loginId = bus().config().getLoginId();

        if ( contactCard.getId() == null )
        {
            bus().account().create( loginId, contactCard, ( SuccessCallback<ContactCard> ) response -> {
                success( messages.msgRecordCreated() );
                controller().goTo( new EditContact( response.getId() ) );
            } );
        }
        else
        {
            bus().account().update( loginId, contactCard.getId(), contactCard,
                    ( SuccessCallback<ContactCard> ) response -> success( messages.msgRecordUpdated() ) );
        }
    }

    private void delete( DeleteContactEvent event )
    {
        ContactCard contactCard = event.getContactCard();

        bus().account().delete( bus().config().getLoginId(), contactCard.getId(),
                ( SuccessCallback<Void> ) response -> {
                    success( messages.msgRecordDeleted( Formatter.formatContactName( contactCard ) ) );
                    controller().goTo( new Contacts() );
                } );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<ContactCard>
    {
    }
}