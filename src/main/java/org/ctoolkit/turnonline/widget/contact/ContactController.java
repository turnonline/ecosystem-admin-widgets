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

package org.ctoolkit.turnonline.widget.contact;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import org.ctoolkit.turnonline.widget.contact.place.Contacts;
import org.ctoolkit.turnonline.widget.contact.place.EditContact;
import org.ctoolkit.turnonline.widget.contact.presenter.ContactsPresenter;
import org.ctoolkit.turnonline.widget.contact.presenter.EditContactPresenter;
import org.ctoolkit.turnonline.widget.shared.presenter.Presenter;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ContactController
        implements ActivityMapper
{
    private static HashMap<String, Presenter> presenters = null;

    @Inject
    public ContactController( ContactsPresenter contactsPresenter,
                              EditContactPresenter editContactPresenter )
    {
        if ( presenters == null )
        {
            presenters = new HashMap<>();

            presenters.put( Contacts.class.getName(), contactsPresenter );
            presenters.put( EditContact.class.getName(), editContactPresenter );
        }
    }

    @Override
    public Activity getActivity( final Place place )
    {
        Presenter presenter = presenters.get( place.getClass().getName() );
        if ( presenter == null )
        {
            presenter = presenters.get( ContactEntryPoint.DEFAULT_PLACE.getClass().getName() );
        }

        return presenter;
    }
}
