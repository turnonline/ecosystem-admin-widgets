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

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.ItemChangedCalculateEvent;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
import biz.turnonline.ecosystem.widget.shared.event.RowItemSelectionEvent;
import biz.turnonline.ecosystem.widget.shared.rest.JSON;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingProduct;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingStructureTemplate;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductInvoicing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.VatRate;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
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
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.ATTENDEE;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.BILLING_ITEM;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.EVENT_PART;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.ORDER_ITEM;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.STANDARD;
import static biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel.WEBINAR;
import static gwt.material.design.client.constants.IconType.ASSIGNMENT;
import static gwt.material.design.client.constants.IconType.ASSIGNMENT_TURNED_IN;
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
    MaterialLink billingItem;

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

    private AppEventBus bus;

    private PricingItemMapper mapper;

    private String currency;

    private boolean originBtnAddEnabled;

    private boolean originBtnDeleteEnabled;

    private boolean isReadOnly;

    @Inject
    public PricingItemsPanel( AppEventBus eventBus )
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

        bus.addHandler( RowItemSelectionEvent.TYPE, event -> btnDelete.setEnabled( !isReadOnly && event.isSelected() ) );
        bus.addHandler( ItemChangedCalculateEvent.TYPE, event -> calculate() );
        pricingTree.addSelectionHandler( e -> clearAndPopulateRows( ( TreeItemWithModel ) e.getSelectedItem() ) );

        itemsRoot.addBody( new MaterialWidget( DOM.createTBody() ) );
    }

    /**
     * If {@code true} sets all editable fields read only.
     * If {@code false} sets all fields previously editable back to be editable.
     */
    public void setReadOnly( boolean readOnly )
    {
        if ( isReadOnly == readOnly )
        {
            //nothing to do, call must be idempotent
            return;
        }

        isReadOnly = readOnly;

        if ( readOnly )
        {
            originBtnAddEnabled = btnAdd.isEnabled();
            originBtnDeleteEnabled = btnDelete.isEnabled();

            btnAdd.setEnabled( false );
            btnDelete.setEnabled( false );
            btnCalculate.setEnabled( false );
            rootTreeItem.setChildrenReadOnly( true );
        }
        else
        {
            btnAdd.setEnabled( originBtnAddEnabled );
            btnDelete.setEnabled( originBtnDeleteEnabled );
            btnCalculate.setEnabled( true );
            rootTreeItem.setChildrenReadOnly( false );
        }
    }

    public void reset()
    {
        itemsRoot.getBody().clear();
        pricingTree.clear();

        isReadOnly = false;
        btnCalculate.setEnabled( true );
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
     * Adds and populates a row and tree item. The final tree will be based on the product's template if defined.
     *
     * @param product    the product as a source of pricing item
     * @param parentItem the empty tree item as a parent item to be populated from given product
     */
    public List<PricingItem> fill( @Nonnull Product product, @Nullable TreeItemWithModel parentItem )
    {
        ProductPricing pricing = checkNotNull( product, "Product cannot be null" ).getPricing();
        if ( pricing == null )
        {
            return new ArrayList<>();
        }

        String productVat = pricing.getVat();
        List<PricingStructureTemplate> templates = pricing.getTemplate();
        List<PricingItem> items = new ArrayList<>();

        if ( templates != null )
        {
            for ( PricingStructureTemplate next : templates )
            {
                items.add( fromTemplate( next, productVat, product ) );
            }
        }

        ProductInvoicing invoicing = product.getInvoicing();

        String currency = pricing.getCurrency();
        currency = currency == null ? "EUR" : currency;

        Double priceExclVat = pricing.getPriceExclVat();
        String unit = invoicing == null ? null : invoicing.getUnit();

        PricingItem root;
        if ( parentItem == null )
        {
            root = new PricingItem();
            root.setAmount( 1.0 );
        }
        else
        {
            root = parentItem.getModel();

            String message = "Expected model of " + TreeItemWithModel.class.getSimpleName() + " not be null";
            checkNotNull( root, message );
        }

        root.setCheckedIn( true );
        root.setItemName( product.getItemName() );
        root.setItemType( STANDARD );
        root.setSubsidiary( pricing.getSubsidiary() );
        root.setItems( items );
        root.setCurrency( currency );
        root.setVat( productVat );

        root.setPriceExclVat( priceExclVat == null ? 0.0 : priceExclVat );
        root.setUnit( unit == null ? "ITEM" : unit );

        List<PricingItem> rootAsList = new ArrayList<>();
        rootAsList.add( root );

        if ( parentItem == null )
        {
            fillFromTemplate( rootAsList );
        }
        else
        {
            parentItem.getRowItem().fill( root );
            items.forEach( pi -> chainAddPricingItem( JSON.clone( pi, mapper ), parentItem ) );
        }

        // sets currency recursively for all current items
        setCurrency( currency );

        return rootAsList;
    }

    @SuppressWarnings( "Duplicates" )
    private PricingItem fromTemplate( @Nonnull PricingStructureTemplate template,
                                      @Nullable String vat,
                                      @Nonnull Product product )
    {
        PricingItem item = new PricingItem();
        item.setAmount( template.getAmount() );
        item.setCheckedIn( template.getCheckedIn() );
        item.setItemName( template.getItemName() );
        item.setItemType( template.getItemType() );
        item.setOrder( template.getOrder() );
        item.setPriceExclVat( template.getPriceExclVat() );
        item.setSubsidiary( template.getSubsidiary() );
        item.setUnit( template.getUnit() );
        item.setVat( vat );
        item.setProduct( new PricingProduct().setTemplateId( template.getId() ).setId( product.getId() ) );

        List<PricingStructureTemplate> templates = template.getItems();
        List<PricingItem> items = new ArrayList<>();

        if ( templates != null )
        {
            for ( PricingStructureTemplate next : templates )
            {
                items.add( fromTemplate( next, vat, product ) );
            }
        }

        if ( !items.isEmpty() )
        {
            item.setItems( items );
        }

        return item;
    }

    /**
     * Updates pricing item tree and row items.
     *
     * @param items the pricing items
     */
    public void fill( @Nullable List<PricingItem> items )
    {
        reset();
        fill( items, false );
    }

    /**
     * Updates pricing item tree and row items that represents product's pricing template.
     *
     * @param items the pricing items taken from product pricing template
     */
    public void fillFromTemplate( @Nullable List<PricingItem> items )
    {
        reset();
        fill( items, true );
    }

    /**
     * Changes the specified VAT for entire pricing tree recursively.
     *
     * @param rate the vat rate to be set
     */
    public void changeVatInTree( @Nonnull VatRate rate )
    {
        this.rootTreeItem.changeVatInTree( rate );
    }

    /**
     * Sets the currency for entire pricing tree recursively.
     *
     * @param currency the target currency
     */
    public void setCurrency( @Nonnull String currency )
    {
        this.currency = checkNotNull( currency, "Currency cannot be null" );
        this.rootTreeItem.changeCurrencyInTree( currency );
    }

    private void fill( @Nullable List<PricingItem> items, boolean template )
    {
        rootTreeItem = TreeItemWithModel.parent( bus, template );

        pricingTree.add( rootTreeItem );
        selectedTreeItem = rootTreeItem;

        if ( items != null )
        {
            items.forEach( pi -> chainAddPricingItem( JSON.clone( pi, mapper ), rootTreeItem ) );
        }

        clearAndPopulateRows( selectedTreeItem );
        pricingTree.setSelectedItem( selectedTreeItem );

        pricingTree.expand();

        originBtnAddEnabled = btnAdd.isEnabled();
        originBtnDeleteEnabled = btnDelete.isEnabled();
    }

    public void update( @Nonnull Pricing pricing )
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
        if ( rootTreeItem.isPricingTemplate() && rootTreeItem.equals( selected ) )
        {
            // root tree item represents only single product, so don't allow add more items
            btnAdd.setEnabled( false );
        }
        else
        {
            btnAdd.setEnabled( !isReadOnly );
        }

        itemsRoot.getBody().clear();
        btnDelete.setEnabled( false );
        selectedItemCompositeKey = selected.getItemCompositeKey();

        String itemType = selected.getChildItemType();
        if ( ORDER_ITEM.equals( itemType ) )
        {
            this.itemType.setIconType( ASSIGNMENT );
        }
        if ( BILLING_ITEM.equals( itemType ) )
        {
            this.itemType.setIconType( ASSIGNMENT_TURNED_IN );
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
        Pricing pricing = new Pricing().setItems( bind() );
        bus.billing().calculate( pricing, response -> bus.fireEvent( new RecalculatedPricingEvent( response ) ) );
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
        item.setCurrency( currency == null ? "EUR" : currency );

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
            case ASSIGNMENT_TURNED_IN:
                item.setItemType( BILLING_ITEM );
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

    @UiHandler( "billingItem" )
    void billingItem( ClickEvent event )
    {
        itemType.setIconType( ASSIGNMENT_TURNED_IN );
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
