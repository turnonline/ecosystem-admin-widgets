package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.CustomerSelectEvent;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Customer;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CustomerSelector
        extends MaterialWindow
{
    private final EventBus eventBus;

    private static final Color PRIMARY_COLOR = Color.LIGHT_GREEN;

    private static AppMessages messages = AppMessages.INSTANCE;

    public CustomerSelector( EventBus eventBus )
    {
        super( messages.labelSelectCustomer() );
        this.eventBus = eventBus;

        setToolbarColor( PRIMARY_COLOR );
        setCloseAnimation( new MaterialAnimation().transition( Transition.BOUNCEOUTUP ) );
    }

    @Override
    public void open()
    {
        clear();
        newTable();
        super.open();
    }

    protected void newTable() {
        SmartTable<Customer> table = new SmartTable<>();
        table.setSelectionType( SelectionType.NONE );

        ColumnContactType<Customer> type = new ColumnContactType<>();
        type.setWidth( "5%" );

        ColumnContactName<Customer> name = new ColumnContactName<>();
        name.setWidth( "30%" );

        ColumnContactAddress<Customer> address = new ColumnContactAddress<>();
        address.setWidth( "30%" );

        ColumnContactContacts<Customer> contacts = new ColumnContactContacts<>();
        contacts.setWidth( "30%" );

        table.addColumn( type, "" );
        table.addColumn( name, messages.labelName() );
        table.addColumn( address, messages.labelAddress() );
        table.addColumn( contacts, messages.labelContacts() );

        table.configure( new CustomerDataSource( ( AppEventBus ) eventBus ) );
        table.addRowSelectHandler( event -> eventBus.fireEvent( new CustomerSelectEvent( event.getModel() ) ) );

        add( table );
    }
}
