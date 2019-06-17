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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.client.ui.table.Table;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableRow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Dedicated order items panel with specific handling of {@link PricingItem#getItemType()}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class OrderItems
        extends Composite
        implements HasModel<Order>
{
    private static AppMessages messages = AppMessages.INSTANCE;

    private static ItemsUiBinder binder = GWT.create( ItemsUiBinder.class );

    @UiField
    MaterialTree pricingTree;

    @UiField
    Table itemsRoot;

    @UiField
    MaterialButton btnAdd;

    @UiField
    MaterialButton btnDelete;

    private List<PricingItem> values = new ArrayList<>();

    private EventBus eventBus;

    @Inject
    public OrderItems( EventBus eventBus )
    {
        this.eventBus = eventBus;

        initWidget( binder.createAndBindUi( this ) );

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

        // body
        itemsRoot.addBody( new MaterialWidget( DOM.createTBody() ) );
    }

    @Override
    public void bind( Order order )
    {
        order.setItems( new ArrayList<>() );

        for ( int i = 0; i < itemsRoot.getWidgetCount(); i++ )
        {
            PricingItem pricingItem = new PricingItem();
            order.getItems().add( pricingItem );

            Item item = ( Item ) itemsRoot.getWidget( i );
            item.bind( pricingItem );
        }
    }

    @Override
    public void fill( Order order )
    {
        itemsRoot.getBody().clear();
        values.clear();
        pricingTree.clear();

        MaterialTreeItem tItem = new MaterialTreeItem( "Order items" );
        tItem.setIconType( IconType.FOLDER );

        pricingTree.add( tItem );
        pricingTree.setSelectedItem( tItem );

        if ( order.getItems() != null )
        {
            order.getItems().forEach( pi -> chainAddPricingItem( pi, pricingTree.getSelectedItem() ) );
        }
    }

    private void chainAddPricingItem( @Nonnull PricingItem pi, @Nonnull MaterialTreeItem parent )
    {
        MaterialTreeItem innerParent = addPricingItem( pi, parent );

        if ( pi.getItems() != null && !pi.getItems().isEmpty() )
        {
            for ( PricingItem item : pi.getItems() )
            {
                chainAddPricingItem( item, innerParent );
            }
        }
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

    @UiHandler( "btnAdd" )
    public void handleAdd( ClickEvent event )
    {
        addPricingItem( new PricingItem(), pricingTree.getSelectedItem() );
    }

    @UiHandler( "btnDelete" )
    public void handleDelete( ClickEvent event )
    {
        deleteSelectedRows();
    }

    private MaterialTreeItem addPricingItem( @Nonnull PricingItem pi, @Nonnull MaterialTreeItem parent )
    {
        values.add( pi );

        Item item = new Item( eventBus );
        item.fill( pi );
        itemsRoot.getBody().add( item );

        MaterialTreeItem tItem = new MaterialTreeItem();
        String itemName;
        if ( pi.getCurrency() != null && pi.getPriceExclVat() != null )
        {
            itemName = pi.getItemName()
                    + " - "
                    + NumberFormat.getCurrencyFormat( pi.getCurrency() ).format( pi.getPriceExclVat() );
        }
        else
        {
            itemName = pi.getItemName();
        }
        tItem.setText( itemName );

        if ( "Standard".equalsIgnoreCase( pi.getItemType() ) )
        {
            tItem.setIconType( IconType.LOOKS_ONE );
        }
        else if ( "OrderItem".equalsIgnoreCase( pi.getItemType() ) )
        {
            tItem.setIconType( IconType.POLL );
        }
        else if ( "Attendee".equalsIgnoreCase( pi.getItemType() ) )
        {
            tItem.setIconType( IconType.PEOPLE );
        }
        else if ( "EventPart".equalsIgnoreCase( pi.getItemType() ) )
        {
            tItem.setIconType( IconType.EVENT );
        }

        parent.addItem( tItem );
        return tItem;
    }

    private void deleteSelectedRows()
    {
        List<Item> rowsToDelete = new ArrayList<>();
        List<PricingItem> itemsToDelete = new ArrayList<>();

        for ( int i = 0; i < itemsRoot.getBody().getWidgetCount(); i++ )
        {
            Item item = ( Item ) itemsRoot.getBody().getWidget( i );
            MaterialCheckBox selected = item.getSelected();
            if ( selected.getValue() )
            {
                rowsToDelete.add( item );
                itemsToDelete.add( values.get( i ) );
            }
        }

        rowsToDelete.forEach( item -> itemsRoot.getBody().remove( item ) );
        values.removeAll( itemsToDelete );
    }

    private TableData header( String label, String width )
    {
        TableHeader header = new TableHeader();
        header.add( new Label( label ) );
        header.setWidth( width );
        return header;
    }

    interface ItemsUiBinder
            extends UiBinder<HTMLPanel, OrderItems>
    {
    }
}
