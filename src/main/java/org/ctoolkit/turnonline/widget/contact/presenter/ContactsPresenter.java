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

import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.turnonline.widget.shared.presenter.Presenter;
import org.ctoolkit.turnonline.widget.contact.AppEventBus;

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
    }

    @Inject
    public ContactsPresenter( AppEventBus eventBus, IView view, PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    public void bind()
    {

    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();
    }
}