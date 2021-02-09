package biz.turnonline.ecosystem.widget.myaccount.event;

import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class SaveWhyEvent
        extends GwtEvent<SaveWhyEventHandler>
{
    public static Type<SaveWhyEventHandler> TYPE = new Type<SaveWhyEventHandler>();

    private Account account;

    public SaveWhyEvent( Account account )
    {
        this.account = account;
    }

    public Type<SaveWhyEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveWhyEventHandler handler )
    {
        handler.onSave( this );
    }

    public Account getAccount()
    {
        return account;
    }
}
