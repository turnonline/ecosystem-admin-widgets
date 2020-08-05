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
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ContactsView
        extends View<List<ContactCard>>
        implements ContactsPresenter.IView
{
    private static final ContactsViewUiBinder binder = GWT.create( ContactsViewUiBinder.class );

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
        type.width( "5%" );

        ColumnContactName<ContactCard> name = new ColumnContactName<>();
        name.width( "30%" );

        ColumnContactAddress<ContactCard> address = new ColumnContactAddress<>();
        address.width( "30%" );

        ColumnContactContacts<ContactCard> contacts = new ColumnContactContacts<>();
        contacts.width( "30%" );

        ColumnActions actions = new ColumnActions( bus() );
        actions.width( "5%" );

        table.addColumn( "", type );
        table.addColumn( messages.labelName(), name );
        table.addColumn( messages.labelAddress(), address );
        table.addColumn( messages.labelContacts(), contacts );
        table.addColumn( actions );

        table.configure( new ContactsDataSource( ( AppEventBus ) bus() ) );
    }

    @UiHandler( "newContact" )
    public void handleNew( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new EditContactEvent() );
    }

    interface ContactsViewUiBinder
            extends UiBinder<HTMLPanel, ContactsView>
    {
    }
}