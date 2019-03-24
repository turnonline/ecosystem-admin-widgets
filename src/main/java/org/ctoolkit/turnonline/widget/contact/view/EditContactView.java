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

package org.ctoolkit.turnonline.widget.contact.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import org.ctoolkit.turnonline.widget.contact.event.BackEvent;
import org.ctoolkit.turnonline.widget.contact.presenter.EditContactPresenter;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;
import org.ctoolkit.turnonline.widget.shared.view.View;

import javax.inject.Inject;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditContactView
        extends View<ContactCard>
        implements EditContactPresenter.IView
{
    private static EditContactsViewUiBinder binder = GWT.create( EditContactsViewUiBinder.class );

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialSwitch company;

    // basic info

    @UiField
    MaterialTextBox businessName;

    @UiField
    MaterialTextBox prefix;

    @UiField
    MaterialTextBox firstName;

    @UiField
    MaterialTextBox lastName;

    @UiField
    MaterialTextBox suffix;

    // contacts

    @UiField
    MaterialTextBox phone;

    @UiField
    MaterialTextBox email;

    @UiField
    MaterialTextBox ccEmail;

    interface EditContactsViewUiBinder
            extends UiBinder<HTMLPanel, EditContactView>
    {
    }

    @Inject
    public EditContactView( EventBus eventBus )
    {
        super( eventBus );

        add( binder.createAndBindUi( this ) );
    }

    @Override
    protected void bind()
    {
        // TODO: implement
    }

    @Override
    protected void fill()
    {
        // TODO: implement
        ContactCard contact = getRawModel();

        company.setValue( contact.getCompany() );
        businessName.setValue( contact.getBusinessName() );

        prefix.setValue( contact.getPrefix() );
        firstName.setValue( contact.getFirstName() );
        lastName.setValue( contact.getLastName() );
        suffix.setValue( contact.getSuffix() );

        phone.setValue( contact.getContactPhone() );
        email.setValue( contact.getContactEmail() );
        ccEmail.setValue( contact.getCcEmail() );
    }

    @UiHandler( "btnBack" )
    public void handleBack( ClickEvent event )
    {
        bus().fireEvent( new BackEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( ClickEvent event )
    {
        // TODO: implement
//        bus().fireEvent( new EditContactEvent() );
    }
}