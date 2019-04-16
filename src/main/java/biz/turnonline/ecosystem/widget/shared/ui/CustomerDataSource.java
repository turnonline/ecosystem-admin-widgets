package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Customer;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CustomerDataSource
        extends RefreshableDataSource<Customer>
{
    private AppEventBus eventBus;

    public CustomerDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<Customer> loadConfig, LoadCallback<Customer> callback )
    {
        super.load( loadConfig, callback );

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.accountSteward().getContacts( eventBus.getConfiguration().getLoginId(),
                offset,
                limit,
                null,
                response -> {
                    List<ContactCard> items = response.getItems();
                    List<Customer> customers = map( items );
                    callback.onSuccess( new LoadResult<>( customers, offset, customers.size(), false ) );
                } );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }

    private List<Customer> map( List<ContactCard> contacts )
    {
        List<Customer> customers = new ArrayList<>();
        contacts.forEach( contactCard -> customers.add( new Customer( contactCard ) ) );
        return customers;
    }
}
