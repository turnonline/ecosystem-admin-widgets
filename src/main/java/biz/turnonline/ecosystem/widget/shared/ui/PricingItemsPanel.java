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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.CalculatePricingEvent;
import biz.turnonline.ecosystem.widget.shared.event.ItemChangedCalculateEvent;
import biz.turnonline.ecosystem.widget.shared.event.RowItemSelectionEvent;
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
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.client.ui.table.Table;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableRow;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.ATTENDEE;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.EVENT_PART;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.ORDER_ITEM;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.STANDARD;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.WEBINAR;
import static gwt.material.design.client.constants.IconType.ASSIGNMENT;
import static gwt.material.design.client.constants.IconType.EVENT;
import static gwt.material.design.client.constants.IconType.LOOKS_ONE;
import static gwt.material.design.client.constants.IconType.PEOPLE;
import static gwt.material.design.client.constants.IconType.PERSONAL_VIDEO;

/**
 * Dedicated pricing items panel with specific handling of {@link PricingItem#getItemType()}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PricingItemsPanel
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

    @UiField
    MaterialIcon itemType;

    @UiField
    MaterialLink standard;

    @UiField
    MaterialLink orderItem;

    @UiField
    MaterialLink webinar;

    @UiField
    MaterialLink attendee;

    @UiField
    MaterialLink eventPart;

    private TreeItemWithModel rootTreeItem;

    private String selectedItemCompositeKey;

    private TreeItemWithModel selectedTreeItem;

    private EventBus bus;

    private PricingItemMapper mapper;

    @Inject
    public PricingItemsPanel( EventBus eventBus )
    {
        this.bus = eventBus;

        initWidget( binder.createAndBindUi( this ) );
        mapper = GWT.create( PricingItemMapper.class );

        // header
        MaterialWidget thead = new MaterialWidget( DOM.createTHead() );
        itemsRoot.addHead( thead );

        TableRow thRow = new TableRow();
        thRow.add( header( messages.labelCheckedIn(), "5%" ) );
        thRow.add( header( messages.labelItemName(), "40%" ) );
        thRow.add( header( messages.labelAmount(), "10%" ) );
        thRow.add( header( messages.labelPriceExcludingVat(), "15%" ) );
        thRow.add( header( messages.labelUnit(), "15%" ) );
        thRow.add( header( messages.labelVat(), "10%" ) );
        thRow.add( deleteHeader() );
        thead.add( thRow );

        btnAdd.setEnabled( false );
        btnDelete.setEnabled( false );

        bus.addHandler( RowItemSelectionEvent.TYPE, event -> btnDelete.setEnabled( event.isSelected() ) );
        bus.addHandler( ItemChangedCalculateEvent.TYPE, event -> calculate() );

        pricingTree.addSelectionHandler( event -> {
            btnAdd.setEnabled( true );
            clearAndPopulateRows( ( TreeItemWithModel ) event.getSelectedItem() );
        } );

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

    /**
     * Updates pricing item tree and row items.
     *
     * @param items the pricing items
     */
    public void fill( @Nullable List<PricingItem> items )
    {
        fill( items, false );
    }

    /**
     * Updates pricing item tree and row items that represents product's pricing template.
     *
     * @param items the pricing items taken from product pricing template
     */
    public void fillFromTemplate( @Nullable List<PricingItem> items )
    {

        fill( items, true );
    }

    /**
     * Returns the root {@link TreeItemWithModel} for this panel.
     *
     * @return the root tree item model
     */
    public TreeItemWithModel getRootTreeItem()
    {
        return rootTreeItem;
    }

    private void fill( @Nullable List<PricingItem> items, boolean template )
    {
        rootTreeItem = TreeItemWithModel.parent( bus, template );

        itemsRoot.getBody().clear();
        pricingTree.clear();
        pricingTree.add( rootTreeItem );
        selectedTreeItem = rootTreeItem;

        if ( items != null )
        {
            items.forEach( pi -> chainAddPricingItem( JSON.clone( pi, mapper ), rootTreeItem ) );
        }

        clearAndPopulateRows( selectedTreeItem );
        pricingTree.setSelectedItem( selectedTreeItem );

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
        String compositeKey = inner.getItemCompositeKey();

        if ( compositeKey != null && compositeKey.equals( selectedItemCompositeKey ) )
        {
            selectedTreeItem = inner;
        }

        if ( items != null && !items.isEmpty() )
        {
            for ( PricingItem item : items )
            {
                chainAddPricingItem( item, inner );
            }
        }
    }

    private void clearAndPopulateRows( @Nonnull TreeItemWithModel selected )
    {
        itemsRoot.getBody().clear();
        btnDelete.setEnabled( false );
        selectedItemCompositeKey = selected.getItemCompositeKey();

        String itemType = selected.getChildItemType();
        if ( ORDER_ITEM.equals( itemType ) )
        {
            this.itemType.setIconType( ASSIGNMENT );
        }
        else if ( WEBINAR.equals( itemType ) )
        {
            this.itemType.setIconType( PERSONAL_VIDEO );
        }
        else if ( ATTENDEE.equals( itemType ) )
        {
            this.itemType.setIconType( PEOPLE );
        }
        else if ( EVENT_PART.equals( itemType ) )
        {
            this.itemType.setIconType( EVENT );
        }
        else
        {
            this.itemType.setIconType( LOOKS_ONE );
        }

        List<RowItem> items = selected.getChildrenRows();
        if ( items != null )
        {
            items.forEach( i -> {
                if ( !btnDelete.isEnabled() && i.getDelete().getValue() )
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
            if ( ( ( RowItem ) widget ).getDelete().getValue() )
            {
                ( ( RowItem ) widget ).remove();
            }
        } );

        // disabled as all selected rows just has been removed
        btnDelete.setEnabled( false );
        calculate();
    }

    private void calculate()
    {
        Pricing pricing = new Pricing();
        pricing.setItems( bind() );

        bus.fireEvent( new CalculatePricingEvent( pricing ) );
    }

    private TableData header( String label, String width )
    {
        TableHeader header = new TableHeader();
        header.add( new Label( label ) );
        header.setWidth( width );
        return header;
    }

    private TableData deleteHeader()
    {
        TableHeader header = new TableHeader( new MaterialIcon( IconType.DELETE ) );
        header.setWidth( "5%" );
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
        calculate();
    }

    @UiHandler( "btnAdd" )
    public void handleAdd( ClickEvent event )
    {
        TreeItemWithModel selected = ( TreeItemWithModel ) pricingTree.getSelectedItem();

        PricingItem item = new PricingItem();
        item.setPriceExclVat( 0D );
        item.setCurrency( "EUR" );

        PricingItem model = selected.getModel();
        if ( model != null && model.getVat() != null )
        {
            item.setVat( model.getVat() );
        }

        IconType icon = itemType.getIconType();
        switch ( icon )
        {
            case LOOKS_ONE:
                item.setItemType( STANDARD );
                break;
            case ASSIGNMENT:
                item.setItemType( ORDER_ITEM );
                break;
            case PERSONAL_VIDEO:
                item.setItemType( WEBINAR );
                break;
            case PEOPLE:
                item.setItemType( ATTENDEE );
                break;
            case EVENT:
                item.setItemType( EVENT_PART );
                break;
        }

        itemsRoot.getBody().add( selected.add( item ).getRowItem() );
    }

    @UiHandler( "btnDelete" )
    public void handleDelete( ClickEvent event )
    {
        deleteSelectedRows();
    }

    @UiHandler( "standard" )
    void standard( ClickEvent event )
    {
        itemType.setIconType( LOOKS_ONE );
    }

    @UiHandler( "orderItem" )
    void orderItem( ClickEvent event )
    {
        itemType.setIconType( ASSIGNMENT );
    }

    @UiHandler( "webinar" )
    void webinar( ClickEvent event )
    {
        itemType.setIconType( PERSONAL_VIDEO );
    }

    @UiHandler( "attendee" )
    void attendee( ClickEvent event )
    {
        itemType.setIconType( PEOPLE );
    }

    @UiHandler( "eventPart" )
    void eventPart( ClickEvent event )
    {
        itemType.setIconType( EVENT );
    }

    interface PricingItemMapper
            extends ObjectMapper<PricingItem>
    {
    }

    interface ItemsUiBinder
            extends UiBinder<HTMLPanel, PricingItemsPanel>
    {
    }

}
