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

import biz.turnonline.ecosystem.widget.contact.AppEventBus;
import biz.turnonline.ecosystem.widget.contact.event.DeleteContactEvent;
import biz.turnonline.ecosystem.widget.contact.event.EditContactEvent;
import biz.turnonline.ecosystem.widget.contact.presenter.ContactsPresenter;
import biz.turnonline.ecosystem.widget.contact.ui.ColumnActions;
import biz.turnonline.ecosystem.widget.contact.ui.ColumnAddress;
import biz.turnonline.ecosystem.widget.contact.ui.ColumnContacts;
import biz.turnonline.ecosystem.widget.contact.ui.ColumnName;
import biz.turnonline.ecosystem.widget.contact.ui.ColumnType;
import biz.turnonline.ecosystem.widget.contact.ui.ContactsDataSource;
import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow.Question;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTable;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ContactsView
        extends View
        implements ContactsPresenter.IView
{
    private static ContactsViewUiBinder binder = GWT.create( ContactsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    MaterialButton btnNew;

    @UiField
    MaterialButton btnDelete;

    @UiField
    SmartTable<ContactCard> table;

    @UiField
    ConfirmationWindow confirmationWindow;

    @Override
    public void refresh()
    {
        table.refresh();
    }

    interface ContactsViewUiBinder
            extends UiBinder<HTMLPanel, ContactsView>
    {
    }

    @Inject
    public ContactsView( EventBus eventBus, @Named( "ContactsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        scaffoldNavBar.setActive( Route.CONTACTS );

        add( binder.createAndBindUi( this ) );
        initTable();

        confirmationWindow.getBtnOk().addClickHandler( event -> {
            List<ContactCard> selectedRowModels = table.getSelectedRowModels( false );
            bus().fireEvent( new DeleteContactEvent( selectedRowModels ) );
        } );
    }

    private void initTable()
    {
        ColumnType type = new ColumnType();
        type.setWidth( "5%" );

        ColumnName name = new ColumnName();
        name.setWidth( "30%" );

        ColumnAddress address = new ColumnAddress();
        address.setWidth( "30%" );

        ColumnContacts contacts = new ColumnContacts();
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

    @UiHandler( "btnNew" )
    public void handleNew( ClickEvent event )
    {
        bus().fireEvent( new EditContactEvent() );
    }

    @UiHandler( "btnDelete" )
    public void handleDelete( ClickEvent event )
    {
        List<ContactCard> selected = table.getSelectedRowModels( false );
        if ( !selected.isEmpty() )
        {
            confirmationWindow.open( new Question()
            {
                @Override
                public int selectedRecords()
                {
                    return selected.size();
                }

                @Override
                public String name()
                {
                    return Formatter.formatContactName( selected.get( 0 ) );
                }
            } );
        }
    }
}