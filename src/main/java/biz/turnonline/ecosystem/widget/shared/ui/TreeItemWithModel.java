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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.ItemChangedCalculateEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingStructureTemplate;
import biz.turnonline.ecosystem.widget.shared.rest.billing.VatRate;
import com.google.common.base.Strings;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.tree.MaterialTreeItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel.formatPrice;
import static gwt.material.design.client.constants.IconType.ASSIGNMENT;
import static gwt.material.design.client.constants.IconType.ASSIGNMENT_TURNED_IN;
import static gwt.material.design.client.constants.IconType.EVENT;
import static gwt.material.design.client.constants.IconType.FOLDER;
import static gwt.material.design.client.constants.IconType.LOOKS_ONE;
import static gwt.material.design.client.constants.IconType.PEOPLE;
import static gwt.material.design.client.constants.IconType.PERSONAL_VIDEO;

/**
 * {@link MaterialTreeItem} as a widget associated with {@link PricingItem} model.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class TreeItemWithModel
        extends MaterialTreeItem
{
    static final String STANDARD = "Standard";

    static final String ORDER_ITEM = "OrderItem";

    static final String WEBINAR = "Webinar";

    static final String ATTENDEE = "Attendee";

    static final String EVENT_PART = "EventPart";

    static final String BILLING_ITEM = "BillingItem";

    private static AppMessages messages = AppMessages.INSTANCE;

    private final EventBus eventBus;

    private final PricingItemsPanel.Context context;

    private TreeItemWithModel parent;

    private PricingItem model;

    private RowItem rowItem;

    private List<RowItem> childrenRows = new ArrayList<>();

    private TreeItemWithModel( @Nonnull EventBus eventBus,
                               @Nonnull PricingItemsPanel.Context context )
    {
        this.eventBus = checkNotNull( eventBus, "EventBus can't be null" );
        this.model = null;
        this.context = checkNotNull( context, "Context can't be null" );

        addClickHandler( event -> getTree().expand() ); // override default collapsible behavior - always expanded
    }

    private TreeItemWithModel( @Nonnull EventBus eventBus,
                               @Nonnull PricingItem model,
                               @Nonnull PricingItemsPanel.Context context )
    {
        this.eventBus = checkNotNull( eventBus, "EventBus can't be null" );
        this.model = checkNotNull( model, "PricingItem can't be null" );
        this.context = checkNotNull( context, "Context can't be null" );

        String itemName;
        if ( model.getCurrency() != null && model.getFinalPriceExclVat() != null )
        {
            itemName = ( Strings.isNullOrEmpty( model.getItemName() ) ? "" : model.getItemName() + " - " )
                    + formatPrice( model.getCurrency(), model.getFinalPriceExclVat() );
        }
        else
        {
            itemName = model.getItemName();
        }
        setText( itemName );

        String itemType = model.getItemType();
        if ( STANDARD.equals( itemType ) )
        {
            setIconType( LOOKS_ONE );
        }
        if ( BILLING_ITEM.equals( itemType ) )
        {
            setIconType( ASSIGNMENT );
        }
        else if ( ORDER_ITEM.equals( itemType ) )
        {
            setIconType( ASSIGNMENT_TURNED_IN );
        }
        else if ( WEBINAR.equals( itemType ) )
        {
            setIconType( PERSONAL_VIDEO );
        }
        else if ( ATTENDEE.equals( itemType ) )
        {
            setIconType( PEOPLE );
        }
        else if ( EVENT_PART.equals( itemType ) )
        {
            setIconType( EVENT );
        }
        else
        {
            setIconType( LOOKS_ONE );
        }
    }

    /**
     * Returns the tree item as a parent item that does not have a pricing item associated.
     *
     * @param eventBus the app wide even bus
     * @param context  the UI module context in which tree item will be created
     * @return the tree item as a parent
     */
    static TreeItemWithModel parent( @Nonnull EventBus eventBus, @Nonnull PricingItemsPanel.Context context )
    {
        TreeItemWithModel widgets = new TreeItemWithModel( eventBus, context );
        widgets.setText( messages.labelPricingItems() );
        widgets.setIconType( FOLDER );

        return widgets;
    }

    void setChildrenReadOnly( boolean readOnly )
    {
        for ( RowItem next : childrenRows )
        {
            readOnly( next, readOnly );
        }
    }

    private void readOnly( RowItem item, boolean readOnly )
    {
        item.setReadOnly( readOnly );

        for ( RowItem next : item.getTreeItem().childrenRows )
        {
            readOnly( next, readOnly );
        }
    }

    /**
     * Updates the associated pricing items recursively with the values from UI and returns pricing tree structure.
     *
     * @return the updated pricing tree structure
     */
    public List<PricingItem> bind()
    {
        List<PricingItem> items = new ArrayList<>();
        TreeItemWithModel treeItem;

        for ( RowItem next : childrenRows )
        {
            treeItem = next.getTreeItem();
            PricingItem model = next.bind();
            items.add( model );
            model.setItems( treeItem.bind() );
        }

        return items.isEmpty() ? null : items;
    }

    /**
     * Returns the model associated with this tree item.
     *
     * @return the associated pricing item
     */
    public PricingItem getModel()
    {
        return model;
    }

    /**
     * Returns the item unique identification based on the model's {@link PricingItem#getParentKey()}
     * and {@link PricingItem#getId()}. It returns null if at least one of these values is undefined.
     *
     * @return the item unique identification
     */
    String getItemCompositeKey()
    {
        if ( model == null )
        {
            return null;
        }

        String parentKey = model.getParentKey();
        Long id = model.getId();
        return parentKey != null && id != null ? parentKey + "::" + id : null;
    }

    /**
     * Returns current list of row items that are created from items of associated model {@link PricingItem#getItems()}.
     *
     * @return the children rows
     */
    List<RowItem> getChildrenRows()
    {
        return childrenRows;
    }

    /**
     * Search for item type of the children.
     *
     * @return the child item type
     */
    String getChildItemType()
    {
        String itemType = null;
        PricingItem model;
        Iterator<RowItem> it = childrenRows.iterator();

        while ( it.hasNext() && itemType == null )
        {
            model = it.next().getTreeItem().getModel();
            itemType = model.getItemType();
        }
        return itemType;
    }

    /**
     * Add the specified pricing item as an tree item and row item.
     * Once processed a corresponding {@link RowItem} will be added
     * to the list {@link #getChildrenRows()}
     *
     * @param item the item to be rendered as tree item
     * @return the tree item
     */
    TreeItemWithModel add( @Nonnull PricingItem item )
    {
        TreeItemWithModel treeItem = new TreeItemWithModel( eventBus, item, context );
        treeItem.parent = this;

        addItem( treeItem );

        rowItem = treeItem.fillRowItem();
        childrenRows.add( rowItem );

        if ( treeItem.isRoot() )
        {
            // only the root pricing item is allowed to be changed at UI (different VAt per pricing item is invalid)
            rowItem.addVatChangeHandler( treeItem::vatChanged );
        }

        return treeItem;
    }

    private void vatChanged( ValueChangeEvent<List<VatRate>> event )
    {
        changeVatInTree( event.getValue().get( 0 ) );
        eventBus.fireEvent( new ItemChangedCalculateEvent() );
    }

    /**
     * Changes the specified VAT for entire pricing tree recursively.
     *
     * @param rate the vat rate to be set
     */
    void changeVatInTree( @Nonnull VatRate rate )
    {
        checkNotNull( rate, "VatRate cannot be null" );

        rowItem.setVatRateValue( rate );
        if ( model != null )
        {
            // make sure model value has set as widget might be in unattached state
            // so model is being preferred source of value while binding
            model.setVat( rate.getCode() );
        }

        for ( RowItem next : childrenRows )
        {
            next.getTreeItem().changeVatInTree( rate );
        }
    }

    /**
     * Changes the specified currency for entire pricing tree recursively.
     *
     * @param currency the target currency
     */
    void changeCurrencyInTree( @Nonnull String currency )
    {
        checkNotNull( currency, "Currency cannot be null" );

        if ( model != null )
        {
            model.setCurrency( currency );
        }

        for ( RowItem next : childrenRows )
        {
            next.getTreeItem().changeCurrencyInTree( currency );
        }
    }

    private RowItem fillRowItem()
    {
        rowItem = new RowItem( eventBus, this );
        rowItem.fill( this.model );

        return rowItem;
    }

    /**
     * Returns the boolean indication whether this tree item represents a root pricing item.
     *
     * @return true if this item is root
     */
    boolean isRoot()
    {
        return parent.getModel() == null;
    }

    /**
     * Returns boolean indication whether model has been initialized from product's pricing template.
     *
     * @return {@code true} if initialized from {@link PricingStructureTemplate}
     */
    boolean isProductContext()
    {
        return context == PricingItemsPanel.Context.PRODUCT;
    }

    /**
     * Returns boolean indication whether model has been initialized from PURCHASE_ORDER.
     *
     * @return {@code true} if initialized from PURCHASE_ORDER
     */
    boolean isPurchaseOrderContext()
    {
        return context == PricingItemsPanel.Context.VIEW_ONLY;
    }

    /**
     * Returns row item that's being associated with this tree item
     *
     * @return the associated row item
     */
    RowItem getRowItem()
    {
        return rowItem;
    }

    /**
     * Removes specified row item from the children list (rows).
     *
     * @param rowItem the row item to be deleted, if present
     */
    void remove( RowItem rowItem )
    {
        parent.childrenRows.remove( rowItem );
    }
}
