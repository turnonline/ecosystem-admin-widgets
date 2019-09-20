package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductDiscount;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.client.ui.table.Table;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableRow;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Discounts
        extends Composite
        implements TakesValue<List<ProductDiscount>>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private FlowPanel root = new FlowPanel();

    private FlowPanel actions = new FlowPanel();

    private Table itemsRoot = new Table();

    private MaterialWidget tbody = new MaterialWidget( DOM.createTBody() );

    private List<ProductDiscount> values = new ArrayList<>();

    public Discounts()
    {
        initWidget( root );

        root.add( actions );
        root.add( itemsRoot );

        // -- actions

        actions.getElement().setAttribute( "style", "margin: 10px 10px;padding-bottom: 20px;" );

        MaterialButton btnAdd = new MaterialButton( messages.labelAdd(), IconType.ADD_CIRCLE, ButtonType.OUTLINED );
        btnAdd.setIconColor( Color.GREEN );
        btnAdd.setTextColor( Color.GREEN );
        btnAdd.setWaves( WavesType.GREEN );
        btnAdd.setMarginRight( 10 );
        btnAdd.addClickHandler( event -> newRow() );
        actions.add( btnAdd );

        MaterialButton btnRemove = new MaterialButton( messages.labelDelete(), IconType.DELETE, ButtonType.OUTLINED );
        btnRemove.setIconColor( Color.RED );
        btnRemove.setTextColor( Color.RED );
        btnRemove.setWaves( WavesType.RED );
        btnRemove.addClickHandler( event -> deleteSelectedRows() );
        actions.add( btnRemove );

        // -- table
        itemsRoot.addStyleName( "table" );

        // header

        MaterialWidget thead = new MaterialWidget( DOM.createTHead() );
        itemsRoot.addHead( thead );

        TableRow thRow = new TableRow();
        thRow.add( selectHeader() );
        thRow.add( header( messages.labelValue(), "15%" ) );
        thRow.add( header( messages.labelDiscountType(), "15%" ) );
        thRow.add( header( messages.labelDiscountRule(), "20%" ) );
        thRow.add( header( messages.labelCodes(), "40%" ) );
        thRow.add( header( messages.labelActive(), "5%" ) );
        thead.add( thRow );

        // body
        itemsRoot.addBody( tbody );
    }

    @Override
    public void setValue( @Nullable List<ProductDiscount> value )
    {
        values.clear();

        if ( value != null && !value.isEmpty() )
        {
            value.forEach( this::createRow );
        }
    }

    @Override
    public List<ProductDiscount> getValue()
    {
        List<ProductDiscount> discounts = new ArrayList<>(  );

        for ( int i = 0; i < values.size(); i++ )
        {
            DiscountItem item = (DiscountItem) tbody.getWidget( i );
            discounts.add( item.getValue() );
        }

        return discounts;
    }

    protected void newRow()
    {
        ProductDiscount discount = new ProductDiscount();
        discount.setEnabled( true );
        createRow( discount );
    }

    protected void deleteSelectedRows()
    {
        List<DiscountItem> itemsToDelete = new ArrayList<>();
        List<ProductDiscount> discountsToDelete = new ArrayList<>();

        for ( int i = 0; i < tbody.getWidgetCount(); i++ )
        {
            DiscountItem item = ( DiscountItem ) tbody.getWidget( i );
            MaterialCheckBox selected = item.getSelected();
            if ( selected.getValue() )
            {
                itemsToDelete.add( item );
                discountsToDelete.add( values.get( i ) );
            }
        }

        itemsToDelete.forEach( Widget::removeFromParent );
        values.removeAll( discountsToDelete );
    }

    private TableData selectHeader()
    {
        TableHeader header = new TableHeader();
        header.setWidth( "5%" );
        return header;
    }

    private TableData header( String label, String width )
    {
        TableHeader header = new TableHeader();
        header.add( new Label( label ) );
        header.setWidth( width );
        return header;
    }

    private void createRow( ProductDiscount discount )
    {
        DiscountItem item = new DiscountItem();
        item.setValue( discount );
        tbody.add( item );

        values.add( discount );
    }
}
