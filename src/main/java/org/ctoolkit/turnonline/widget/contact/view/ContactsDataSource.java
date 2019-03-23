package org.ctoolkit.turnonline.widget.contact.view;

import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;
import org.ctoolkit.gwt.client.facade.Items;
import org.ctoolkit.turnonline.widget.contact.AppEventBus;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ContactsDataSource
        implements DataSource<ContactCard>
{
    private AppEventBus eventBus;

    public ContactsDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<ContactCard> loadConfig, LoadCallback<ContactCard> callback )
    {
        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.accountSteward().getContacts( eventBus.getConfiguration().getLoginId(),
                offset,
                limit,
                null,
                new MethodCallback<Items<ContactCard>>()
                {
                    @Override
                    public void onFailure( Method method, Throwable exception )
                    {
                        callback.onFailure( exception );
                    }

                    @Override
                    public void onSuccess( Method method, Items<ContactCard> response )
                    {
                        callback.onSuccess( new LoadResult<>( response.getItems(), offset, response.getItems().size(), false ) );
                    }
                } );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
