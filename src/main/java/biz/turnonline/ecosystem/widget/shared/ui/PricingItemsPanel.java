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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.ItemChangedCalculateEvent;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
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
import com.google.gwt.i18n.client.NumberFormat;
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
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;
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

    private final Context context;

    @UiField
    MaterialTree pricingTree;

    @UiField
    Table itemsRoot;

    @UiField
    MaterialButton btnCalculate;

    @UiField
    MaterialButton btnAdd;

    @UiField
    MaterialIcon btnCollapse;

    @UiField
    MaterialIcon btnExpand;

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

    @UiField
    MaterialDropDown types;

    private TreeItemWithModel rootTreeItem;

    private String selectedItemCompositeKey;

    private TreeItemWithModel selectedTreeItem;

    private AppEventBus bus;

    private PricingItemMapper mapper;

    private String currency;

    private boolean originBtnAddEnabled;

    private boolean isReadOnly;

    @Inject
    public PricingItemsPanel( AppEventBus eventBus, Context context )
    {
        this.bus = checkNotNull( eventBus, "EventBus can't be null" );
        this.context = checkNotNull( context, "Context can't be null" );

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
        if ( context != Context.VIEW_ONLY )
        {
            // delete action column won't be shown for PURCHASE_ORDER
            thRow.add( header( "", "5%" ) );
        }
        thead.add( thRow );

        btnAdd.setEnabled( false );

        //workaround to hide an item as for example billingItem.setVisible( false ) doesn't work properly
        types.clear();

        switch ( context )
        {
            case INVOICE:
            {
                types.add( standard );
                types.add( billingItem );
                types.add( webinar );
                types.add( attendee );
                types.add( eventPart );

                break;
            }
            case ORDER:
            case PRODUCT:
            {
                types.add( standard );
                types.add( orderItem );
                types.add( webinar );
                types.add( attendee );
                types.add( eventPart );

                break;
            }
            case VIEW_ONLY:
            {
                btnAdd.setVisible( false );
                btnCalculate.setVisible( false );
                btnCollapse.setVisible( false );
                btnExpand.setVisible( false );
                itemType.setVisible( false );
                break;
            }
        }

        bus.addHandler( ItemChangedCalculateEvent.TYPE, event -> calculate() );
        pricingTree.addSelectionHandler( e -> clearAndPopulateRows( ( TreeItemWithModel ) e.getSelectedItem() ) );

        itemsRoot.addBody( new MaterialWidget( DOM.createTBody() ) );
    }

    /**
     * The price properties formatting.
     *
     * @param priceExclVat    the price excluding VAT
     * @param vatBase         the VAT base
     * @param finalPrice      the final price including VAT
     * @param items           the pricing items
     * @param priceExclVatBox the text box of the price excluding VAT
     * @param vatBaseBox      the input text of the base VAT
     * @param priceInclVatBox the text box of price final price
     */
    public static void updatePricing( @Nullable Double priceExclVat,
                                      @Nullable Double vatBase,
                                      @Nullable Double finalPrice,
                                      @Nullable List<PricingItem> items,
                                      @Nonnull MaterialTextBox priceExclVatBox,
                                      @Nonnull MaterialTextBox vatBaseBox,
                                      @Nonnull MaterialTextBox priceInclVatBox )
    {
        updatePricing( priceExclVat,
                vatBase,
                finalPrice,
                null,
                items,
                priceExclVatBox,
                vatBaseBox,
                priceInclVatBox,
                null );
    }

    /**
     * The price properties formatting.
     *
     * @param priceExclVat    the price excluding VAT
     * @param vatBase         the VAT base
     * @param finalPrice      the final price including VAT
     * @param amountToPay     the total amount to be paid
     * @param items           the pricing items
     * @param priceExclVatBox the text box of the price excluding VAT
     * @param vatBaseBox      the input text of the base VAT
     * @param priceInclVatBox the text box of price final price
     * @param toPayBox        the text box of total amount to be paid
     */
    public static void updatePricing( @Nullable Double priceExclVat,
                                      @Nullable Double vatBase,
                                      @Nullable Double finalPrice,
                                      @Nullable Double amountToPay,
                                      @Nullable List<PricingItem> items,
                                      @Nonnull MaterialTextBox priceExclVatBox,
                                      @Nonnull MaterialTextBox vatBaseBox,
                                      @Nonnull MaterialTextBox priceInclVatBox,
                                      @Nullable MaterialTextBox toPayBox )
    {
        String excludingVat;
        String base;
        String includingVat;
        String currency = null;
        String toPay;

        if ( items != null && !items.isEmpty() )
        {
            currency = items.get( 0 ).getCurrency();
        }

        if ( currency != null )
        {
            if ( priceExclVat != null && vatBase != null && finalPrice != null )
            {
                excludingVat = formatPrice( currency, priceExclVat );
                base = formatPrice( currency, vatBase );
                includingVat = formatPrice( currency, finalPrice );

                if ( amountToPay == null )
                {
                    toPay = formatPrice( currency, finalPrice );
                }
                else
                {
                    toPay = formatPrice( currency, amountToPay );
                }
            }
            else
            {
                excludingVat = formatPrice( currency, 0.0 );
                base = formatPrice( currency, 0.0 );
                includingVat = formatPrice( currency, 0.0 );
                toPay = formatPrice( currency, 0.0 );
            }
        }
        else
        {
            excludingVat = "0";
            base = "0";
            includingVat = "0";
            toPay = "0";
        }

        priceExclVatBox.setValue( excludingVat );
        vatBaseBox.setValue( base );
        priceInclVatBox.setValue( includingVat );

        if ( toPayBox != null )
        {
            toPayBox.setValue( toPay );
        }
    }

    public static String formatPrice( @Nonnull String currency, @Nonnull Double number )
    {
        return NumberFormat.getCurrencyFormat( currency ).format( number );
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

            btnAdd.setEnabled( false );
            btnCalculate.setEnabled( false );
            rootTreeItem.setChildrenReadOnly( true );
        }
        else
        {
            btnAdd.setEnabled( originBtnAddEnabled );
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
        currency = currency == null ? bus.config().getCurrency() : currency;

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
            fill( rootAsList );
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

    /**
     * Updates pricing item tree and row items.
     *
     * @param items the pricing items
     */
    public void fill( @Nullable List<PricingItem> items )
    {
        reset();

        rootTreeItem = TreeItemWithModel.parent( bus, context );

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
        setReadOnly( context == Context.VIEW_ONLY );
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
        if ( rootTreeItem.isProductContext() && rootTreeItem.equals( selected ) )
        {
            // root tree item represents only single product, so don't allow add more items
            btnAdd.setEnabled( false );
        }
        else
        {
            btnAdd.setEnabled( !isReadOnly );
        }

        itemsRoot.getBody().clear();
        selectedItemCompositeKey = selected.getItemCompositeKey();

        String itemType = selected.getChildItemType();
        if ( ORDER_ITEM.equals( itemType ) )
        {
            this.itemType.setIconType( ASSIGNMENT_TURNED_IN );
        }
        if ( BILLING_ITEM.equals( itemType ) )
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
            items.forEach( i -> itemsRoot.getBody().add( i ) );
        }
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

    @UiHandler( "btnCollapse" )
    void onCollapseTree( @SuppressWarnings( "unused" ) ClickEvent e )
    {
        pricingTree.collapse();
    }

    @UiHandler( "btnExpand" )
    void onExpandTree( @SuppressWarnings( "unused" ) ClickEvent e )
    {
        pricingTree.expand();
    }

    @UiHandler( "btnCalculate" )
    void onCalculate( @SuppressWarnings( "unused" ) ClickEvent e )
    {
        calculate();
    }

    @UiHandler( "btnAdd" )
    public void handleAdd( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        TreeItemWithModel selected = ( TreeItemWithModel ) pricingTree.getSelectedItem();

        PricingItem item = new PricingItem();
        item.setPriceExclVat( 0D );
        item.setCurrency( currency == null ? bus.config().getCurrency() : currency );

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
            case ASSIGNMENT_TURNED_IN:
                item.setItemType( ORDER_ITEM );
                break;
            case ASSIGNMENT:
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

        RowItem rowItem = selected.add( item ).getRowItem();
        itemsRoot.getBody().add( rowItem );
    }

    @UiHandler( "standard" )
    void standard( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        itemType.setIconType( LOOKS_ONE );
    }

    @UiHandler( "billingItem" )
    void billingItem( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        itemType.setIconType( ASSIGNMENT );
    }

    @UiHandler( "orderItem" )
    void orderItem( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        itemType.setIconType( ASSIGNMENT_TURNED_IN );
    }

    @UiHandler( "webinar" )
    void webinar( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        itemType.setIconType( PERSONAL_VIDEO );
    }

    @UiHandler( "attendee" )
    void attendee( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        itemType.setIconType( PEOPLE );
    }

    @UiHandler( "eventPart" )
    void eventPart( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        itemType.setIconType( EVENT );
    }

    public enum Context
    {
        PRODUCT,
        ORDER,
        INVOICE,
        VIEW_ONLY
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
