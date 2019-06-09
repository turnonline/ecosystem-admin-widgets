/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.myaccount.ui;

import biz.turnonline.ecosystem.widget.myaccount.event.CreateDomainEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.DomainDeleteEvent;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.account.Domain;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.Preconditions.isNullOrEmpty;

/**
 * Domains panel that manages list of domains that belongs to an account.
 * User is allowed to Add and Delete domains from the list by firing following events:
 * <ul>
 * <li>{@link CreateDomainEvent}</li>
 * <li>{@link DomainDeleteEvent}</li>
 * </ul>
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class DomainsPanel
        extends Composite
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static DomainsPanelUiBinder binder = GWT.create( DomainsPanelUiBinder.class );

    private final EventBus bus;

    @UiField
    MaterialDataTable<Domain> table;

    @UiField
    MaterialTextBox domain;

    @UiField
    MaterialTextBox subdomain;

    @UiField
    ConfirmationWindow confirmationWindow;

    public DomainsPanel( @Nonnull EventBus bus )
    {
        this.bus = checkNotNull( bus );

        initWidget( binder.createAndBindUi( this ) );
        initTable();
        domain.setReturnBlankAsNull( true );
        subdomain.setReturnBlankAsNull( true );

        domain.getElement().setAttribute( "autocomplete", "off" );
        subdomain.getElement().setAttribute( "autocomplete", "off" );

        domain.setHelperText( messages.tooltipDomainTipNaked() );
        subdomain.setHelperText( messages.tooltipDomainTipSubdomain() );
        domain.setPlaceholder( messages.labelDomainPlaceholderNaked() );
        subdomain.setPlaceholder( messages.labelDomainPlaceholderSubdomain() );

        confirmationWindow.getBtnOk().addClickHandler( event -> {
            List<String> names = new ArrayList<>();
            for ( Domain domain : table.getSelectedRowModels( false ) )
            {
                names.add( domain.getName() );
            }
            bus.fireEvent( new DomainDeleteEvent( names ) );
        } );
    }

    private void initTable()
    {
        table.setSelectionType( SelectionType.SINGLE );

        table.getScaffolding().getTopPanel().removeFromParent();
        table.getScaffolding().getTableBody().getElement().getStyle().setHeight( 100, Style.Unit.PCT );
        ( ( AbstractDataTable.DefaultTableScaffolding ) table.getScaffolding() ).getXScrollPanel().removeFromParent();

        TextColumn<Domain> column = new TextColumn<Domain>()
        {
            @Override
            public String getValue( Domain domain )
            {
                return domain.getName();
            }
        };
        column.setWidth( "60%" );
        table.addColumn( column, messages.labelDomain() );

        column = new TextColumn<Domain>()
        {
            @Override
            public String getValue( Domain domain )
            {
                return isNullOrEmpty( domain.getSubdomain() )
                        ? messages.labelDomainTypeNaked() : messages.labelDomainTypeSubdomain();
            }
        };
        column.setWidth( "35%" );
        table.addColumn( column, messages.labelDomainType() );

        ColumnDomainVerified verified = new ColumnDomainVerified();
        verified.setWidth( "5%" );
        table.addColumn( verified, messages.labelDomainVerified() );
    }

    public void setDomains( @Nonnull List<Domain> data )
    {
        table.setVisibleRange( 0, data.size() );
        table.loaded( 0, data );
    }

    @UiHandler( "btnAdd" )
    public void handleAddDomain( ClickEvent event )
    {
        Domain lDomain = new Domain().domain( domain.getValue() ).subdomain( subdomain.getValue() );
        this.bus.fireEvent( new CreateDomainEvent( lDomain ) );
    }

    @UiHandler( "btnDelete" )
    public void handleDeleteDomain( ClickEvent event )
    {
        List<Domain> selected = table.getSelectedRowModels( false );
        if ( !selected.isEmpty() )
        {
            confirmationWindow.open( new ConfirmationWindow.Question()
            {
                @Override
                public int selectedRecords()
                {
                    return selected.size();
                }

                @Override
                public String name()
                {
                    return selected.get( 0 ).getName();
                }
            } );
        }
    }

    interface DomainsPanelUiBinder
            extends UiBinder<HTMLPanel, DomainsPanel>
    {
    }
}