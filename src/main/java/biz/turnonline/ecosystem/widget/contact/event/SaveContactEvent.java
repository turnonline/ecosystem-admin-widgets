package biz.turnonline.ecosystem.widget.contact.event;

import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveContactEvent
        extends GwtEvent<SaveContactEventHandler>
{
    public static Type<SaveContactEventHandler> TYPE = new Type<SaveContactEventHandler>();

    private final ContactCard contactCard;

    public SaveContactEvent( ContactCard contactCard )
    {
        this.contactCard = contactCard;
    }

    public Type<SaveContactEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveContactEventHandler handler )
    {
        handler.onSaveContact( this );
    }

    public ContactCard getContactCard()
    {
        return contactCard;
    }
}
