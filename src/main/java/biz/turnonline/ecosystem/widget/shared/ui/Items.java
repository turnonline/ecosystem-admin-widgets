package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.HasPricingItems;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.table.Table;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Items<T extends HasPricingItems>
        extends Composite
        implements HasModel<T>
{
    private static ItemsUiBinder binder = GWT.create( ItemsUiBinder.class );

    interface ItemsUiBinder
            extends UiBinder<HTMLPanel, Items>
    {
    }

    private List<PricingItem> values = new ArrayList<>();

    private EventBus eventBus;

    @UiField
    Table itemsRoot;

    @UiField
    MaterialButton btnAdd;

    @UiField
    MaterialButton btnDelete;

    @Inject
    public Items( EventBus eventBus )
    {
        this.eventBus = eventBus;

        initWidget( binder.createAndBindUi( this ) );
    }

    @Override
    public void bind( T hasPricingItems )
    {
        hasPricingItems.setItems( new ArrayList<>() );

        for ( int i = 0; i < itemsRoot.getWidgetCount(); i++ )
        {
            PricingItem pricingItem = new PricingItem();
            hasPricingItems.getItems().add( pricingItem );

            Item item = ( Item ) itemsRoot.getWidget( i );
            item.bind( pricingItem );
        }
    }

    @Override
    public void fill( T hasPricingItems )
    {
        itemsRoot.clear();
        values.clear();

        if ( hasPricingItems.getItems() != null )
        {
            hasPricingItems.getItems().forEach( this::addPricingItem );
        }
    }

    @UiHandler( "btnAdd" )
    public void handleAdd( ClickEvent event )
    {
        addPricingItem( new PricingItem() );
    }

    @UiHandler( "btnDelete" )
    public void handleDelete( ClickEvent event )
    {
        deleteSelectedRows();
    }

    private void addPricingItem( PricingItem pricingItem )
    {
        values.add( pricingItem );

        Item item = new Item( eventBus );
        item.fill( pricingItem );
        itemsRoot.add( item );

        Scheduler.get().scheduleDeferred( () -> item.getItemName().getItemBox().setFocus( true ) );
    }

    private void deleteSelectedRows()
    {
        List<Item> rowsToDelete = new ArrayList<>();
        List<PricingItem> itemsToDelete = new ArrayList<>();

        for ( int i = 0; i < itemsRoot.getWidgetCount(); i++ )
        {
            Item item = ( Item ) itemsRoot.getWidget( i );
            MaterialCheckBox selected = item.getSelected();
            if ( selected.getValue() )
            {
                rowsToDelete.add( item );
                itemsToDelete.add( values.get( i ) );
            }
        }

        rowsToDelete.forEach( item -> itemsRoot.remove( item ) );
        values.removeAll( itemsToDelete );
    }
}
