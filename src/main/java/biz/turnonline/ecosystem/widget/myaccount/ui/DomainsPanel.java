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
import biz.turnonline.ecosystem.widget.myaccount.event.SelectDomainType;
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
import gwt.material.design.client.data.events.RowSelectEvent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.table.AbstractDataTable;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.myaccount.event.SelectDomainType.DT.ALL;
import static biz.turnonline.ecosystem.widget.myaccount.event.SelectDomainType.DT.PRODUCTS;
import static biz.turnonline.ecosystem.widget.myaccount.event.SelectDomainType.DT.ROOT;
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

    @UiField
    MaterialButton btnDelete;

    @UiField
    GroupToggleButton<SelectDomainType.DT> domainTypes;

    public DomainsPanel( @Nonnull EventBus bus )
    {
        this.bus = checkNotNull( bus );

        initWidget( binder.createAndBindUi( this ) );
        initTable();

        domainTypes.addItem( messages.labelDomainSelectionDomains(), ROOT );
        domainTypes.addItem( messages.labelDomainSelectionProducts(), PRODUCTS );
        domainTypes.addItem( messages.labelDomainSelectionAll(), ALL );

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

        table.addRowSelectHandler( this::rowSelected );

        domainTypes.addSelectionHandler( e -> bus.fireEvent( new SelectDomainType( e.getSelectedItem() ) ) );
    }

    private void rowSelected( RowSelectEvent<Domain> event )
    {
        btnDelete.setEnabled( event.isSelected() );
        if ( event.isSelected() )
        {
            Domain domain = event.getModel();
            this.domain.setValue( domain.getDomain() );
        }
        else
        {
            this.domain.setValue( null );
        }
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
        column.width( "60%" );
        table.addColumn( messages.labelDomain(), column );

        column = new TextColumn<Domain>()
        {
            @Override
            public String getValue( Domain domain )
            {
                return evaluateDomainType( domain );
            }
        };
        column.width( "35%" );
        table.addColumn( messages.labelDomainType(), column );

        ColumnDomainVerified verified = new ColumnDomainVerified();
        verified.width( "5%" );
        table.addColumn( messages.labelDomainVerified(), verified );
    }

    public void setDomains( @Nonnull List<Domain> data, @Nonnull SelectDomainType.DT type )
    {
        table.setVisibleRange( 0, data.size() );
        table.loaded( 0, data );

        btnDelete.setEnabled( false );
        domain.setValue( null );
        subdomain.setValue( null );

        switch ( type )
        {
            case ROOT:
            {
                domainTypes.setActive( 0, true );
                domainTypes.setActive( 1, false );
                domainTypes.setActive( 2, false );
                break;
            }
            case PRODUCTS:
            {
                domainTypes.setActive( 0, false );
                domainTypes.setActive( 1, true );
                domainTypes.setActive( 2, false );
                break;
            }
            case ALL:
            {
                domainTypes.setActive( 0, false );
                domainTypes.setActive( 1, false );
                domainTypes.setActive( 2, true );
                break;
            }
        }
    }

    private String evaluateDomainType( Domain domain )
    {
        String type;
        if ( isNullOrEmpty( domain.getSubdomain() ) && isNullOrEmpty( domain.getUri() ) )
        {
            type = messages.labelDomainTypeNaked();
        }
        else if ( !isNullOrEmpty( domain.getSubdomain() ) && isNullOrEmpty( domain.getUri() ) )
        {
            type = messages.labelDomainTypeSubdomain();
        }
        else
        {
            type = messages.labelDomainTypeProduct();
        }

        return type;
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