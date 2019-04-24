package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ProductSelector
        extends MaterialWindow
{
    private final EventBus eventBus;

    private static final Color PRIMARY_COLOR = Color.GREY;

    private static AppMessages messages = AppMessages.INSTANCE;

    public ProductSelector( EventBus eventBus )
    {
        super( messages.labelSelectProduct() );
        this.eventBus = eventBus;

        setToolbarColor( PRIMARY_COLOR );
        getLabelTitle().setIconType( IconType.TABLET_MAC );
        setCloseAnimation( new MaterialAnimation().transition( Transition.BOUNCEOUTUP ) );
    }

    @Override
    public void open()
    {
        clear();
        newTable();
        super.open();
    }

    protected void newTable()
    {
        SmartTable<Product> table = new SmartTable<>();
        table.setSelectionType( SelectionType.NONE );

        ColumnProductPublished published = new ColumnProductPublished();
        published.setWidth( "5%" );

        ColumnProductName name = new ColumnProductName();
        name.setWidth( "45%" );

        ColumnProductPrice price = new ColumnProductPrice();
        price.setWidth( "25%" );

        ColumnProductVat vat = new ColumnProductVat();
        vat.setWidth( "25%" );

        table.addColumn( published );
        table.addColumn( name, messages.labelName() );
        table.addColumn( price, messages.labelPriceExclusiveVat() );
        table.addColumn( vat, messages.labelVat() );

        table.configure( new ProductsDataSource( ( AppEventBus ) eventBus ) );
        table.addRowSelectHandler( event -> onSelect( event.getModel() ) );

        add( table );
    }

    public void onSelect( Product product )
    {
        // noop
    }
}
