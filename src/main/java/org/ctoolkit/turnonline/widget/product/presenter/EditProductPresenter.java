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

package org.ctoolkit.turnonline.widget.product.presenter;

import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.turnonline.widget.product.AppEventBus;
import org.ctoolkit.turnonline.widget.product.event.BackEvent;
import org.ctoolkit.turnonline.widget.product.place.Products;
import org.ctoolkit.turnonline.widget.shared.presenter.Presenter;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditProductPresenter
        extends Presenter<EditProductPresenter.IView, AppEventBus>
{
    public interface IView
            extends org.ctoolkit.turnonline.widget.shared.view.IView<Product>
    {
    }

    @Inject
    public EditProductPresenter( AppEventBus eventBus,
                                 IView view,
                                 PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackEvent.TYPE, event -> controller().goTo( new Products() ) );

//        bus().addHandler( SaveContactEvent.TYPE, new SaveContactEventHandler()
//        {
//            @Override
//            public void onSaveContact( SaveContactEvent event )
//            {
//                ContactCard contactCard = event.getContactCard();
//                String loginId = bus().getConfiguration().getLoginId();
//
//                if ( contactCard.getId() == null )
//                {
//                    bus().accountSteward().create( loginId, contactCard, new FacadeCallback<ContactCard>()
//                    {
//                        @Override
//                        public void onSuccess( Method method, ContactCard response )
//                        {
//                            super.onSuccess( method, response );
//                            success( messages.msgRecordCreated() );
//
//                            controller().goTo( new Contacts() );
//                        }
//                    } );
//                }
//                else
//                {
//                    bus().accountSteward().update( loginId, contactCard.getId(), contactCard, new FacadeCallback<ContactCard>()
//                    {
//                        @Override
//                        public void onSuccess( Method method, ContactCard response )
//                        {
//                            super.onSuccess( method, response );
//                            success( messages.msgRecordUpdated() );
//
//                            controller().goTo( new Contacts() );
//                        }
//                    } );
//                }
//            }
//        } );
    }

    @Override
    public void onBackingObject()
    {
//        view().setModel( newContactCard() );
//
//        EditContact where = ( EditContact ) controller().getWhere();
//        if ( where.getId() != null )
//        {
//            bus().accountSteward().findById( bus().getConfiguration().getLoginId(), where.getId(), new FacadeCallback<ContactCard>()
//            {
//                @Override
//                public void onSuccess( Method method, ContactCard response )
//                {
//                    super.onSuccess( method, response );
//                    view().setModel( response );
//                }
//            } );
//        }

        onAfterBackingObject();
    }

//    private ContactCard newContactCard()
//    {
//        ContactCard contactCard = new ContactCard();
//        contactCard.setNumberOfDays( 30 );
//        contactCard.setHasPostalAddress( false );
//
//        return contactCard;
//    }
}