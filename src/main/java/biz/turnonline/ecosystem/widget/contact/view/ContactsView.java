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

package biz.turnonline.ecosystem.widget.contact.view;

import biz.turnonline.ecosystem.widget.contact.event.EditContactEvent;
import biz.turnonline.ecosystem.widget.contact.presenter.ContactsPresenter;
import biz.turnonline.ecosystem.widget.contact.ui.ColumnActions;
import biz.turnonline.ecosystem.widget.contact.ui.ContactsDataSource;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import biz.turnonline.ecosystem.widget.shared.ui.ColumnContactAddress;
import biz.turnonline.ecosystem.widget.shared.ui.ColumnContactContacts;
import biz.turnonline.ecosystem.widget.shared.ui.ColumnContactName;
import biz.turnonline.ecosystem.widget.shared.ui.ColumnContactType;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTable;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ContactsView
        extends View
        implements ContactsPresenter.IView
{
    private static ContactsViewUiBinder binder = GWT.create( ContactsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    MaterialAnchorButton newContact;

    @UiField
    SmartTable<ContactCard> table;

    @Inject
    public ContactsView( @Named( "ContactsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.CONTACTS );

        add( binder.createAndBindUi( this ) );
        initTable();

        // refresh action setup
        breadcrumb.setRefreshTooltip( messages.tooltipContactListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> refresh() );

        breadcrumb.setClearFilterVisible( false );
    }

    @Override
    public void refresh()
    {
        table.refresh();
    }

    private void initTable()
    {
        ColumnContactType<ContactCard> type = new ColumnContactType<>();
        type.setWidth( "5%" );

        ColumnContactName<ContactCard> name = new ColumnContactName<>();
        name.setWidth( "30%" );

        ColumnContactAddress<ContactCard> address = new ColumnContactAddress<>();
        address.setWidth( "30%" );

        ColumnContactContacts<ContactCard> contacts = new ColumnContactContacts<>();
        contacts.setWidth( "30%" );

        ColumnActions actions = new ColumnActions( bus() );
        actions.setWidth( "5%" );

        table.addColumn( type, "" );
        table.addColumn( name, messages.labelName() );
        table.addColumn( address, messages.labelAddress() );
        table.addColumn( contacts, messages.labelContacts() );
        table.addColumn( actions );

        table.configure( new ContactsDataSource( ( AppEventBus ) bus() ) );
    }

    @UiHandler( "newContact" )
    public void handleNew( ClickEvent event )
    {
        bus().fireEvent( new EditContactEvent() );
    }

    interface ContactsViewUiBinder
            extends UiBinder<HTMLPanel, ContactsView>
    {
    }
}