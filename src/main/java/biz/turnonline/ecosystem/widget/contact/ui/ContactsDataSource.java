package biz.turnonline.ecosystem.widget.contact.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import biz.turnonline.ecosystem.widget.shared.ui.RefreshableDataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ContactsDataSource
        extends RefreshableDataSource<ContactCard>
{
    private AppEventBus eventBus;

    public ContactsDataSource( AppEventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public void load( LoadConfig<ContactCard> loadConfig, LoadCallback<ContactCard> callback )
    {
        super.load( loadConfig, callback );

        int limit = loadConfig.getLimit();
        int offset = loadConfig.getOffset();

        eventBus.account().getContacts( eventBus.config().getLoginId(),
                offset,
                limit,
                null,
                response -> callback.onSuccess( new LoadResult<>( response.getItems(), offset, response.getItems().size(), false ) ) );
    }

    @Override
    public boolean useRemoteSort()
    {
        return true;
    }
}
