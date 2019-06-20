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

package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.CalculatePricingEvent;
import biz.turnonline.ecosystem.widget.billing.event.RowItemAtOrderSelectionEvent;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.JSON;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.client.ui.table.Table;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableRow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

/**
 * Dedicated order items panel with specific handling of {@link PricingItem#getItemType()}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrderItems
        extends Composite
{
    private static AppMessages messages = AppMessages.INSTANCE;

    private static ItemsUiBinder binder = GWT.create( ItemsUiBinder.class );

    @UiField
    MaterialTree pricingTree;

    @UiField
    Table itemsRoot;

    @UiField
    MaterialButton btnCalculate;

    @UiField
    MaterialButton btnAdd;

    @UiField
    MaterialButton btnDelete;

    private TreeItemWithModel rootTreeItem;

    private EventBus bus;

    private PricingItemMapper mapper;

    @Inject
    public OrderItems( EventBus eventBus )
    {
        this.bus = eventBus;

        initWidget( binder.createAndBindUi( this ) );
        mapper = GWT.create( PricingItemMapper.class );

        // header
        MaterialWidget thead = new MaterialWidget( DOM.createTHead() );
        itemsRoot.addHead( thead );

        TableRow thRow = new TableRow();
        thRow.add( header( "", "0%" ) );
        thRow.add( header( messages.labelItemName(), "30%" ) );
        thRow.add( header( messages.labelAmount(), "10%" ) );
        thRow.add( header( messages.labelUnit(), "15%" ) );
        thRow.add( header( messages.labelPriceExclusiveVat(), "20%" ) );
        thRow.add( header( messages.labelCurrency(), "10%" ) );
        thRow.add( header( messages.labelVat(), "15%" ) );
        thead.add( thRow );

        btnAdd.setEnabled( false );
        btnDelete.setEnabled( false );

        bus.addHandler( RowItemAtOrderSelectionEvent.TYPE, event -> btnDelete.setEnabled( event.isSelected() ) );

        pricingTree.addSelectionHandler( event -> {
            btnAdd.setEnabled( true );
            clearAndPopulateRows( ( TreeItemWithModel ) event.getSelectedItem() );
        } );

        // body
        itemsRoot.addBody( new MaterialWidget( DOM.createTBody() ) );
    }

    /**
     * Binds all pricing items with the values from UI and returns its instances as list.
     *
     * @return the actual pricing item list
     */
    public List<PricingItem> bind()
    {
        return rootTreeItem.bind();
    }

    public void fill( List<PricingItem> items )
    {
        rootTreeItem = TreeItemWithModel.parent( bus );

        itemsRoot.getBody().clear();
        pricingTree.clear();
        pricingTree.add( rootTreeItem );
        pricingTree.setSelectedItem( rootTreeItem );

        if ( items != null )
        {
            items.forEach( pi -> chainAddPricingItem( JSON.clone( pi, mapper ), rootTreeItem ) );
        }

        clearAndPopulateRows( rootTreeItem );
        pricingTree.expand();
    }

    public void update( Pricing pricing )
    {
        fill( pricing.getItems() );
    }

    private void chainAddPricingItem( @Nonnull PricingItem pi, @Nonnull TreeItemWithModel parent )
    {
        TreeItemWithModel inner = parent.add( pi );
        List<PricingItem> items = pi.getItems();

        if ( items != null && !items.isEmpty() )
        {
            for ( PricingItem item : items )
            {
                chainAddPricingItem( item, inner );
            }
        }
    }

    private void clearAndPopulateRows( @Nonnull TreeItemWithModel item )
    {
        itemsRoot.getBody().clear();
        btnDelete.setEnabled( false );

        List<RowItem> items = item.getChildrenRows();
        if ( items != null )
        {
            items.forEach( i -> {
                if ( !btnDelete.isEnabled() && i.getSelected().getValue() )
                {
                    // make delete button enabled if there is already at least one row selected
                    btnDelete.setEnabled( true );
                }
                itemsRoot.getBody().add( i );
            } );
        }
    }

    private void deleteSelectedRows()
    {
        itemsRoot.getBody().getChildrenList().forEach( widget -> {
            if ( ( ( RowItem ) widget ).getSelected().getValue() )
            {
                ( ( RowItem ) widget ).remove();
            }
        } );

        // disabled as all selected rows just has been removed
        btnDelete.setEnabled( false );
    }

    private TableData header( String label, String width )
    {
        TableHeader header = new TableHeader();
        header.add( new Label( label ) );
        header.setWidth( width );
        return header;
    }

    @UiHandler( "btnCollapse" )
    void onCollapseTree( ClickEvent e )
    {
        pricingTree.collapse();
    }

    @UiHandler( "btnExpand" )
    void onExpandTree( ClickEvent e )
    {
        pricingTree.expand();
    }

    @UiHandler( "btnCalculate" )
    void onCalculate( ClickEvent e )
    {
        Pricing pricing = new Pricing();
        pricing.setItems( bind() );

        bus.fireEvent( new CalculatePricingEvent( pricing ) );
    }

    @UiHandler( "btnAdd" )
    public void handleAdd( ClickEvent event )
    {
        TreeItemWithModel selected = ( TreeItemWithModel ) pricingTree.getSelectedItem();

        PricingItem item = new PricingItem();
        item.setPriceExclVat( 0D );
        item.setCurrency( "EUR" );

        itemsRoot.getBody().add( selected.add( item ).getRowItem() );
    }

    @UiHandler( "btnDelete" )
    public void handleDelete( ClickEvent event )
    {
        deleteSelectedRows();
    }

    interface PricingItemMapper
            extends ObjectMapper<PricingItem>
    {
    }

    interface ItemsUiBinder
            extends UiBinder<HTMLPanel, OrderItems>
    {
    }

}
